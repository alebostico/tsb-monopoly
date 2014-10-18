/**
 * 
 */
package monopoly.controller;

import java.util.List;

import monopoly.model.Juego;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@SuppressWarnings("unused")
public class JuegoController {
	
	private Juego juego;
	
	private TarjetaController gestorTarjetas;
	
	private FichasController gestorFichas;
	
	public JuegoController(Juego juego)
	{
		this.juego = juego;
		gestorTarjetas = new TarjetaController(this.juego);
		gestorFichas = new FichasController();
	}


}
