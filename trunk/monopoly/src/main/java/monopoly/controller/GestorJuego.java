/**
 * 
 */
package monopoly.controller;

import java.util.List;

import monopoly.model.Juego;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class GestorJuego {

    private List<Juego> juegosList;

    /**
     * @return the juegosList
     */
    public List<Juego> getJuegosList() {
        return juegosList;
    }

    /**
     * @param juegosList the juegosList to set
     */
    public void setJuegosList(List<Juego> juegosList) {
        this.juegosList = juegosList;
    }

}
