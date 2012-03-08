package entidades;

/**
  * @author qmarqeva
 */
public class EntComando {
    private int idBD;
    private String comando;
    private int estado;
    private String resp;

    public EntComando(int idBD, String comando) {
        this.idBD = idBD;
        this.comando = comando;
        this.estado = 1;
    }



    /**
     * @return the idBD
     */
    public int getIdBD() {
        return idBD;
    }

    /**
     * @param idBD the idBD to set
     */
    public void setIdBD(int idBD) {
        this.idBD = idBD;
    }

    /**
     * @return the comando
     */
    public String getComando() {
        return comando;
    }

    /**
     * @param comando the comando to set
     */
    public void setComando(String comando) {
        this.comando = comando;
    }

    /**
     * @return the Estado donde
     * 1: Nuevo
     * 2: Esperando respuesta
     * 3: Completo
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param pendienteRta the pendienteRta to set
     */
    public void setEstado(int std) {
        this.estado = std;
    }

    /**
     * @return the resp
     */
    public String getResp() {
        return resp;
    }

    /**
     * @param resp the resp to set
     */
    public void setResp(String resp) {
        this.resp = resp;
    }

}
