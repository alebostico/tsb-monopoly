package monopoly.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ListUtils {

	/**
	 * Método para desordenar listas. También se puede usar
	 * java.util.Collections.shuffle()
	 * 
	 * @param lista
	 *            La lista que se quiere desordenar
	 * @return La lista desordenada
	 */
	public static <T> List<T> randomizeList(List<T> lista) {

		List<T> tmpList = new ArrayList<T>();
		Random rRandom = new Random();

		while (!lista.isEmpty()) {
			tmpList.add(lista.remove(rRandom.nextInt(lista.size())));
		}

		return tmpList;
	}

}
