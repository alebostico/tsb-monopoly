package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteSiete extends Carta{
    
    
    public CartaSuerteSiete(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: ADELANTATE HASTA LA CALLE VERMUDEZ, SI PASAS POR LA CASILLA DE SALIDA COBRA 200 EUROS
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	
	return true;
    }


    
    

}
