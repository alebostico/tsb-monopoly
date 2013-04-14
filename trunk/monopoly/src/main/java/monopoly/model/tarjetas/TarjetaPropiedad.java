/**
 * 
 */
package monopoly.model.tarjetas;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public abstract class TarjetaPropiedad {
    
    private Jugador jugador;
    
    private Integer idTarjeta;
    
    private String descripcion;
    
    private Integer valorHipotecario;
    
    public TarjetaPropiedad(){
	
    }

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * @return the idTarjeta
     */
    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    /**
     * @param idTarjeta the idTarjeta to set
     */
    public void setIdTarjeta(Integer idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valorHipotecario
     */
    public Integer getValorHipotecario() {
        return valorHipotecario;
    }

    /**
     * @param valorHipotecario the valorHipotecario to set
     */
    public void setValorHipotecario(Integer valorHipotecario) {
        this.valorHipotecario = valorHipotecario;
    }
    
}
