package DataBase;

import Utilities.UtilidadesApp;
import entidades.EntCanal;
import entidades.EntComando;
import entidades.EntTrama;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * @author qmarqeva
 */
public class ConexionBase {

    private Connection conexion;
    private PreparedStatement psA; //crear comando para enviar
    private PreparedStatement psK;
    private PreparedStatement psB;
    private PreparedStatement psL;
    private PreparedStatement psM;
    private PreparedStatement psN;
    private PreparedStatement psO;

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac
     */
    public ConexionBase() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + UtilidadesApp.bd_ip + "/" + UtilidadesApp.bd_schema + "?noAccessToProcedureBodies=true";

        try {

            String passEncript = UtilidadesApp.encript(UtilidadesApp.bd_pass);

            if (passEncript == null) {
                if (UtilidadesApp.getDebugMode()) {
                    System.out.println("Error encriptar: "
                            + UtilidadesApp.bd_pass);
                } else {
                    UtilidadesApp.logError.info("Error encriptar: "
                            + UtilidadesApp.bd_pass);
                }
                System.exit(1);
            }

            Class.forName(driver).newInstance();
            this.conexion = DriverManager.getConnection(
                    url,
                    UtilidadesApp.bd_user,
                    passEncript);

            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Conexion a Base de Datos: "
                        + UtilidadesApp.bd_schema
                        + " user:["
                        + UtilidadesApp.bd_user
                        + "] Establecida Correctamente");
            }

        } catch (Exception exc) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al tratar de abrir la base de Datos: "
                        + UtilidadesApp.bd_schema
                        + " : " + exc);
            } else {
                UtilidadesApp.logError.info("Error al tratar de abrir la base de Datos: "
                        + UtilidadesApp.bd_schema + " :: " + exc);
            }
            System.exit(1);
        }

    }

    /**
     * Cierra la this.conexion con la base de datos
     * @return Connection
     */
    public boolean CerrarConexion() {
        try {
            //Cerrar todos los ResultSet
            if (psA != null) {
                psA.close();
            }
            if (psB != null) {
                psB.close();
            }
            if (psK != null) {
                psK.close();
            }
            if (psL != null) {
                psL.close();
            }
            if (psM != null) {
                psM.close();
            }
            if (psN != null) {
                psN.close();
            }

            if (psO != null) {
                psO.close();
            }
            //Cerrar Conexión Principal
            if (this.conexion != null) {
                this.conexion.close();
            }

        } catch (SQLException ex) {

            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al cerrar la Conexion BD " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al cerrar la Conexion BD " + ex);
            }

            return false;
        }
        this.conexion = null;
        return true;
    }

    /**
     * Inserta un nuevo dato proveniente
     * de un equipo SKP
     * 
     * @param id
     * @param nunidad
     * @param idempresa
     * @param latitud
     * @param longitud
     * @param fecha
     * @param hora
     * @param velocidad
     * @param G1
     * @param G2
     * @param SAL
     * @param BAT
     * @param GPS1
     * @param GSM
     * @param GPS2
     * @param ING
     * @param param1,
     * @param idevento
     */
    public void insertTramaEqp(EntTrama indv) {

        try {

            //Construcción de SQL
            if (psB == null) {
                try {
                    String insert = "INSERT INTO DATA_ESTACIONES(ID_PARTICION_DE, ID_GRUPO, ID_EST, FECHA_HORA_DE, "
                            + " VAR1_DE, VAR2_DE, VAR3_DE, VAR4_DE, VAR5_DE, VAR6_DE, DIG_IN1_DE, DIG_IN2_DE, "
                            + " DIG_IN3_DE, DIG_IN4_DE, DIG_IN5_DE, DIG_IN6_DE, DIG_IN7_DE, DIG_IN8_DE, "
                            + " DIG_OUT1_DE, DIG_OUT2_DE, DIG_OUT3_DE, DIG_OUT4_DE, DIG_OUT5_DE, "
                            + " DIG_OUT6_DE, DIG_OUT7_DE, DIG_OUT8_DE, CARGA) "
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    psB = conexion.prepareStatement(insert);

                } catch (SQLException ex) {
                    if (UtilidadesApp.getDebugMode()) {
                        System.out.println("Problemas al generar PrepareStatement " + ex);
                    } else {
                        UtilidadesApp.logDataBase.info("Problemas al generar PrepareStatement " + ex);
                    }
                }
            }


            //Parámetros
            psB.setInt(1, indv.getIdPartition());
            psB.setString(2, indv.getIdGrupo());
            psB.setString(3, indv.getIdEquipNum());
            psB.setString(4, indv.getFechaHora());
            psB.setDouble(5, indv.getIn1());
            psB.setDouble(6, indv.getIn2());
            psB.setDouble(7, indv.getIn3());
            psB.setDouble(8, indv.getIn4());
            psB.setDouble(9, indv.getIn5());
            psB.setDouble(10, indv.getIn6());

            String indig = indv.getInDig();
            for (int i = 0; i < 8; i++) {
                psB.setString(11 + i, String.valueOf(indig.charAt(i)));
            }

            String outdig = indv.getOutDig();
            for (int i = 0; i < 8; i++) {
                psB.setString(19 + i, String.valueOf(outdig.charAt(i)));
            }

            psB.setInt(27, indv.getCarga());
            psB.executeUpdate();


        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar 'insertTramaEqp' " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar 'insertTramaEqp' " + ex);
            }
        }
    }

    /**
     * Almacena en la Base de Datos los
     * datos recibidos por el taximetro.
     *
     * @param empresa
     * @param ruc
     * @param serial
     * @param ciudad
     * @param tiquete
     * @param autoriz
     * @param placa
     * @param taxi
     * @param fecha
     * @param horaini
     * @param horafin
     * @param distancia
     * @param costo
     * @param paga
     */
    public void actualizarComando(int idCmd, String rta) {
        try {
            //Construcción de SQL
            if (psK == null) {
                try {
                    String insert = "UPDATE COMANDOS SET "
                            + " RESPUESTA = ? WHERE ID_CMD_ENV = ?";
                    psK = conexion.prepareStatement(insert);

                } catch (SQLException ex) {
                    if (UtilidadesApp.getDebugMode()) {
                        System.out.println("Problemas al generar PrepareStatement " + ex);
                    } else {
                        UtilidadesApp.logDataBase.info("Problemas al generar PrepareStatement " + ex);
                    }
                }
            }

            //Carga de Parámetros
            psK.setString(1, rta);
            psK.setInt(2, idCmd);

            psK.executeUpdate();


        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar 'actualizarComando' " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar 'actualizarComando' " + ex);
            }
        }
    }

    /**
     * Cambio de estado a un comando 
     * @param idCmd
     * @param state
     */
    public void actualizarComando(int idCmd, int state) {
        try {
            if (psN == null) {
                try {

                    String insert = "UPDATE COMANDOS SET "
                            + " ESTADO = ?, FH_ENVIADO = NOW() WHERE ID_CMD_ENV = ?";

                    psN = conexion.prepareStatement(insert);

                } catch (SQLException ex) {
                    if (UtilidadesApp.getDebugMode()) {
                        System.out.println("Problemas al generar PrepareStatement [actualizarComando]" + ex);
                    } else {
                        UtilidadesApp.logDataBase.info("Problemas al generar PrepareStatement [actualizarComando]" + ex);
                    }
                }
            }

            psN.setInt(1, state);
            psN.setInt(2, idCmd);

            psN.executeUpdate();

        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar [actualizarComando] " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar [actualizarComando] " + ex);
            }
        }
    }

    /**
     *
     * @param idEqp
     * @param firsTime  Es la primera vez?
     * @param fctrConv
     *
     * simbologia:
     * A -> ya existia
     * N -> nueva (actualizacion)
     * B -> marcada para eliminar
     * X -> eliminada
     *
     */
    public ConcurrentHashMap<Integer, EntCanal> cargaFactoresConversion(
            String idEqp,
            boolean firsTime,
            ConcurrentHashMap<Integer, EntCanal> fctrConv) {

        try {
            if (psM == null) {
                try {

                    String insert = "SELECT ID_CANAL, VAL_SENSOR, VAL_EQV, SIMB, ESTADO"
                            + " FROM FACTORES_CONVERSION"
                            + " WHERE GRUPO_STCN = ?"
                            + " AND ESTADO = 'N' "
                            + " OR ESTADO = ?";

                    psM = conexion.prepareStatement(insert);

                } catch (SQLException ex) {
                    if (UtilidadesApp.getDebugMode()) {
                        System.out.println("Problemas al generar PrepareStatement [cargaFactoresConversion]" + ex);
                    } else {
                        UtilidadesApp.logDataBase.info("Problemas al generar PrepareStatement [cargaFactoresConversion]" + ex);
                    }
                }
            }

            String std = "B";
            if (firsTime) {
                std = "A";
                fctrConv = new ConcurrentHashMap<Integer, EntCanal>();
            }

            psM.setString(1, idEqp);
            psM.setString(2, std);

            ResultSet dataFC = psM.executeQuery();




            while (dataFC.next()) {
                int idC = dataFC.getInt("ID_CANAL");
                double valS = dataFC.getDouble("VAL_SENSOR");
                double valE = dataFC.getDouble("VAL_EQV");
                String valSimb = dataFC.getString("SIMB");
                String estd = dataFC.getString("ESTADO");

                if (firsTime && (estd.equals("A") || estd.equals("N"))) {
                    fctrConv.put(idC, new EntCanal(idC, valS, valE, valSimb));
                } else if (estd.equals("N")) {
                    fctrConv.remove(idC);
                    fctrConv.put(idC, new EntCanal(idC, valS, valE, valSimb));
                } else if (estd.equals("B")) {
                    fctrConv.remove(idC);
                }
            }

            actualizarEstFactorConvers(idEqp, "N", "A");
            actualizarEstFactorConvers(idEqp, "B", "X");

        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar [cargaFactoresConversion] " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar [cargaFactoresConversion] " + ex);
            }
        }
        return fctrConv;
    }

    /**
     * Carga de comandos para envio al Equipo
     * @param nombEquipo
     * @param chm
     * @return
     */
    public ConcurrentHashMap<Integer, EntComando> cargaComandos(String nombEquipo,
            ConcurrentHashMap<Integer, EntComando> chm) {

        try {

            if (psL == null) {
                psL = conexion.prepareStatement("SELECT ID_CMD_ENV, COMANDO "
                        + "FROM COMANDOS WHERE "
                        + "IDEQUIPO_EST = ? AND ESTADO = 1");
            }

            psL.setString(1, nombEquipo);

            ResultSet rs = psL.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID_CMD_ENV");
                String cmd = rs.getString("COMANDO");
                if (chm.get(id) == null) {
                    chm.put(id, new EntComando(id, cmd));
                }
            }
        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar [cargaComandos] " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar [cargaComandos] " + ex);
            }
        }
        return chm;
    }

    /**
     * Actualiza el estado de un
     * factor de conversion,
     * relacionado a un equipo
     *
     * @param idEqp
     * @param valueOld
     * @param valueNew
     */
    public void actualizarEstFactorConvers(
            String idEqp,
            String valueOld,
            String valueNew) {

        try {

            if (psO == null) {
                psO = conexion.prepareStatement(
                        " UPDATE FACTORES_CONVERSION"
                        + " SET ESTADO = ? "
                        + " WHERE ESTADO = ?"
                        + " AND GRUPO_STCN = ?");
            }

            psO.setString(1, valueNew);
            psO.setString(2, valueOld);
            psO.setString(3, idEqp);

            psO.executeUpdate();

        } catch (SQLException ex) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("Error al ejecutar 'actualizarEstFactorConvers' " + ex);
            } else {
                UtilidadesApp.logDataBase.info("Error al ejecutar 'actualizarEstFactorConvers' " + ex);
            }
        }

    }

    /**
     * Registra un comando a ser
     * enviado a una de las estaciones
     * 
     * @param idEstc
     * @param cmd
     * @param idEqp
     */
    public String enviarComando(int idEstc, String cmd, String idEqp) {

        try {

            if (psA == null) {
                psA = conexion.prepareStatement("INSERT INTO COMANDOS "
                        + " (ID_EST, "
                        + " COMANDO, "
                        + " FH_ENCOLADO, "
                        + " ESTADO, "
                        + " IDEQUIPO_EST) "
                        + " VALUES(?,?,DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,?)");
            }

            psA.setInt(1, idEstc);
            psA.setString(2, cmd);
            psA.setString(3, idEqp);

            psA.executeUpdate();
            return "Comando Encolado";

        } catch (SQLException ex) {
            String msj = "Error al ejecutar 'enviarComando' " + ex;
            if (UtilidadesApp.getDebugMode()) {
                System.out.println(msj);
            } else {
                UtilidadesApp.logDataBase.info(msj);
            }
            return msj;
        }

    }
}
