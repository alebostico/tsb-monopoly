package monopoly.model.cartas;

import monopoly.model.Juego;
import monopoly.model.Jugador;

public class CartaSuerteDos extends Carta {


    public CartaSuerteDos(String texto, String imagen, String tipo) {
	super(texto, imagen, tipo);

    }

    /**
     * Instruccion: MULTA POR EXCESO DE VELOCIDAD 
     * @param c
     * @return
     */
    @Override
    boolean cumplirInstrucciones(Juego j, Jugador k) {
	k.setDinero(k.getDinero()-15);
	return true;
    }
}
