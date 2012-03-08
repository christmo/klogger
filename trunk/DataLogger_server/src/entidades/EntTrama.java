package entidades;

import Utilities.UtilidadesApp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qmarqeva
 */
public class EntTrama {

    private int idPartition;
    private String idEquipo;
    private Date fechaHora;
    private double in1;
    private double in2;
    private double in3;
    private double in4;
    private double in5;
    private double in6;
    private int inDig;
    private int outDig;
    private int carga;
    private SimpleDateFormat sdfIn = new SimpleDateFormat("ddMMyyHHmmss");
    private SimpleDateFormat sdfOutFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd");

    public EntTrama(
            String idEq,
            int in1,
            int in2,
            int in3,
            int in4,
            int in5,
            int in6,
            int inDig,
            int outDig,
            int carga) {

        this.idEquipo = idEq;
        this.in1 = in1;
        this.in2 = in2;
        this.in3 = in3;
        this.in4 = in4;
        this.in5 = in5;
        this.in6 = in6;
        this.inDig = inDig;
        this.outDig = outDig;
        this.carga = carga;

    }

    /**
     * Retorna identificador de partición
     * @return
     */
    public int getIdPartition() {
        return this.idPartition;
    }

    /**
     * @return the id
     */
    public String getIdEquipo() {
        return idEquipo;
    }

    /**
     * @param id the id to set
     */
    public void setIdEquipo(String id) {
        this.idEquipo = id;
    }

    /**
     * @return the fechaHora
     */
    public String getFechaHora() {
        return sdfOutFechaHora.format(fechaHora);
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public boolean setFechaHora(String fechaHora) {
        try {
            this.fechaHora = sdfIn.parse(fechaHora);
            this.idPartition = Integer.parseInt(sdfId.format(this.fechaHora));
            return true;
        } catch (ParseException ex) {
            String msj = "No se pudo convertir la fecha y hora";
            if (UtilidadesApp.getDebugMode()) {
                System.out.println(msj + " [" + fechaHora + "]");
            } else {
                UtilidadesApp.logError.info(msj + " [" + fechaHora + "]");
            }
            return false;
        }
    }

    /**
     * @return the in1
     */
    public double getIn1() {
        return in1;
    }

    /**
     * @param in1 the in1 to set
     */
    public void setIn1(EntCanal cn) {
        if (cn != null) {
            this.in1 = cn.convertirValor(this.in1);
        } 
    }

    /**
     * @return the in2
     */
    public double getIn2() {
        return in2;
    }

    /**
     * @param in2 the in2 to set
     */
    public void setIn2(EntCanal cn) {
        if (cn != null) {
            this.in2 = cn.convertirValor(this.in2);
        }
    }

    /**
     * @return the in3
     */
    public double getIn3() {
        return in3;
    }

    /**
     * @param in3 the in3 to set
     */
    public void setIn3(EntCanal cn) {
        if (cn != null) {
            this.in3 = cn.convertirValor(this.in3);
        } 
    }

    /**
     * @return the in4
     */
    public double getIn4() {
        return in4;
    }

    /**
     * @param in4 the in4 to set
     */
    public void setIn4( EntCanal cn) {
        if (cn != null) {
            this.in4 = cn.convertirValor(this.in4);
        }
    }

    /**
     * @return the in5
     */
    public double getIn5() {
        return in5;
    }

    /**
     * @param in5 the in5 to set
     */
    public void setIn5(EntCanal cn) {
        if (cn != null) {
            this.in5 = cn.convertirValor(this.in5);
        }
    }

    /**
     * @return the in6
     */
    public double getIn6() {
        return in6;
    }

    /**
     * @param in6 the in6 to set
     */
    public void setIn6(EntCanal cn) {
        if (cn != null) {
            this.in6 = cn.convertirValor(this.in6);
        }
    }

    /**
     * @return the inDig
     */
    public String getInDig() {
        String binary = Integer.toBinaryString(inDig);
        if (binary.length() < 8) {
            int max = 8 - binary.length();
            for (int i = 0; i < max; i++) {
                binary = "0" + binary;
            }
        }
        return binary;
    }

    /**
     * @param inDig the inDig to set
     */
    public void setInDig(int inDig) {
        this.inDig = inDig;
    }

    /**
     * @return the outDig
     */
    public String getOutDig() {
        String binary = Integer.toBinaryString(outDig);
        if (binary.length() < 8) {
            int max = 8 - binary.length();
            for (int i = 0; i < max; i++) {
                binary = "0" + binary;
            }
        }
        return binary;
    }

    /**
     * @param outDig the outDig to set
     */
    public void setOutDig(int outDig) {
        this.outDig = outDig;
    }

    /**
     * @return the carga
     */
    public int getCarga() {
        return carga;
    }

    /**
     * @param carga the carga to set
     */
    public void setCarga(int carga) {
        this.carga = carga;
    }

    /**
     * Identificador de grupo (letras)
     */
    public String getIdGrupo() {
        return this.idEquipo.substring(0, 2);
    }

    /**
     * Identificador de equipo (parte 2)
     * @return
     */
    public String getIdEquipNum() {
        return this.idEquipo.substring(2, idEquipo.length());
    }
}
