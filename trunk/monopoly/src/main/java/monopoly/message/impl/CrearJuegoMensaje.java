/**
 * 
 */
package monopoly.message.impl;

import monopoly.model.Juego;
import monopoly.util.message.IMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CrearJuegoMensaje implements IMensaje {

	private Juego juego;

	public CrearJuegoMensaje(Juego juego) {
		this.juego = juego;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.util.message.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.message.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.message.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return null;
	}

}
