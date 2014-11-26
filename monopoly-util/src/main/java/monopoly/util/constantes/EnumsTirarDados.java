/**
 * 
 */
package monopoly.util.constantes;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class EnumsTirarDados {

	public enum TirarDados {
		TURNO_INICIO("turno inicio"), 
		AVANZAR_CASILLERO("avanzar casillero");

		private final String nombre;

		TirarDados(String nombre) {
			this.nombre = nombre;
		}

		public String getNombre() {
			return nombre;
		}
		
	}
}
