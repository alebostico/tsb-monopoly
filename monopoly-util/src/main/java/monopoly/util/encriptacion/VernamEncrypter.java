package monopoly.util.encriptacion;


/**
 * Encripta una cadena en base a la técnica de Vernam original. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La clave usada para encriptar (por 
 * razones de simplicidad) es la tabla de transposición almacenada internamente por la clase 
 * TranspositionEncrypter. De esa tabla, se usan tantos caracteres a modo de clave como sea el largo 
 * de la cadena a encriptar (de acuerdo a la idea de Vernam), y se hace un XOR entre cada caracter 
 * de la cadena a encriptar y cada caracter de la clave. Notar que en este caso, el mensaje encriptado
 * puede contener caracteres que no correspondan al alfabeto base (representado por la constante 
 * Encrypter.ALFABETO). 
 * Patrón aplicado: Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Julio de 2009
 */
public class VernamEncrypter extends TranspositionEncrypter
{
       /**
        *  Inicia un encriptador con técnica de Vernam. El mensaje a encriptar será inicializado 
        *  como la cadena vacía ("") y el programador deberá cambiar luego ese valor mediante setOpenMessage().
        */
       public VernamEncrypter( )
       {
           this("");
       }
    
       /**
        *  Inicia un encriptador con técnica de Vernam. El mensaje a encriptar se inicializa 
        *  con el valor del parámetro mens. Si ese parámetro es null, el mensaje a encriptar se inicia como 
        *  la cadena vacía ("") y luego el programador deberá cambiar ese valor con setOpenMessage().
        *  
        *  @param mens el mensaje abierto que será encriptado.
        */
       public VernamEncrypter( String mens )
       {
           super(mens);
       }
       
       /**
        * Retorna la clave usada por el encriptador. El método está disponible sólo para
        * efectos didácticos... Si el mensaje a encriptar es la cadena vacía (""), el método
        * retornará null. La técnica es simple: se hace un XOR entre el mensaje abierto y el 
        * mensaje encriptado...
        * 
        * @return la clave usada por el encriptador.
        */
       public String getKey()
       {
           if ( mensaje == "" || encriptado == null || encriptado == "" ) return null;
           return doXOR( mensaje, encriptado );
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, según la técnica de Vernam original. Retorna null 
        * si el proceso de encriptación no pudo hacerse por haber caracteres extraños en el mensaje abierto. 
        * Notar que el mensaje encriptado puede contener caracteres que no correspondan al alfabeto base 
        * (representado por la constante Encrypter.ALFABETO), ya que en el encriptado se aplica un XOR entre
        * los caractes del mensaje abierto y los caracteres de la clave.
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         algún caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          encriptado = doXOR(mensaje, transposicion);
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la técnica de Vernam original.
        *  El método PUEDE CAMBIAR el valor del mensaje abierto almacenado en la clase (lo cual ocurrirá si se 
        *  invoca a setOpenMessage() y luego se invoca a decode() sin invocar previamente a code()). Retorna null 
        *  si el proceso de desencriptación no pudo hacerse (lo que ocurrirá si el mensaje encriptado era null 
        *  o era la cadena vacíac("")). 
        *  @return una cadena con el mensaje desencriptado, o null si la cadena no pudo desencriptarse.
        */
       public String decode ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( encriptado == null || encriptado == "" ) return null;
          mensaje = doXOR(encriptado, transposicion);
          return mensaje;
       }
       
       /**
        * Retorna una cadena con información general sobre el encriptador.
        * @return un cadena con información del encriptador.
        */
       public String toString()
       {
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptación conocida: " + encriptado;
           return res + "\nTécnica: Cifrado de Vernam" + "\nClave: " + getKey();
       }
       
       private String doXOR( String m1, String m2 )
       {
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < m1.length(); i++) 
          {
              char actual = m1.charAt(i);
              char clave  = m2.charAt(i);
              char cripto = (char)(actual ^ clave);             
              b.append( cripto );
          }
          return b.toString();
       }
}