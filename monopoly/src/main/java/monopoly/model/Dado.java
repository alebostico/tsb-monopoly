package monopoly.model;

import java.io.Serializable;

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Dado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979691718041435144L;
	
	private int[] dados;
	
	public Dado(){
		 dados = new int[2];
		 tirarDados();
	}
	
	public static String getPathImageDado(int numero){
		switch (numero) {
		case 1:
			return "/images/dados/dado_1.png";
		case 2:
			return "/images/dados/dado_2.png";
		case 3:
			return "/images/dados/dado_3.png";
		case 4:
			return "/images/dados/dado_4.png";
		case 5:
			return "/images/dados/dado_5.png";
		case 6:
			return "/images/dados/dado_6.png";
		default:
			break;
		}
		return "";
	}

	/**
	 * Devuelve un entero aleatorio entre 1 y 6
	 * 
	 * @param c
	 * @return
	 */
	private int tirarDado() {
		return (int) (Math.random() * 6 + 1);
	}

	/**
	 * Tira los dos dados y devuelve la suma de los dos.
	 * 
	 * @return La suma de los dos dados.
	 */
	private int tirarDados() {
		dados[0] = this.tirarDado();
		dados[1] = this.tirarDado();

		GestorLogs.registrarDebug("Dados tirados: " + getValorDado(1) + " + "
				+ getValorDado(2) + " = " + getSuma());

		return dados[0] + dados[1];
	}
	
	/**
	 * Tira los dos dados y devuelve el valor de cada dado.
	 * 
	 * @return La suma de los dos dados.
	 */
	public int[] getValorDados() {
		return dados;
	}

	/**
	 * Retorna el valor del dado pasado por parámetro de la última tirada de
	 * dados.
	 * 
	 * @param nroDado
	 *            El número del cual se quiere saber el valor que salió en la
	 *            última tirada.
	 * @return El valor de la última tirada de dados del dado 'nroDado'.
	 */
	public int getValorDado(int nroDado) {
		switch (nroDado) {
		case 1:
			return dados[0];
		case 2:
			return dados[1];
		default:
			return 0;
		}
	}

	/**
	 * Devuelve la suma de los dos dados de la última tirada.
	 * 
	 * @return
	 */
	public int getSuma() {
		return dados[0] + dados[1];
	}
}
