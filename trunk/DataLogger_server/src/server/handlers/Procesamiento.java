package server.handlers;

import DataBase.ConexionBase;
import Utilities.UtilidadesApp;
import entidades.EntCanal;
import entidades.EntComando;
import entidades.EntTrama;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.util.internal.ConcurrentHashMap;

public class Procesamiento extends SimpleChannelHandler {

    private ConexionBase con;
    private String idEquipo; //ID físico
    private boolean estaAgregado; //Primer registro de la conexion
    private Calendar fechaHoraConexion;
    private Calendar fechaHoraUltimoDato;
    private ConcurrentHashMap<Integer, EntCanal> canales;
    private ConcurrentHashMap<Integer, EntComando> comandos;
    //Timer Lectura de comandos hacia el equipo
    private Timer timerSendCommand;
    private TimerTask tm;
    private Channel chEqp;

    public Procesamiento(Timer tm) {
        estaAgregado = false;
        canales = new ConcurrentHashMap<Integer, EntCanal>();
        comandos = new ConcurrentHashMap<Integer, EntComando>();
        this.timerSendCommand = tm;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
        String data = "";

        while (buf.readable()) {
            byte aux = buf.readByte();
            char charVal = (char) aux;
            int valEnt = charVal;

            if (valEnt < 10) {
                data += valEnt;
            } else if (valEnt == 10 || valEnt == 13) {
                data += "@";
            } else {
                data += charVal;
            }            
        }

        fechaHoraUltimoDato = new GregorianCalendar();

        //TODO: REGISTRAR ULTIMA HORA DE DATO RECIBIDO

        System.out.println("[" + data + "]");

        //TODO: IDENTIFICAR LOS TIPOS DE MENSAJES EXISTENTES

        if (data.indexOf("0@80") == 0) {
            data = UtilidadesApp.eliminarEspacios(data);
            System.out.println("Nombre : [" + data + "]");
            registrarNombreEquipo(data);
        } else if (data.indexOf("0150") == 0) {
            System.out.println("RTA : [" + data + "]");
            vincularRespuesta(data.substring(4, data.length()));
        } else {
            procesarTrama(data);
        }

        data = "";
        buf.clear();
    }

