/**
 * 
 */
package monopoly.model;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public abstract class TarjetaPropiedad {
    
    private Jugador jugador;
    
    
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
    
    

}
