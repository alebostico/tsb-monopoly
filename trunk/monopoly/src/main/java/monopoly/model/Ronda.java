/**
 * 
 */
package monopoly.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


    public boolean agregarJugador(Jugador j)
    {
        return jugadores.add(j);
    }

    public boolean eliminarJugador(Jugador j)
    {
        return jugadores.remove(j);
    }

    
    public Jugador getJugador(Color c)
    {
        Jugador j=null;
        Iterator i=jugadores.iterator();
        while(i.hasNext())
        {
            j=(Jugador)i.next();
            if(j.getColor().equals(c))
                return j;
        }

        return null;

    }
    

    public int tamanio()
    {
        return jugadores.size();
    }

    public void marcarPrimerJugador(Jugador j)
    {
        jugadores.marcarPrimero(j);
    }

    public void moverUnLugar()
    {
        jugadores.moverUnLugar();
    }

    public boolean existeJugador(Jugador j)
    {
        return jugadores.contains(j);
    }

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
                    return (Jugador)jugadores.getPrimero().getDato();
                }
            }
        }
        return derecha;
    }

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


}


//public class Ronda<E> implements Iterable{
//
//    private Nodo primero;
//
//    public Ronda()
//    {
//        primero=null;
//    }
//
//    public Nodo getPrimero() {
//        return primero;
//    }
//
//    public void setPrimero(Nodo primero) {
//        this.primero = primero;
//    }
//
//
//    /**
//     * Agrega un objeto al final de la lista, solo si el objeto no existe
//     * @param dato
//     * @return true si lo pudo agregar
//     */
//    public boolean  agregar(E dato)
//    {
//            Nodo n=new Nodo();
//            n.setDato(dato);
//            if(primero==null)
//            {
//                primero=n;
//            }
//            else
//            {
//                if(existe(dato))
//                    return false;
//                else
//                {
//                    Nodo aux=primero;
//
//                    while(!aux.getProximo().equals(primero))
//                    {
//                        aux=aux.getProximo();
//                    }
//                    aux.setProximo(n);
//                }
//            }
//            n.setProximo(primero);
//            return true;
//
//    }
//
//
//    public boolean existe(E dato)
//    {
//        Nodo aux=primero;
//        while(!aux.getProximo().equals(primero))
//        {
//            aux=aux.getProximo();
//            if(aux.getDato().equals(dato))
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Elimina el objeto pasado por parametros si existe, sino existe no hace nada (devuelve false)
//     * @param dato
//     * @return true si lo pudo eliminar
//     */
//    public boolean eliminar(E dato)
//    {
//        Nodo aux=primero;
//        Nodo anterior=null;
//        while(!aux.getProximo().equals(primero))
//        {
//            anterior=aux;
//            aux=aux.getProximo();
//            if(aux.getDato().equals(dato))
//            {
//                anterior.setProximo(aux.getProximo());
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * retorna el tamaño de la lista
//     * @return
//     */
//    public int tamanio()
//    {
//        Nodo aux=primero;
//        int contador=0;
//        if (aux!=null)
//            contador++;
//        while(!aux.getProximo().equals(primero))
//        {
//            aux=aux.getProximo();
//            contador++;
//        }
//       return contador;
//    }
//
//
//    @Override
//    public String toString()
//    {
//        Nodo aux=primero;
//        String retorno="";
//        if(aux!=null)
//            retorno+=aux.getDato().toString()+"\n";
//        while(!aux.getProximo().equals(primero))
//        {
//            aux=aux.getProximo();
//            retorno+=aux.getDato().toString()+"\n";
//
//        }
//        return retorno;
//    }
//
//    @Override
//    public Iterador iterator() {
//        return new Iterador();
//    }
//
//
//    /**
//     * mueve el inicio de la lista un lugar
//     */
//    public void moverUnLugar()
//    {
//        primero=primero.getProximo();
//    }
//
//    /**
//     * marca cual sera el primer elemento de la lista si existe, si no existe no hace nada
//     */
//    public void marcarPrimero(E dato)
//    {
//        {
//            Nodo aux=primero;
//            while(!aux.getProximo().equals(primero))
//            {
//                aux=aux.getProximo();
//                if(aux.getDato().equals(dato))
//                {
//                    primero=aux;
//                    return;
//                }
//            }
//        }
//
//    }
//
//
//
//
//    public class Iterador implements Iterator{
//
//        private Nodo actual;
//
//        private int indice=0;
//
//        public Iterador() {
//            this.actual = primero;
//        }
//
//
//        @Override
//        public boolean hasNext() {
//           // return !actual.equals(primero);
//           return indice<tamanio();
//           // return !actual.getProximo().equals(primero);
//        }
//
//        @Override
//        public Object next() {
//            Object proximo=actual.getDato();
//            actual=actual.getProximo();
//
//            indice++;
//
//            return proximo;
//        }
//
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//    }
//
//
//
//}
