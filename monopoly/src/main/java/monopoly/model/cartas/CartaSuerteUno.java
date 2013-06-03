package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteUno extends Carta{
    
    
    public CartaSuerteUno(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: HAS GANADO UN CONCURSO DE CRUCIGRAMAS COBRAS 100 EUROS
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	k.setDinero(k.getDinero()+100);
	return true;
    }


    
    

}
