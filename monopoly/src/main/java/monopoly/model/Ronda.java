/**
 * 
 */
package monopoly.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monopoly.util.ListaCircular;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Ronda{

    private ListaCircular<Jugador> jugadores;

    public Ronda()
    {
       jugadores=new ListaCircular<Jugador>();
    }

    public Ronda(List<Jugador> jugadores) {
        this.jugadores=new ListaCircular<Jugador>();
        for(Jugador j:jugadores)
            this.jugadores.add(j);
    }

    /**
     * Agrega un jugador a la lista existente
     * @param c
     * @return
     */
    public boolean agregarJugador(Jugador j)
    {
        return jugadores.add(j);
    }
    
    
    /**
     * Elimina el jugador especificado de la lista
     * @param c
     * @return
     */

    public boolean eliminarJugador(Jugador j)
    {
        return jugadores.remove(j);
    }

    /**
     * Devuelve el jugador de la fica especificada 
     * @param c
     * @return
     */
    public Jugador getJugador(Ficha f)
    {
        Jugador j=null;
        Iterator<Jugador> i=jugadores.iterator();
        while(i.hasNext())
        {
            j=(Jugador)i.next();
            if(j.getFicha().equals(f))
                return j;
        }

        return null;

    }
    
    /**
     * Devuelve el numero de jugadores en la lista
     * @param c
     * @return
     */
    public int tamanio()
    {
        return jugadores.size();
    }
    
    /**
     * Marca el primer jugador de la ronda
     * @param c
     * @return
     */

    public void marcarPrimerJugador(Jugador j)
    {
        jugadores.marcarPrimero(j);
    }
    
    /**
     * Mueve el inicio de la ronda un lugar
     * @param c
     * @return
     */
    public void moverUnLugar()
    {
        jugadores.moverUnLugar();
    }
    
    
    /**
     * Busca el objeto pasado por parametros y devuelve true si lo encuentra
     * @param c
     * @return
     */

    public boolean existeJugador(Jugador j)
    {
        return jugadores.contains(j);
    }
    
    /**
     * Devuelve el jugador de la derecha al actual (el siguiente en la ronda)
     * @param c
     * @return
     */
    public Jugador getJugadorDerecha(Jugador j)
    {
        Jugador derecha=null;
        Iterator i=jugadores.iterator();
        while(i.hasNext())
        {
            Jugador actual=(Jugador)i.next();
            if(actual.equals(j))
            {
                if(i.hasNext())
                {
                    derecha=(Jugador)i.next();
                    return derecha;
                }
                else
                {
                    return (Jugador)jugadores.get(1);
                }
            }
        }
        return derecha;
    }

    /**
     * Devuelve la lista completa de jugadores
     * @param c
     * @return
     */
    public ListaCircular<Jugador> getJugadores() {
        return jugadores;
    }




    public ArrayList<Jugador> getListaJugadores()
    {
        if(!jugadores.isEmpty())
        {
            ArrayList lista=new ArrayList();
            Iterator i=jugadores.iterator();
            while(i.hasNext())
            {
                lista.add(i.next());
            }
            return lista;
        }
        else
            return null;
    }

    @Override
    public String toString() {
	return jugadores.toString();
    }
    
    


}



