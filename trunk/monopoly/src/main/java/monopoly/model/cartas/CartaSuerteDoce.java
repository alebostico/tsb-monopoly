package monopoly.model.cartas;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class CartaSuerteDoce extends Carta{
    
    
    public CartaSuerteDoce(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);
	// TODO Auto-generated constructor stub
    }
    


    /**
     * Instruccion: HAS REPARACIONES EN TODOS TUS EDIFICIOS, PAGA POR CADA CASA 25 EUROS Y POR CADA HOTEL 100 EUROS 
     * @param c
     * @return
     */
    @Override
    
    //ver este metodo
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	for (TarjetaPropiedad tp : k.getTarjPropiedadList()) {
	    if(tp.getNombre().equals("casa"))	
	    {
		k.setDinero(k.getDinero()-25);
	    }
	    if(tp.getNombre().equals("hotel"))
	    {
		k.setDinero(k.getDinero()-100);
	    }
	    
	}
	return true;
    }


    
    

}
