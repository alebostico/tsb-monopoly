package monopoly.model.cartas;

import monopoly.model.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public abstract class Carta {
    
    // traer de la base de datos el texto, imagen, tipo y orgen
    //para armar el maso hacer coincidir el orden de cada carta con el numero de la estrategia
    //va a tener que ser uno por uno
    
    //cada casillero deberia tener el nombre que figura en el tablero para poder buscarlo 
    //con ese nombre en la lista o arreglo de casilleros
    //en las imagenes de las cartas hay 12 de una y 15 de otra... faltara alguna?
  //ve al paseo del prado
	
  //j.getTablero().getCasillerosList().getcasillero("paseo del prado").setjugador(k);
    
    private String texto;
    private String imagen;
    private String tipo;
    private int orden;
    
    
    public final static String CARTA_SUERTE="Carta Suerte";
    public final static String CARTA_COMUNIDAD="Caja de Comunidad";
    

    public Carta(String texto, String imagen, String tipo) {
	super();
	this.texto = texto;
	this.imagen = imagen;
	this.tipo = tipo;
    }
    


    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }


    public String getImagen() {
        return imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    


    public int getOrden() {
        return orden;
    }



    public void setOrden(int orden) {
        this.orden = orden;
    }



    abstract boolean  cumplirInstrucciones(Juego j, Jugador k);

}
