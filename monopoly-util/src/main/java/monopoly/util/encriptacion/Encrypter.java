package monopoly.util.encriptacion;


/**
 * Define el comportamiento que es exigible para una clase que realice trabajos de 
 * encriptación en base a métodos tradicionales. Se supone un "alfabeto" compuesto 
 * de ciertos caracteres, y ese alfabeto está almacenado en la constante ALFABETO
 * provista por esta clase.
 * 
 * Patrón aplicado: Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Julio de 2009
 */
public abstract class Encrypter
{
    /**
     *  El alfabeto base aceptado.
     */
    public static final String ALFABETO = " ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz.,;:?!0123456789";
    
    protected String mensaje;     // el mensaje abierto (mensaje a encriptar)
    protected String encriptado;  // el mensaje encriptado
    
    /**
     *  Inicializa el encriptador con un mensaje abierto tomado como parámetro. Si el parámetro es null, el 
     *  mensaje a encriptar se inicializa como una cadena vacía (""), lo cual debería provocar que el método 
     *  code() retorne null. El constructor también inicia como cadena vacía el mensaje encriptado (que se 
     *  obtiene con getEncrypted()) y esto también debería provocar que el método decode() retorne null.
     */
    public Encrypter ( String mens )
    {
        if ( mens == null ) mens = "";
        mensaje = mens;
        encriptado = "";
    }   
    
    /**
     *  Retorna el último mensaje abierto que debería ser usado la próxima vez que se invoque a code().
     *  @return el mensaje abierto.
     */
    public String getOpenMessage()
    {
        return mensaje;
    }
    
    /**
     *  Cambia el mensaje a encriptar. Si el nuevo mensaje es null, el mensaje a encriptar se inicia como cadena 
     *  vacía (""), lo que debería provocar que una invocación a code() retorne null. En todos los casos, también 
     *  se inicia como cadena vacía el mensaje encriptado, lo que debería provocar que una nueva invocación a decode() 
     *  retorne null.
     *  @param mens el nuevo mensaje a encriptar.
     */
    public void setOpenMessage( String mens )
    {
        if ( mens == null ) mens = "";
        mensaje = mens;
        encriptado = "";
    }
    
    /**
     * Retorna el último mensaje encriptado. Si el valor retornado es null, es que la última invocación a code() 
     * no pudo completar el proceso (posiblemente por caracteres extraños en el mensaje abierto). Si el valor retornado
     * es la cadena vacía (""), es que nunca se invocó code() o bien es que el mensaje abierto fue cambiado (con 
     * setOpenMessage()) desde la última vez que se invocó a code().
     * @return el último mensaje encriptado.
     */
    public String getEncrypted()
    {
        return encriptado;
    }
    
    /**
     *  Retorna una cadena con el último mensaje abierto trabajado por el encriptador y la última encriptación
     *  conocida para ese mensaje (que puede ser null o la cadena vacía si el proceso de encriptación falló o 
     *  nunca se activó).
     *  @return una cadena con el último mensaje abierto y la última encriptación conocida para él.
     */
    public String toString()
    {
        return "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptación conocida: " + encriptado;
    }
    
    /**
     *  Encripta un mensaje formado por los símbolos del alfabeto base, siguiendo alguna técnica
     *  de encriptación que será provista por las clases derivadas. El método retorna una cadena con el mensaje
     *  encriptado (o null si el proceso no pudo realizarse por haber caracteres extraños en el mensaje abierto),
     *  pero de todos modos el mensaje encriptado queda almacenado en la clase y puede recuperarse directamente 
     *  usando getEncrypted().
     *  @return una cadena con el mensaje encriptado, o null si el mensaje abierto no pudo encriptarse (debido 
     *          a que algún caracter de ese mensaje original no figuraba en el alfabeto base aceptado).
     */
    public abstract String code ( );
    
    /**
     *  Desencripta un mensaje, siguiendo alguna técnica de encriptación que será provista por las 
     *  clases derivadas. El mensaje encriptado se supone creado con la misma técnica que será usada 
     *  para desencriptar, y el mensaje obtenido contendrá exclusivamente caracteres del alfabeto base aceptado.
     *  El método retorna una cadena con el mensaje abierto (o null si el proceso no pudo realizarse por haber 
     *  caracteres extraños en el mensaje encriptado), pero de todos modos el mensaje abierto queda almacenado en 
     *  la clase y puede recuperarse directamente usando getOpenMessage().
     *  @return una cadena con el mensaje desencriptado, o null el proceso no pudo hacerse (debido a que algún 
     *          caracter del mensaje encriptado no era válido).
     */
    public abstract String decode ( );
    
    /**
     * Comprueba si la cadena tomada como parámetro está formada exclusivamente por caracteres del alfabeto base 
     * aceptado por el encriptador.
     * @param cad la cadena a comprobar
     * @return true si todos los caracteres son válidos, o false en caso contrario (o si cad es null).  
     */
    protected boolean isOk ( String cad )
    {
      if (cad == null) return false;
      
      for (int i = 0; i < cad.length(); i++) 
      {
          if ( Encrypter.ALFABETO.indexOf( cad.charAt(i) ) == -1 ) return false;
      }    
      return true;
    }
}