    /**
     * Manejo de Excepciones que se pueden producir dentro del Canal
     * en este caso toda excepción probocará que el canal se cierre.
     *
     * @param ctx
     * @param e
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {

        String auxMsjError = "";
        if (e.getCause().toString().equals("org.jboss.netty.handler.timeout.ReadTimeoutException")) {
            auxMsjError = "TimeOut cumplido. [" + idEquipo + "]";
        } else if (e.getCause().toString().equals("java.io.IOException: Se ha forzado la interrupción de una conexión existente por el host remoto")) {
            auxMsjError = "Conexion reiniciada por el Equipo. [" + idEquipo + "]";
        } else if (e.getCause().toString().equals("java.io.IOException: Connection reset by peer")) {
            auxMsjError = "Conexion reiniciada. [" + e.getCause() + "]";
        } else if (e.getCause().toString().equals("java.io.IOException")) {
            auxMsjError = "Equipo a cortado la comunicacion";
        } else {
            auxMsjError = "Excepción no registrada :: " + e.getCause();
        }

        if (UtilidadesApp.getDebugMode()) {
            System.out.println(auxMsjError);
        } else {
            UtilidadesApp.logInfo.info(auxMsjError);
        }

        if (tm != null) {
            tm.cancel();
        }
        Channel ch = e.getChannel();
        ch.close();

    }

    /**
     * Lanzado cuando se ha establecido una conexión
     * hacia el servidor.
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        idEquipo = null;
        //Registro de fecha y hora de conexion
        fechaHoraConexion = new GregorianCalendar();
        fechaHoraUltimoDato = fechaHoraConexion;
        con = new ConexionBase();
        chEqp = e.getChannel();
        //TODO: REGISTRAR LA CONEXIÓN DEL EQUIPO
    }

    /**
     * Lanzado al registrar la desconexión de un
     * canal
     * 
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (con != null) {
            con.CerrarConexion();
        }
        if (tm != null) {
            tm.cancel();
        }
        if (UtilidadesApp.getDebugMode()) {
            System.out.println("Canal Cerrado [" + idEquipo + "]");
        } else {
            UtilidadesApp.logInfo.info("Canal Cerrado [" + idEquipo + "]");
        }
    }

    /**
     * Procesa la trama enviada por
     * el equipo
     * @param trm
     */
    private void procesarTrama(String trm) {

        //comprobar cambios en factores de conversion
        canales = con.cargaFactoresConversion(idEquipo, false, canales);

        String msj = "";

        if (trm.length() > 30 && trm.contains("#") && trm.contains("$")) {

            trm = trm.substring(trm.indexOf("#"), trm.indexOf("$"));
            trm = trm.replace('$', '#');
            trm = trm.replaceAll("#", "");

            //separar parámetros
            String[] dataParam = trm.split(",");
            try {

                EntTrama objSave = new EntTrama(
                        dataParam[0],
                        Integer.parseInt(dataParam[3]),
                        Integer.parseInt(dataParam[4]),
                        Integer.parseInt(dataParam[5]),
                        Integer.parseInt(dataParam[6]),
                        Integer.parseInt(dataParam[7]),
                        Integer.parseInt(dataParam[8]),
                        Integer.parseInt(dataParam[9]),
                        Integer.parseInt(dataParam[10]),
                        Integer.parseInt(dataParam[11]));

                boolean state = objSave.setFechaHora(dataParam[1] + dataParam[2]);

                //Aplicar factores de conversion
                objSave.setIn1(canales.get(1));
                objSave.setIn2(canales.get(2));
                objSave.setIn3(canales.get(3));
                objSave.setIn4(canales.get(4));
                objSave.setIn5(canales.get(5));
                objSave.setIn6(canales.get(6));

                //insertarlo
                if (state) {
                    con.insertTramaEqp(objSave);
                }

            } catch (NumberFormatException nfe) {
                msj = "No es posible transformar el valor. ";
                if (UtilidadesApp.getDebugMode()) {
                    System.out.println(msj + " [" + trm + "]");
                } else {
                    UtilidadesApp.logError.info(msj + " [" + trm + "]");
                }
            } catch (IndexOutOfBoundsException iofbe) {
                msj = "Parametros insuficientes. ";
                if (UtilidadesApp.getDebugMode()) {
                    System.out.println(msj + " [" + trm + "]");
                } else {
                    UtilidadesApp.logError.info(msj + " [" + trm + "]");
                }
            }
        } else {
            msj = "Marcas de inicio o fin no determinadas";
            if (UtilidadesApp.getDebugMode()) {
                System.out.println(msj + " [" + trm + "]");
            } else {
                UtilidadesApp.logError.info(msj + " [" + trm + "]");
            }
        }
    }

    /**
     * Registra el nombre del Equipo
     * @param idEqp
     */
    private void registrarNombreEquipo(String idEqp) {
        idEquipo = idEqp;
        canales = con.cargaFactoresConversion(idEqp, true, canales);

        tm = new TimerTask() {

            @Override
            public void run() {
                enviarComandoEqp(chEqp);
            }
        };
        timerSendCommand.schedule(tm, 2 * 1000, 2 * 1000);
    }

    /**
     * Envia un comando al equipo
     */
    private void enviarComandoEqp(Channel ch) {
        System.out.println("buscando comandos...");
        con.cargaComandos(idEquipo, comandos);

        for (Map.Entry<Integer, EntComando> indv : comandos.entrySet()) {
            if (indv.getValue().getEstado() == 1) {

                String msj = "Datos enviados : [" + idEquipo + "] : [ " + indv.getValue().getComando() + ']';
                if (UtilidadesApp.getDebugMode()) {
                    System.out.println(msj);
                } else {
                    UtilidadesApp.logInfo.info(msj);
                }
                ch.write(indv.getValue().getComando());
                indv.getValue().setEstado(2);
                con.actualizarComando(indv.getKey(), 2);

            } else if (indv.getValue().getEstado() == 3) {
                comandos.remove(indv.getKey());
                con.actualizarComando(indv.getKey(), 3);
                con.actualizarComando(indv.getKey(), indv.getValue().getResp());
            }
        }
    }

    /**
     * Vincula respuesta con el comando
     * state: PRUEBAS
     * @param rta
     */
    private void vincularRespuesta(String rta) {
        for (Map.Entry<Integer, EntComando> indv : comandos.entrySet()) {
            if (indv.getValue().getEstado() == 2) {
                indv.getValue().setResp(rta);
                indv.getValue().setEstado(3);

                System.out.println("comando id["+indv.getKey()+"] ["+indv.getValue().getEstado()+"]");
                break;
            }
        }
    }
}
