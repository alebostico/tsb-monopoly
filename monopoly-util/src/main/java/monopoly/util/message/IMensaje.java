/**
 * 
 */
package monopoly.util.message;

/**
 * @author pablo
 *
 */
public interface IMensaje {
		
	public String decodificarMensaje(String[] vCadena);
	
	public String codificarMensaje(String[] vCadena);
		
	public String getTipoMensaje();
	
//	public IMensaje siguienteMensaje();

}
