package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteNueve extends Carta{
    
    
    public CartaSuerteNueve(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: VE A LA CARCEL DIRECTAENTE SIN PASAR POR LA CASILLA DE SALIDA
     * NI COBRAR LOS 200 EUROS
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	
	return true;
    }


    
    

}
