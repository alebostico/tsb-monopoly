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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import monopoly.model.Jugador;

import org.hibernate.annotations.Type;

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
	@Column(name = "tarjetaPropiedadID")
	private Integer idTarjeta;

	@ManyToOne
	@JoinColumn(name = "jugadorID")
	private Jugador jugador;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "valorHipotecario")
	private Integer valorHipotecario;

	@Column(name = "nombreImagen")
	private String nombreImagen;

	@Column(name = "isHipotecada", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hipotecada;
	
	@Column(name = "valorPropiedad")
	private int valorPropiedad;

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
	 * @param valorPropiedad
	 */
	public TarjetaPropiedad(Jugador jugador, String nombre,
			Integer valorHipotecario, String nombreImagen,Integer valorPropiedad) {
		super();
		this.jugador = jugador;
		this.nombre = nombre;
		this.valorHipotecario = valorHipotecario;
		this.nombreImagen = nombreImagen;
		this.hipotecada = false;
		this.valorPropiedad=valorPropiedad;
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
	
	
	/**
	 * @return the valorPropiedad
	 */
	public int getValorPropiedad() {
		return valorPropiedad;
	}

	/**
	 * @param valorPropiedad
	 *            the valor to set
	 */
	public void setValorPropiedad(int valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TarjetaPropiedad [ nombre=" + nombre + ", valorHipotecario="
				+ valorHipotecario + ",valoPropiedadr="+valorPropiedad+ " ]";
	}
	
	public boolean hipotecarPropiedad()
	{
		return this.getJugador().getJuego().getBanco().hipotecarPropiedad(jugador, this);
	}
	
	public boolean deshipotecarPropiedad()
	{
		return this.getJugador().getJuego().getBanco().deshipotecarPropiedad(this.getJugador(), this);
	}
	

}