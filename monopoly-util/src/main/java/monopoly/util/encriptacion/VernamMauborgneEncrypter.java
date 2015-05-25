package monopoly.util.encriptacion;


/**
 * Encripta una cadena en base a la técnica de Vernam - Mauborgne. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposición es creada 
 * y almacenada internamente por la misma clase. La clave de desplazamiento es creada por la propia 
 * clase, del mismo largo que el mensaje a encriptar (de acuerdo a la idea de Vernam), y cada uno de
 * los caracteres de esa clave se elige en forma aleatoria desde los símbolos de alfabeto base (de 
 * acuerdo a la idea de Mauborgne).
 * Patrón aplicado: Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Julio de 2009
 */
public class VernamMauborgneEncrypter extends VigenereEncrypter
{
       /**
        *  Inicia un encriptador con técnica de Vernam-Mauborgne. El mensaje a encriptar será inicializado 
        *  como la cadena vacía ("") y el programador deberá cambiar luego ese valor mediante setOpenMessage(). 
        */
       public VernamMauborgneEncrypter( )
       {
           this(null);
       }
                    
       /**
        *  Inicia un encriptador con técnica de Vernam-Mauborgne. El mensaje a encriptar se inicializa 
        *  con el valor del parámetro mens. Si ese parámetro es null, el mensaje a encriptar se inicia como 
        *  la cadena vacía ("") y luego el programador deberá cambiar ese valor con setOpenMessage().
        *  @param mens el mensaje abierto que será encriptado.
        */
       public VernamMauborgneEncrypter( String mens )
       {
           super(mens);
           clave = null;
           if ( mensaje.equals("") ) return;
           createKey();
       }
       
    /**
     *  Cambia el mensaje a encriptar. Si el nuevo mensaje es null, el mensaje a encriptar se inicia como cadena 
     *  vacía (""), lo que debería provocar que una invocación a code() retorne null. En todos los casos, también 
     *  se inicia como cadena vacía el mensaje encriptado, lo que debería provocar que una nueva invocación a decode() 
     *  retorne null. La clave de desplazamiento a usar, se vuelve a crear en forma aleatoria, y se ajusta al tamaño
     *  del nuevo mensaje.
     *  @param mens el nuevo mensaje a encriptar.
     */
    public void setOpenMessage( String mens )
    {
        super.setOpenMessage( mens );
        createKey();
    }
       
       /**
        * Retorna una cadena con información general sobre el encriptador.
        * @return un cadena con información del encriptador.
        */
       public String toString()
       {
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptación conocida: " + encriptado;
           return res + "\nTécnica: Cifrado de Vernam - Mauborgne" + "\nTabla usada: " + transposicion + "\nClave: " + clave;
       }

       // crea una clave del mismo largo que el mensaje, combinando n símbolos aleatoriamente 
       // elegidos del alfabeto base.
       private void createKey ()
       {
           int n = mensaje.length();
           int na = ALFABETO.length();
           StringBuffer key = new StringBuffer(n);
           for( int i = 0; i < n; i++ )
           {
                int ind = (int) ( Math.random() * 1000 ) % na;
                key.append( ALFABETO.charAt(ind) );
           }
           clave = key.toString();
       }    
}