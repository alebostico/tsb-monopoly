package monopoly.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

	/**
	 * Método para deordenar listas. También se puede usar
	 * java.utils.Collections.shuffle()
	 * 
	 * @param lista
	 *            La lista que se quiere desordenar
	 * @return La lista desordenada
	 */
	public static <T> List<T> randomizeList(List<T> lista) {

		List<T> tmpList = new ArrayList<T>();
		Random rRandom = new Random();
		int iRandom;
		int low = 0;
		int high;

		while (!lista.isEmpty()) {
			high = lista.size();
			iRandom = rRandom.nextInt(high - low) + low;

			tmpList.add(lista.remove(iRandom));
		}

		return tmpList;
	}

}
