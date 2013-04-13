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
public class Jugador {
    
    private Ficha ficha;
    private TarjetaPropiedad tarjPropiedad;
    private Juego juego;
    
    public Jugador(){
	
    }
    
    /**
     * @return the ficha
     */
    public Ficha getFicha() {
        return ficha;
    }
    /**
     * @param ficha the ficha to set
     */
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }
    /**
     * @return the tarjPropiedad
     */
    public TarjetaPropiedad getTarjPropiedad() {
        return tarjPropiedad;
    }
    /**
     * @param tarjPropiedad the tarjPropiedad to set
     */
    public void setTarjPropiedad(TarjetaPropiedad tarjPropiedad) {
        this.tarjPropiedad = tarjPropiedad;
    }
    /**
     * @return the juego
     */
    public Juego getJuego() {
        return juego;
    }
    /**
     * @param juego the juego to set
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    

}
