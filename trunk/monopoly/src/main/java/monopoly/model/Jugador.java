/**
 * 
 */
package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Entity
@Table(name="jugador", catalog = "monopoly_db")
public class Jugador implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "jugadorID")
    private Integer idJugador;
    
    @Transient
    private Ficha ficha;
    
    @Transient
    private List<TarjetaPropiedad> tarjPropiedadList;
    @Transient
    private Juego juego;
    @Transient
    private int dinero;
    
    @Transient
    private List<Tarjeta> tarjetaCarcelList;
    
    /**
     * Constructor por defecto.
     * inicializa el arraylist tarjetaCarcelList,
     * que será utilizado para almacenar las tarjetas
     * de comunidad y suerte que permiten salir de la cárcel.
     */
    public Jugador(){
	tarjetaCarcelList  = new ArrayList<>();
    }

    /**
     * @return the ficha
     */
    public Ficha getFicha() {
        return ficha;
    }
    
    /**
     * @param ficha the ficha to set
     */
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    /**
     * @return the tarjPropiedadList
     */
    public List<TarjetaPropiedad> getTarjPropiedadList() {
        return tarjPropiedadList;
    }

    /**
     * @param tarjPropiedadList the tarjPropiedadList to set
     */
    public void setTarjPropiedadList(List<TarjetaPropiedad> tarjPropiedadList) {
        this.tarjPropiedadList = tarjPropiedadList;
    }

    /**
     * @return the juego
     */
    public Juego getJuego() {
        return juego;
    }

    /**
     * @param juego the juego to set
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
    
    public int getDinero() {
        return dinero;
    }


    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    /**
     * @return the idJugador
     */
    public Integer getIdJugador() {
        return idJugador;
    }


    /**
     * @param idJugador the idJugador to set
     */
    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    /**
     * @return the tarjetaCarcelList
     */
    public List<Tarjeta> getTarjetaCarcelList() {
        return tarjetaCarcelList;
    }

    /**
     * @param tarjetaCarcelList the tarjetaCarcelList to set
     */
    public void setTarjetaCarcelList(List<Tarjeta> tarjetaCarcelList) {
        this.tarjetaCarcelList = tarjetaCarcelList;
    }

    /**
     * Devuelve true si el jugador  pasada por parametro es igual al que ejecuta el metodo
     * compara en funcion del nombre (string) de la ficha
     * @param c
     * @return
     */
    @Override
    public boolean equals(Object o) {
	if ( o==null)
	    return false;
	if (o.getClass()!=this.getClass())
	    return false;
	Jugador j=(Jugador)o;
        return this.getFicha().equals(j.getFicha());
    }


    @Override
    public String toString() {
	return "Jugador [ficha=" + ficha + "]";
    }
    
      
    
}
