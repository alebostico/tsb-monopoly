/**
 * 
 */
package monopoly.util;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Nodo {

    private Object dato;
    private Nodo proximo;


    public Nodo()
    {

    }

    public Nodo(Object dato, Nodo proximo)
    {
        this.dato=dato;
        this.proximo=proximo;
    }


    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public Nodo getProximo() {
        return proximo;
    }

    public void setProximo(Nodo proximo) {
        this.proximo = proximo;
    }

    /**
     * compara considerando que no deben haber elementos repetidos en la lista
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Nodo aux = (Nodo) obj;
        if (!dato.equals(aux.dato)) {
            return false;
        }
        return true;
    }





}
