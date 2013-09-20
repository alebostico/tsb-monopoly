/**
 * 
 */
package monopoly.util;

/**
 * @author pablo
 * 
 */
public enum EnumMensaje {

	/**
	 * Declarar un tipo de Mensaje
	 */
	LOGIN("LOGIN", 1), INICIAR_PARTIDA("INICIAR_PARTIDA", 2);

	private final String msg;
	private final int idMsg;

	EnumMensaje(String msg, int idMsg) {
		this.msg = msg;
		this.idMsg = idMsg;
	}

	/**
	 * @return the idMensaje
	 */
	public String getMensaje() {
		return msg;
	}

	public final int getIdMensaje() {
		return idMsg;
	}
}
