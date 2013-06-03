/**
 * 
 */
package monopoly.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class ListaCircular<E> implements List<E> {

    private Nodo primero;


    public ListaCircular()
    {
        primero=null;
    }
    
    /**
     * Devuelve el primer elemento de la lista
     * @param c
     * @return
     */
    private Nodo getPrimero() {
        return primero;
    }
    
    /**
     * Setea el primer elemento de la lista
     * @param c
     * @return
     */
    private void setPrimero(Nodo primero) {
        this.primero = primero;
    }
    




    /**
     * retorna el tamanio de la lista
     * @param c
     * @return
     */
    @Override
    public int size()
    {
        Nodo aux=primero;
        int contador=0;
        if (aux!=null)
            contador++;
        if(aux==null)
            return contador;
        while(!aux.getProximo().equals(primero))
        {
            aux=aux.getProximo();
            contador++;
        }
       return contador;
    }

    /**
     * Devuelve true si la lista esta vacia
     * @param c
     * @return
     */
    @Override
    public boolean isEmpty() {
        return primero==null;
    }

    /**
     * Busca el objeto pasado por parametros y devuelve un boolean
     * @param c
     * @return true si lo encuentra, false si no lo encuentra
     */
    @Override
    public boolean contains(Object o) {

        Nodo aux=primero;

        if(aux.getDato().equals(o))
            return true;
        while(!aux.getProximo().equals(primero))
        {
            aux=aux.getProximo();
            if(aux.getDato().equals(o))
            return true;
        }
        return false;

    }
    

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public Iterator<E> iterator() {
       return new Iterador<E>();
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Agrega el objeto pasado por parametro a la lista
     * @param c
     * @return
     */
    @Override
    public boolean add(E dato) {
            Nodo n=new Nodo();
            n.setDato(dato);
            if(primero==null)
            {
                primero=n;
            }
            else
            {
                if(contains(dato))
                    return false;
                else
                {
                    Nodo aux=primero;

                    while(!aux.getProximo().equals(primero))
                    {
                        aux=aux.getProximo();
                    }
                    aux.setProximo(n);
                }
            }
            n.setProximo(primero);
            return true;
    }

    /**
     * Elimina el objeto pasado por parametro
     * @param c
     * @return
     */
    @Override
    public boolean remove(Object dato) {
 
        Nodo aux=primero;

        if(aux.getDato().equals(dato))
            {
                Nodo eliminado=primero;
                primero=primero.getProximo();
                aux=primero;

                if(aux.getProximo().equals(primero))
                {
                    clear();
                    return true;
                }

                while(true)
                {
                    aux=aux.getProximo();
                    if(aux.getProximo().equals(eliminado))
                    {
                        aux.setProximo(primero);
                        return true;
                    }
                }


            }

        Nodo anterior;
        while(!aux.getProximo().equals(primero))
        {
            anterior=aux;
            aux=aux.getProximo();
            if(aux.getDato().equals(dato))
            {
                anterior.setProximo(aux.getProximo());
                return true;
            }
        }
        return false;

  
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {


        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * borra todos los elementos de la lista
     * @param c
     * @return
     */
    @Override
    public void clear() {
        primero=null;
    }

    /**
     * Devuelve el objeto del indice especificado
     * @param c
     * @return
     */
    @Override
    public E get(int index) {

        Nodo aux=primero;
        int contador=1;
        if(contador==index)
            return (E)aux.getDato();
        while(!aux.getProximo().equals(primero))
        {
            aux=aux.getProximo();
            contador++;
            if(contador==index)
            return (E)aux.getDato();
        }
        return null;

    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo no implementado
     * @param c
     * @return
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Devuelve un string con la lista
     * @param c
     * @return
     */
    @Override
    public String toString()
    {

        Nodo aux=primero;
        String retorno="\n";
        if(primero==null)
            return "lista vacia";

        if(aux!=null)
            retorno+=aux.getDato().toString()+"\n";

        while(!aux.getProximo().equals(primero))
        {
            aux=aux.getProximo();
            retorno+=aux.getDato().toString()+"\n";

        }
        return retorno;
    }


    /**
     * mueve el inicio de la lista un lugar a la derecha
     */
    public void moverUnLugar()
    {
        primero=primero.getProximo();
    }

    /**
     * marca cual sera el primer elemento de la lista si existe, si no existe no hace nada
     */
    public void marcarPrimero(E dato)
    {
        {
            Nodo aux=primero;
            while(!aux.getProximo().equals(primero))
            {
                aux=aux.getProximo();
                if(aux.getDato().equals(dato))
                {
                    primero=aux;
                    return;
                }
            }
        }

    }


    public class Iterador<E> implements Iterator{

        private Nodo actual;

        private int indice=0;

        public Iterador() {
            this.actual = primero;
        }


        /**
         *devuelve true si quedan al menos un elemento por iterar
         * @param c
         * @return
         */
        @Override
        public boolean hasNext() {
           // return !actual.equals(primero);
           return indice<size();
           // return !actual.getProximo().equals(primero);
        }

        /**
         * devuelve el proximo elemento
         * @param c
         * @return
         */
        @Override
        public Object next() {
            Object proximo=actual.getDato();
            actual=actual.getProximo();
            indice++;
            return proximo;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }






}
