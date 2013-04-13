/**
 * 
 */
package monopoly.model;

import java.util.List;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Juego {
    
    private Banco banco;
    
    private List<Jugador> jugadoresList; // cambiar por lista circular

    private Tablero tablero;
    
    private List<TarjetaPropiedad> tarjetasPropiedadList; // cambiar por lista circular
    
    public Juego(){
	
    }

    /**
     * @return the banco
     */
    public Banco getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    /**
     * @return the jugadores
     */
    public List<Jugador> getJugadoresList() {
        return jugadoresList;
    }

    /**
     * @param jugadores the jugadores to set
     */
    public void setJugadoresList(List<Jugador> jugadores) {
        this.jugadoresList = jugadores;
    }

    /**
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @param tablero the tablero to set
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * @return the tarjetasPropiedadList
     */
    public List<TarjetaPropiedad> getTarjetasPropiedadList() {
        return tarjetasPropiedadList;
    }

    /**
     * @param tarjetasPropiedadList the tarjetasPropiedadList to set
     */
    public void setTarjetasPropiedadList(List<TarjetaPropiedad> tarjetasPropiedadList) {
        this.tarjetasPropiedadList = tarjetasPropiedadList;
    }
    
}
