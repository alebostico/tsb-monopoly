package monopoly.model.cartas;
import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteCatorce extends Carta{
    
    
    public CartaSuerteCatorce(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: QUEDAS LIBRE DE LA CARCEL ESTA TARJETA SE PUEDE GUARDAR
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	k.setDinero(k.getDinero()+50);
	return true;
    }


    
    

}
