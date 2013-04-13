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
public class Tablero {
    
    private List<Casillero> casilleros;

    
    /**
     * default constructor
     */
    public Tablero(){
	
    }
    /**
     * @return the casilleros
     */
    public List<Casillero> getCasilleros() {
        return casilleros;
    }

    /**
     * @param casilleros the casilleros to set
     */
    public void setCasilleros(List<Casillero> casilleros) {
        this.casilleros = casilleros;
    }
    
}
