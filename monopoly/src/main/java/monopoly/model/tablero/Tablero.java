/**
 * 
 */
package monopoly.model.tablero;

import java.util.List;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Tablero {
    
    private List<Casillero> casillerosList;

    
    /**
     * default constructor
     */
    public Tablero(){
	
    }


    /**
     * @return the casillerosList
     */
    public List<Casillero> getCasillerosList() {
        return casillerosList;
    }


    /**
     * @param casillerosList the casillerosList to set
     */
    public void setCasillerosList(List<Casillero> casillerosList) {
        this.casillerosList = casillerosList;
    }
   
}
