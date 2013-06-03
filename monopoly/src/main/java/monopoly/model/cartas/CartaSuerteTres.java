package monopoly.model.cartas;

import monopoly.model.Juego;
import monopoly.model.Jugador;


    public class CartaSuerteTres extends Carta {


    	    public CartaSuerteTres(String texto, String imagen, String tipo) {
    		super(texto, imagen, tipo);
    
    	    }
    
    	    /**
    	     * Instruccion: VE AL PASEO DEL PRADO 
    	     * @param c
    	     * @return
    	     */
    	    @Override
    	    boolean cumplirInstrucciones(Juego j, Jugador k) {

    		return true;
    	    }
    	}


