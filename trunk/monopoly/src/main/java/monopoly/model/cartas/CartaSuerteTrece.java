package monopoly.model.cartas;
import monopoly.model.*;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteTrece extends Carta{
    
    
    public CartaSuerteTrece(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    
    

    /**
     * Instruccion: LA INSPECCION EN LA CALLE TE OBLIGA A PAGAR REPARACIONES
     * PAGA 40 EUROS POR CADA CASA Y 115 POR CADA HOTEL
     * @param c
     * @return
     */
    @Override
    
    //VER ESTE METODO
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	for (TarjetaPropiedad tp : k.getTarjPropiedadList()) {
	    if(tp.getNombre().equals("casa"))	
	    {
		k.setDinero(k.getDinero()-40);
	    }
	    if(tp.getNombre().equals("hotel"))
	    {
		k.setDinero(k.getDinero()-115);
	    }
	    
	}
	return true;
    }


    
    

}
