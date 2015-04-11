/**
 * 
 */
package monopoly.controller;

import monopoly.model.Banco;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.exception.SinDineroException;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class BancoController {

	private Banco banco;

	public BancoController() {
		this.banco = new Banco(TarjetaController.tarjetasPropiedadesList(), 32,
				12);
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	/**
	 * resta del dinero del jugador el monto indicado
	 * 
	 * @param jugador
	 *            el jugador al que se quiere cobrar
	 * @param monto
	 *            el monto que se quiere cobrar
	 * @return true si puede cobrar el monto, false si no muede cobrar
	 * @throws SinDineroException
	 */
	public void cobrar(Jugador jugador, int monto) throws SinDineroException {
		if (!jugador.pagar(monto)) {
			throw new SinDineroException(
					String.format(
							"El jugador {0} no posee dinero suficiente para pagar €{1}",
							jugador.getNombre(), monto));
		}
	}

	/**
	 * suma al dinero del jugador el monto indicado
	 * 
	 * @param jugador
	 *            el jugador al que se debe pagar
	 * @param monto
	 *            el monto que se debe pagar
	 * @return true si se pudo pagar el monto
	 */
	public boolean pagar(Jugador jugador, int monto) {
		jugador.cobrar(monto);
		return true;
	}

	/**
	 * toma una propiedad en hipoteca y paga al jugador el valor indicado para
	 * la hipoteca de esta propiedad
	 * 
	 * @param jugador
	 *            el jugador que hipoteca la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad que hipoteca el jugador
	 * @return true so
	 */
	public boolean hipotecarPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) {
		if (this.pagar(jugador, tarjetaPropiedad.getValorHipotecario())) {
			tarjetaPropiedad.setHipotecada(true);
			return true;
		} else
			return false;
	}

	/**
	 * deshipoteca una propiedad indicada y cobra al jugador la hipoteca de esta
	 * propiedad mas el 10 %
	 * 
	 * @param jugador
	 *            el jugador que deshipoteca la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad que deshipoteca el jugador
	 * @throws SinDineroException
	 */
	public void deshipotecarPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) throws SinDineroException {
		this.cobrar(jugador,
				(int) (tarjetaPropiedad.getValorHipotecario() * 1.10));
		tarjetaPropiedad.setHipotecada(false);
	}

	/**
	 * vende una propiedad al jugador
	 * 
	 * @param jugador
	 *            el jugador que compra la propiedad
	 * @param tarjetaPropiedad
	 *            la propiedad compra el jugador
	 * @throws SinDineroException
	 */
	public void venderPropiedad(Jugador jugador,
			TarjetaPropiedad tarjetaPropiedad) throws SinDineroException {
		this.cobrar(jugador, tarjetaPropiedad.getValorPropiedad());
		jugador.adquirirPropiedad(tarjetaPropiedad);

	}

}
