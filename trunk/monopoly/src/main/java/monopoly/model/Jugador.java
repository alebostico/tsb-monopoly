/**
 * 
 */
package monopoly.model;

import java.util.List;

import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Jugador {
    
    private Ficha ficha;
    private List<TarjetaPropiedad> tarjPropiedadList;
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
     * @return the tarjPropiedadList
     */
    public List<TarjetaPropiedad> getTarjPropiedadList() {
        return tarjPropiedadList;
    }

    /**
     * @param tarjPropiedadList the tarjPropiedadList to set
     */
    public void setTarjPropiedadList(List<TarjetaPropiedad> tarjPropiedadList) {
        this.tarjPropiedadList = tarjPropiedadList;
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
    
    /**
     * devuelve true si el jugador es de la ficha especificada
     * @param c
     * @return
     */
    public boolean equals(Jugador j) {
        return this.getFicha().equals(j.getFicha());
    }
    
}
