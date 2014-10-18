/**
 * 
 */
package monopoly.message.impl;

import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.IMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class SalirJuegoMensaje implements IMensaje {

	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
			//int idThreadCliente = Integer.parseInt(vCadena[1]);
			
		return null;
	}

	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		String[] vStr = new String[] {ConstantesMensaje.CLOSE_GAME, vCadena[0]};
		
		return String.join(ConstantesMensaje.DELIMITADOR, vStr );
	}

	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return null;
	}

}
