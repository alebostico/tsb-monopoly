/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TurnoComparator implements Comparator<Jugador> {
	
	@Override
	public int compare(Jugador jugador1, Jugador jugador2) {
		return (jugador1.getTiradaInicial().getSuma() - jugador2.getTiradaInicial().getSuma());
	}
}
