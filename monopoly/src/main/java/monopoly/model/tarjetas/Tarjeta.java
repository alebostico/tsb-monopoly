package monopoly.model.tarjetas;

import java.io.Serializable;


public abstract class Tarjeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703546102718210932L;
    
    public boolean isTarjetaSuerte(){
    	return this instanceof TarjetaSuerte;
    }
    
    public boolean isTarjetaComunidad(){
    	return this instanceof TarjetaComunidad;
    }
    
    public boolean isTarjetaPropiedad()
    {
    	return this instanceof TarjetaPropiedad;
    }
}
