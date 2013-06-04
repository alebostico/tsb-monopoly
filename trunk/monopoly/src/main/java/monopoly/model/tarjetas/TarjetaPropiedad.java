/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name = "tarjeta_propiedad", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TarjetaPropiedad implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "jugadorID")
    private Jugador jugador;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "tarjetaPropiedadID")
    private Integer idTarjeta;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "valorHipoticario")
    private Integer valorHipotecario;

    public TarjetaPropiedad() {

    }

    /**
     * @param jugador
     * @param nombre
     * @param valorHipotecario
     */
    public TarjetaPropiedad(Jugador jugador, String nombre, Integer valorHipotecario) {
	super();
	this.jugador = jugador;
	this.nombre = nombre;
	this.valorHipotecario = valorHipotecario;
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

}
