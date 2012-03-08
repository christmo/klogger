package entidades;

/**
 * @author qmarqeva
 */
public class EntCanal {
    private int idCanal;
    private double valSensor;
    private double valEqv;
    private String simb;

    public EntCanal(int idCanal, double valSensor, double valEqv, String simb) {
        this.idCanal = idCanal;
        this.valSensor = valSensor;
        this.valEqv = valEqv;
        this.simb = simb;
    }

    

    /**
     * @return the idCanal
     */
    public int getIdCanal() {
        return idCanal;
    }

    /**
     * @param idCanal the idCanal to set
     */
    public void setIdCanal(int idCanal) {
        this.idCanal = idCanal;
    }

    /**
     * @return the valSensor
     */
    public double getValSensor() {
        return valSensor;
    }

    /**
     * @param valSensor the valSensor to set
     */
    public void setValSensor(double valSensor) {
        this.valSensor = valSensor;
    }

    /**
     * @return the valEqv
     */
    public double getValEqv() {
        return valEqv;
    }

    /**
     * @param valEqv the valEqv to set
     */
    public void setValEqv(double valEqv) {
        this.valEqv = valEqv;
    }

    /**
     * @return the simb
     */
    public String getSimb() {
        return simb;
    }

    /**
     * @param simb the simb to set
     */
    public void setSimb(String simb) {
        this.simb = simb;
    }

    /**
     * Aplica los valores de conversión
     * @param in
     * @return
     */
    public double convertirValor(double in){
        return (in * valEqv)/valSensor;
    }

}
