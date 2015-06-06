package monopoly.util.encriptacion;

/**
 * Encripta una cadena en base a una tabla de transposición. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposición es creada 
 * y almacenada internamente por la misma clase.
 * Patrón aplicado: Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Julio de 2009
 */
public class TranspositionEncrypter extends Encrypter
{
       // la tabla de transposicion...
       protected static final String transposicion = "xyzDEFGH.,;IJKLMNÑoPQRSTU56789VWXYZ abcdefgh:?!ijklmnñOpqrstu01234vwABC";
  
       /**
        *  Inicia un encriptador con técnica de tabla de transposición. El mensaje a encriptar será inicializado 
        *  como la cadena vacía ("") y el programador deberá cambiar luego ese valor mediante setOpenMessage().
        */
       public TranspositionEncrypter( )
       {
           this("");
       }
    
       /**
        *  Inicia un encriptador con técnica de tabla de transposición. El mensaje a encriptar se inicializa 
        *  con el valor del parámetro mens. Si ese parámetro es null, el mensaje a encriptar se inicia como 
        *  la cadena vacía ("") y luego el programador deberá cambiar ese valor con setOpenMessage().
        *  @param mens el mensaje abierto que será encriptado.
        */
       public TranspositionEncrypter( String mens )
       {
           super(mens);
       }
       
       /**
        * Retorna la tabla de transposición usada por el encriptador. El método está disponible sólo para
        * efectos didácticos...
        */
       public static String getTranspositionTable()
       {
           return transposicion;
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, según la técnica de tabla de transposición. 
        * Retorna null si el proceso de encriptación no pudo hacerse por haber caracteres extraños en el 
        * mensaje abierto. 
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         algún caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < mensaje.length(); i++) 
          {
              char actual = mensaje.charAt(i);
              int iactual = Encrypter.ALFABETO.indexOf( actual );
              
              b.append( transposicion.charAt( iactual ) );
          }
          
          encriptado = b.toString();
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la técnica de tabla de transposición.
        *  El método PUEDE CAMBIAR el valor del mensaje abierto almacenado en la clase (lo cual ocurrirá si se 
        *  invoca a setOpenMessage() y luego se invoca a decode() sin invocar previamente a code()). Retorna null 
        *  si el proceso de desencriptación no pudo hacerse por haber caracteres extraños en el mensaje encriptado. 
        *  @return una cadena con el mensaje desencriptado, o null si la cadena no pudo desencriptarse (debido a 
        *          que algún caracter del mensaje encriptado no era válido).
        */
       public String decode ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( encriptado ) ) return null;
          
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < encriptado.length(); i++) 
          {
              char actual = encriptado.charAt(i);
              int iactual = transposicion.indexOf( actual );
              
              b.append( Encrypter.ALFABETO.charAt( iactual ) );
          }
          
          mensaje = b.toString();
          return mensaje;
       }
       
       /**
        * Retorna una cadena con información general sobre el encriptador.
        * @return un cadena con información del encriptador.
        */
       public String toString()
       {
           return super.toString() + "\nTécnica: Tabla de Transposición" + "\nTabla usada: " + transposicion;
       }
}
