/**
 * 
 */
package monopoly.util;

import java.util.Comparator;

import monopoly.model.Jugador;

/**
 * Clase para ordenar los jugadores según el orden de juego.
 * <ul>
 * <li>Para ordenar los jugadores de menor a mayor:<br>
 * {@code Collections.sort(jugadores, new TurnoComparator());}
 * <li>Para ordenarlos de mayor a menor: <br>
 * {@code Collections.sort(jugadores,
 * Collections.reverseOrder(new TurnoComparator()));}
 * </ul>
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TurnoComparator implements Comparator<Jugador> {

	@Override
	public int compare(Jugador jugador1, Jugador jugador2) {
		return (jugador1.getTiradaInicial().getSuma() - jugador2
				.getTiradaInicial().getSuma());
	}
}
