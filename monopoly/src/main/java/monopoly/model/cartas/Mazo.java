package monopoly.model.cartas;

import java.util.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Mazo {
    
    private ArrayList<Carta> mazo;
    
    
    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(ArrayList<Carta> mazo) {
        this.mazo = mazo;
    }

    /**
     * Mezcla el maso de cartas
     * @param obj
     * @return
     */
    public void mezclar()
    {
        Carta c=null;
        for(int j=0;j<100;j++)
        {
            int indice=(int)(Math.random()*(mazo.size()));
            c=mazo.get(indice);
            mazo.remove(indice);
            mazo.add((int)(Math.random()*(mazo.size())),c);
        }
    }
    
    /**
     * Agrega la carta especificada al final del maso
     * @param obj
     * @return
     */
    public void agregar(Carta c) {
	
       if(c.getClass()!=mazo.get(0).getClass() )
           mazo.add(c);
    }
    
    /**
     * Devulve la primera carta del mazo y la lleva al final del mazo
     * @param obj
     * @return
     */
    public Carta robarPrimera()
    {
	Carta robada=mazo.get(0);
	mazo.remove(robada);
	mazo.add(robada);
	return robada;	
    }

}
