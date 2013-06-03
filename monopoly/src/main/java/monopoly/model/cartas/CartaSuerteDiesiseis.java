package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteDiesiseis extends Carta{
    
    
    public CartaSuerteDiesiseis(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: VE A LA ESTACION LAS DELICIAS, SI PASAS POR LA CASILLA DE SALIDA COBRAS 200 EUROS
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	
	return true;
    }


    
    

}
