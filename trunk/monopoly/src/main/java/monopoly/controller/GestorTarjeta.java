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
     * 1,PAGA POR TU POLIZA DE SEGUROS 50
     * 2,EN TU CUMPLEA�OS RECIBES DE CADA JUGADOR 10
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
     *
     */
    
    public boolean jugarTarjetaComunidad(Juego juego, Jugador jugador, Tarjeta tarjetaComunidad)
    {
	switch (((TarjetaComunidad) tarjetaComunidad).getIdTarjeta()) {
	    case  1: return juego.getBanco().pagar(jugador, 50);
	    case  2: return juego.getBanco().cobrarATodosPagarAUno(juego.getJugadoresList(), jugador, 10);
	    case  3: return (juego.getTablero().moverACasillero(jugador, 1)!=null);//salida
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
     * 
     */
    
    public boolean jugarTarjetaSuerte(Juego juego, Jugador jugador, Tarjeta tarjetaSuerte)
    {
	switch (((TarjetaComunidad) tarjetaSuerte).getIdTarjeta()) {
	    case  1: return (juego.getTablero().moverACasillero(jugador, 40)!=null);//del prado
	    case  2: return (juego.getTablero().moverACasillero(jugador, 12)!=null);//glorieta de bilbao
	    case  3: return juego.getBanco().pagar(jugador, 50);
	    case  4: return (juego.getTablero().moverACasillero(jugador, 1)!=null);//salida
	    case  5: return (juego.getTablero().moverACasillero(jugador, 25)!=null);//calle bermudez
	    case  6: return juego.getBanco().pagar(jugador, 150);
	    case  7: return (juego.getTablero().irACarcel(jugador)!=null);
	    case  8: return juego.getBanco().cobrar(jugador, 20);
	    case  9: return (juego.getTablero().moverAtras(jugador, 3)!=null);
	    case 10: return juego.getBanco().cobrarPorEdificioYHotel(jugador, 25, 100);
	    case 11: return juego.getBanco().cobrarPorEdificioYHotel(jugador, 40, 115);
	    //TODO: como hago aca?
	    case 12: jugador.getTarjetaCarcelList().add(tarjetaSuerte); return true;
	    case 13: return juego.getBanco().cobrar(jugador, 150);
	    case 14: return (juego.getTablero().moverACasillero(jugador, 16)!=null);//las delicias
	    default:
		return false;
	}
    }
    
    /**
     * Paga al jugador la cantidad indicada
     */
    protected boolean pagar(Banco banco, Jugador jugador, int monto) {
	return banco.pagar(jugador, monto);
    }

    /**
     * Cobra al jugador la cantidad indicada
     */
    protected boolean cobrar(Banco banco, Jugador jugador, int monto) {
	return banco.cobrar(jugador, monto);
    }

    /**
     * Cobra a todos los jugadores, menos al indicado Paga al jugador indicado la cantidad
     */
    protected boolean cobrarAtodosPagarAuno(Banco banco, List<Jugador> jugadores, Jugador jugador, int monto) {
	return banco.cobrarATodosPagarAUno(jugadores, jugador, monto);
    }

    /**
     * Mueve el jugador a la carcel, sin cobrar los 200
     */
    protected Casillero moverAcarcel(Tablero tablero, Jugador jugador) {
	return tablero.irACarcel(jugador);
    }

    /**
     * Mueve el jugador a la casilla indicada, si pasa por la salida, paga 200
     */
    protected Casillero moverA(Tablero tablero, int idCasillero, Jugador jugador) {
	return tablero.moverACasillero(jugador, idCasillero);
    }

    /**
     * Mueve al jugador a la casilla indicada, retrocediendo, no cobra 200
     */
    protected Casillero retrosederA(Tablero tablero, int idCasillero, Jugador jugador) {
	return tablero.retrocederA(jugador, idCasillero);
    }
    
}
