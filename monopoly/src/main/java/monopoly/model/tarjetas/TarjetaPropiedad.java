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

	public static String TARJETA_02 = "tarjeta02";
	public static String TARJETA_04 = "tarjeta04";
	public static String TARJETA_07 = "tarjeta07";
	public static String TARJETA_09 = "tarjeta09";
	public static String TARJETA_10 = "tarjeta10";
	public static String TARJETA_12 = "tarjeta12";
	public static String TARJETA_14 = "tarjeta14";
	public static String TARJETA_15 = "tarjeta15";
	public static String TARJETA_17 = "tarjeta17";
	public static String TARJETA_19 = "tarjeta19";
	public static String TARJETA_20 = "tarjeta20";
	public static String TARJETA_22 = "tarjeta22";
	public static String TARJETA_24 = "tarjeta24";
	public static String TARJETA_25 = "tarjeta25";
	public static String TARJETA_27 = "tarjeta27";
	public static String TARJETA_28 = "tarjeta28";
	public static String TARJETA_30 = "tarjeta30";
	public static String TARJETA_32 = "tarjeta32";
	public static String TARJETA_33 = "tarjeta33";
	public static String TARJETA_35 = "tarjeta35";
	public static String TARJETA_38 = "tarjeta38";
	public static String TARJETA_40 = "tarjeta40";
	public static String TARJETA_06 = "tarjeta06";
	public static String TARJETA_16 = "tarjeta16";
	public static String TARJETA_26 = "tarjeta26";
	public static String TARJETA_36 = "tarjeta36";
	public static String TARJETA_29 = "tarjeta29";
	public static String TARJETA_13 = "tarjeta13";

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

	@Column(name = "nombrePropiedad")
	private String nombrePropiedad;

	public TarjetaPropiedad() {

	}

	/**
	 * @param jugador
	 * @param nombre
	 * @param valorHipotecario
	 * @param valorPropiedad
	 */
	public TarjetaPropiedad(Jugador jugador, String nombre,
			Integer valorHipotecario, String nombreImagen,
			Integer valorPropiedad) {
		super();
		this.jugador = jugador;
		this.nombre = nombre;
		this.valorHipotecario = valorHipotecario;
		this.nombreImagen = nombreImagen;
		this.hipotecada = false;
		this.valorPropiedad = valorPropiedad;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Integer getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(Integer idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getValorHipotecario() {
		return valorHipotecario;
	}

	public void setValorHipotecario(Integer valorHipotecario) {
		this.valorHipotecario = valorHipotecario;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public int getValorPropiedad() {
		return valorPropiedad;
	}

	public void setValorPropiedad(int valorPropiedad) {
		this.valorPropiedad = valorPropiedad;
	}

	public boolean isHipotecada() {
		return hipotecada;
	}

	public void setHipotecada(boolean hipotecada) {
		this.hipotecada = hipotecada;
	}

	public String getNombrePropiedad() {
		return nombrePropiedad;
	}

	public void setNombrePropiedad(String nombrePropiedad) {
		this.nombrePropiedad = nombrePropiedad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TarjetaPropiedad [ nombre=" + nombre + ", valorHipotecario="
				+ valorHipotecario + ",valoPropiedadr=" + valorPropiedad + " ]";
	}

	public boolean hipotecarPropiedad() {
		return this.getJugador().getJuego().getBanco()
				.hipotecarPropiedad(jugador, this);
	}

	public boolean deshipotecarPropiedad() {
		return this.getJugador().getJuego().getBanco()
				.deshipotecarPropiedad(this.getJugador(), this);
	}

}
