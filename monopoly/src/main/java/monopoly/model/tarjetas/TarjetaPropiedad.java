/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name = "tarjeta_propiedad", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TarjetaPropiedad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    // (strategy = GenerationType.TABLE)
    @Column(name = "tarjetaPropiedadID")
    private Integer idTarjeta;

    @OneToOne
    @JoinColumn(name = "jugadorID")
    private Jugador jugador;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "valorHipotecario")
    private Integer valorHipotecario;

    @Column(name = "nombreImagen")
    private String nombreImagen;
    
    @Transient
    private boolean hipotecada;

    public boolean isHipotecada() {
		return hipotecada;
	}

	public void setHipotecada(boolean hipotecada) {
		this.hipotecada = hipotecada;
	}

	public TarjetaPropiedad() {

    }

    /**
     * @param jugador
     * @param nombre
     * @param valorHipotecario
     */
    public TarjetaPropiedad(Jugador jugador, String nombre, Integer valorHipotecario, String nombreImagen) {
	super();
	this.jugador = jugador;
	this.nombre = nombre;
	this.valorHipotecario = valorHipotecario;
	this.nombreImagen = nombreImagen;
	this.hipotecada= false;
    }

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
	return jugador;
    }

    /**
     * @param jugador
     *            the jugador to set
     */
    public void setJugador(Jugador jugador) {
	this.jugador = jugador;
    }

    /**
     * @return the idTarjeta
     */
    public Integer getIdTarjeta() {
	return idTarjeta;
    }

    /**
     * @param idTarjeta
     *            the idTarjeta to set
     */
    public void setIdTarjeta(Integer idTarjeta) {
	this.idTarjeta = idTarjeta;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    /**
     * @return the valorHipotecario
     */
    public Integer getValorHipotecario() {
	return valorHipotecario;
    }

    /**
     * @param valorHipotecario
     *            the valorHipotecario to set
     */
    public void setValorHipotecario(Integer valorHipotecario) {
	this.valorHipotecario = valorHipotecario;
    }

    /**
     * @return the nombreImagen
     */
    public String getNombreImagen() {
	return nombreImagen;
    }

    /**
     * @param nombreImagen
     *            the nombreImagen to set
     */
    public void setNombreImagen(String nombreImagen) {
	this.nombreImagen = nombreImagen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Propiedad [ nombre=" + nombre + ", valorHipotecario=" + valorHipotecario + " ]";
    }

}
