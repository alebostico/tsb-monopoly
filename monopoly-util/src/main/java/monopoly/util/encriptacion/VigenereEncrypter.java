package monopoly.util.encriptacion;


/**
 * Encripta una cadena en base a la técnica de Vigenere. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposición es creada 
 * y almacenada internamente por la misma clase. 
 * Patrón aplicado: Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Julio de 2009
 */
public class VigenereEncrypter extends TranspositionEncrypter
{
       protected String clave;  // contiene la clave que debe usarse junto a la tabla de transposición
       
       /**
        *  Inicia un encriptador con técnica de Vigenere. El mensaje a encriptar será inicializado 
        *  como la cadena vacía ("") y el programador deberá cambiar luego ese valor mediante setOpenMessage(). 
        *  La clave de desplazamiento a usar, será iniciada con la secuencia "ABC". 
        */
       public VigenereEncrypter( )
       {
           this("", "ABC");
       }
          
       /**
        *  Inicia un encriptador con técnica de Vigenere. El mensaje a encriptar será inicializado 
        *  con el valor del parámetro y el programador podrá cambiar luego ese valor mediante setOpenMessage(). 
        *  La clave de desplazamiento a usar, será iniciada con la secuencia "ABC". 
        */
       public VigenereEncrypter( String mens )
       {
           this(mens, "ABC");
       }
          
       /**
        *  Inicia un encriptador con técnica de Vigenere. El mensaje a encriptar se inicializa 
        *  con el valor del parámetro mens. Si ese parámetro es null, el mensaje a encriptar se inicia como 
        *  la cadena vacía ("") y luego el programador deberá cambiar ese valor con setOpenMessage().
        *  La clave de desplazamiento a usar, será iniciada con el parámetro key. Si este parámetro es null,
        *  la clave quedará como "ABC".
        *  @param mens el mensaje abierto que será encriptado.
        *  @param key la clave de desplazamiento a usar.
        */
       public VigenereEncrypter( String mens, String key )
       {
           super(mens);
           if (key == null) key = "ABC";
           clave = key;
       }
       
       /**
        * Retorna la clave usada por el encriptador para la tabla de transposición. 
        * El método está disponible sólo para efectos didácticos...
        */
       public String getKey()
       {
           return clave;
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, según la técnica Vigenere. 
        * Retorna null si el proceso de encriptación no pudo hacerse por haber caracteres extraños en el 
        * mensaje abierto. 
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         algún caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          
          int ic = 0; // para recorrer la clave...
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < mensaje.length(); i++) 
          {
              char actual = mensaje.charAt(i);
              int iactual = ALFABETO.indexOf( actual );
              
              int iclave = ALFABETO.indexOf( clave.charAt(ic) );
              int icripto = ( iactual + iclave ) % ALFABETO.length();

              ic++;
              if ( ic == clave.length() ) ic = 0;
              
              b.append( transposicion.charAt( icripto ) );
          }
          
          encriptado = b.toString();
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la técnica Vigenere.
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
          
          int ic = 0;
          int n = ALFABETO.length();
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < encriptado.length(); i++) 
          {
              char actual = encriptado.charAt(i);
              int iactual = transposicion.indexOf( actual );
              
              int iclave = ALFABETO.indexOf( clave.charAt(ic) );             
              int idecripto = ( iactual - iclave + n ) % n;
              
              ic++;
              if ( ic == clave.length() ) ic = 0;              
              
              b.append( ALFABETO.charAt( idecripto ) );
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
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptación conocida: " + encriptado;
           return res + "\nTécnica: Cifrado de Vigenere" + "\nTabla usada: " + transposicion + "\nClave: " + clave;
       }
}