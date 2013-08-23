/**
 * 
 */
package monopoly.controller;

import java.util.List;

import monopoly.model.Banco;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaComunidad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class GestorTarjeta {

    /**
     * Ejecuta la accion indicada en la tarjeta comunidad
     * 1,PAGA POR TU POLIZA DE SEGUROS 50
     * 2,EN TU CUMPLEANIOOS RECIBES DE CADA JUGADOR 10
     * 3,COLOCATE EN LA CASILLA DE SALIDA
     * 4,PAGA LA FACTURA DEL MEDICO 50
     * 5,HAS GANADO EL SEGUNDO PREMIO DE BELLEZA, RECIBE 10
     * 6,ERROR DE LA BANCA A TU FAVOR, RECIBE 200
     * 7,VE A LA CARCEL, VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y SIN COBRAR LOS 200
     * 8,HACIENDA TE DEVUELVE 20
     * 9,COBRAS UNA HERENCIA DE 100
     * 10,RECIBE 100 POR LOS INTERESES DE TU PLAZO FIJO
     * 11,PAGA AL HOSPITAL 100
     * 12,RETROCEDE HASTA LA RONDA DE VALENCIA
     * 13,QUEDAS LIBRE DE LA CARCEL, ESTA TARJETA PUEDE VENDERSE O CONSERVARSE HASTA UTILIZARSE
     * 14,LA VENTA DE TUS ACCIONES TE PRODUCE 50
     * @param juego 
     * 			el juego actual
     * @param jugador
     * 			el jugador que tiene la tarjeta
     * @param tarjetaComunidad
     * 			la tarjeta que tiene el jugador
     * @return true si se pudo ejecutar la accion
     *
     */
    
    public boolean jugarTarjetaComunidad(Juego juego, Jugador jugador, Tarjeta tarjetaComunidad)
    {
	switch (((TarjetaComunidad) tarjetaComunidad).getIdTarjeta()) {
	    case  1: return juego.getBanco().pagar(jugador, 50);
	    case  2: return juego.getBanco().cobrarATodosPagarAUno(juego.getJugadoresList(), jugador, 10);
	    case  3: return (juego.getTablero().moverACasillero(jugador, 1, true)!=null);//salida
	    case  4: return juego.getBanco().pagar(jugador, 50);
	    case  5: return juego.getBanco().cobrar(jugador, 10);
	    case  6: return juego.getBanco().cobrar(jugador, 200);
	    case  7: return (juego.getTablero().irACarcel(jugador)!=null);
	    case  8: return juego.getBanco().pagar(jugador, 20);
	    case  9: return juego.getBanco().pagar(jugador, 100);
	    case 10: return juego.getBanco().pagar(jugador, 100);
	    case 11: return juego.getBanco().cobrar(jugador, 100);
	    case 12: return (juego.getTablero().retrocederA(jugador, 2)!=null);
	    //TODO: como hago aca?
	    case 13: jugador.getTarjetaCarcelList().add(tarjetaComunidad); return true;
	    case 14: return juego.getBanco().pagar(jugador, 50);
	    default:
		return false;
	}
    }
    
    /**
     * Ejecuta la accion indicada en la tarjeta suerte
     * 1,VE AL PASEO DEL PRADO
     * 2,VE A LA GLORIETA DE BILBAO. SI PASAS POR LA CASILLA DE SALIDA COBRA 200
     * 3,LA BANCA TE PAGA 50 DE INTERESES
     * 4,COLOCATE EN LA CASILLA DE SALIDA
     * 5,ADELANTATE HASTA LA CALLE CEA BERMUDEZ. SI PASAS POR LA CASILLA DE SALIDA, COBRA 200
     * 6,RECIBES EL RESCATE POR EL SEGURO DE TUS EDIFICIOS. COBRA 150
     * 7,VE A LA CARCEL. VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y SIN COBRAR LOS 200
     * 8,MULTA POR EMBRIAGUEZ 20
     * 9,RETROCEDE TRES CASILLAS
     * 10,HAZ REPARACIONES EN TODOS TUS EDIFICIOS. PAGA POR CADA CASA 25. PAGA POR CADA HOTEL 100
     * 11,LA INSPECCION DE LA CALLE TE OBLIGA A REPARACIONES. PAGA 40.POR CADA CASA. PAGA 115 POR HOTEL
     * 12,QUEDAS LIBRE DE LA CARCEL. Esta carta puede venderse o conservarse hasta que sea utilizada
     * 13,PAGA POR GASTOS ESCOLARES 150
     * 14,VE A LA ESTACIoN DE LAS DELICIAS. SI PASAS POR LA CASILLA DE SALIDA,COBRA 200
     * @param juego 
     * 			el juego actual
     * @param jugador
     * 			el jugador que tiene la tarjeta
     * @param tarjetaComunidad
     * 			la tarjeta que tiene el jugador
     * @return true si se pudo ejecutar la accion
     */
    
    public boolean jugarTarjetaSuerte(Juego juego, Jugador jugador, Tarjeta tarjetaSuerte)
    {
		switch (((TarjetaComunidad) tarjetaSuerte).getIdTarjeta()) {
		    case  1: return (juego.getTablero().moverACasillero(jugador, 40, false)!=null);//del prado
		    case  2: return (juego.getTablero().moverACasillero(jugador, 12, true)!=null);//glorieta de bilbao
		    case  3: return juego.getBanco().pagar(jugador, 50);
		    case  4: return (juego.getTablero().moverACasillero(jugador, 1, true)!=null);//salida
		    case  5: return (juego.getTablero().moverACasillero(jugador, 25, true)!=null);//calle bermudez
		    case  6: return juego.getBanco().pagar(jugador, 150);
		    case  7: return (juego.getTablero().irACarcel(jugador)!=null);
		    case  8: return juego.getBanco().cobrar(jugador, 20);
		    case  9: return (juego.getTablero().moverAtras(jugador, 3)!=null);
		    case 10: return juego.getBanco().cobrarPorCasaYHotel(jugador, 25, 100);
		    case 11: return juego.getBanco().cobrarPorCasaYHotel(jugador, 40, 115);
		    //TODO: como hago aca?
		    case 12: jugador.getTarjetaCarcelList().add(tarjetaSuerte); return true;
		    case 13: return juego.getBanco().cobrar(jugador, 150);
		    case 14: return (juego.getTablero().moverACasillero(jugador, 16, true)!=null);//las delicias
		    default:
			return false;
		}
    }
    
    
}
