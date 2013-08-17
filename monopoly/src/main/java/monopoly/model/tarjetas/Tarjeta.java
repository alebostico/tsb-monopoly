package monopoly.model.tarjetas;

import java.util.List;

import monopoly.model.Banco;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Tablero;

public abstract  class Tarjeta {
    
    
    public abstract boolean jugarTarjeta(Juego j, Jugador k);
    
    /**
     * Paga al jugador la cantidad indicada
     */
    protected boolean pagar(Banco b, Jugador j, int c )
    {
	return b.pagar(j, c);
    }
    
    /**
     * Cobra al jugador la cantidad indicada
     */
    protected boolean cobrar(Banco b, Jugador j, int c)
    {
	return b.cobrar(j, c);
    }
    
    /**
     * Cobra a todos los jugadores, menos al indicado
     * Paga al jugador indicado la cantidad
     */
    protected boolean cobrarAtodosPagarAuno(Banco b, List<Jugador> js, Jugador j, int c)
    {
	return b.cobrarATodosPagarAUno(js, j, c);
    }
    
    /**
     * Mueve el jugador a la carcel, sin cobrar los 200
     */
    protected Casillero moverAcarcel(Tablero t, Jugador j)
    {
	return t.irACarcel(j);
    }
    
    /**
     * Mueve el jugador a la casilla indicada, si pasa por la salida, paga 200
     */
    protected Casillero moverA(Tablero t, int idCasillero, Jugador j)
    {
	return t.moverACasillero(j, idCasillero);
    }
    
    /**
     * Mueve al jugador a la casilla indicada, retrocediendo, no cobra 200
     */
    protected Casillero retrosederA(Tablero t, int idCasillero, Jugador j)
    {
	return t.retrocederA(j, idCasillero);
    }
    
    
    
    
    
    
    
    
    
    

}
