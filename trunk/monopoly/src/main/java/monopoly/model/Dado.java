package monopoly.model;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Dado {
    
    
    /**
     * Devuelve un entero aleatorio entre 1 y 6
     * @param c
     * @return
     */
    public static int tirarDado()
    {
	 return (int)(Math.random()*6+1);
    }
    



}
