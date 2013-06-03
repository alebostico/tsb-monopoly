package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteDiez extends Carta{
    
    
    public CartaSuerteDiez(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: MULTA POR EMBRIAGUEZ 20 EUROS
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	k.setDinero(k.getDinero()-20);
	return true;
    }


    
    

}
