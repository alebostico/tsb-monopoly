/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name="tarjeta_estacion", catalog = "monopoly_db")
//@AttributeOverrides({
//    @AttributeOverride(name="jugador", column=@Column(name="jugadorID")),
//    @AttributeOverride(name="nombre", column=@Column(name="nombre")),
//    @AttributeOverride(name="valorHipoticario", column=@Column(name="valorHipoticario"))
//})
@PrimaryKeyJoinColumn(name="tarjetaPropiedadID")
public class TarjetaEstacion extends TarjetaPropiedad implements Serializable{

    private static final long serialVersionUID = 1459008914691723627L;

    @Column(name = "precioAlquiler")
    private Integer precioAlquiler;

    @Column(name = "valorDosEstacion")
    private Integer valorDosEstacion;

    @Column(name = "valorTresEstacion")
    private Integer valorTresEstacion;

    @Column(name = "valorCuatroEstacion")
    private Integer valorCuatroEstacion;

    
    public TarjetaEstacion() {
	super();
    }
    
    /**
     * @param jugador
     * @param nombre
     * @param valorHipotecario
     * @param precioAlquiler
     * @param valorUnaEstacion
     * @param valorDosEstacion
     * @param valorTresEstacion
     * @param valorCuatroEstacion
     */
    public TarjetaEstacion(Jugador jugador, String nombre, Integer valorHipotecario, Integer precioAlquiler,
	    Integer valorDosEstacion, Integer valorTresEstacion, Integer valorCuatroEstacion, String nombreImagen) {
	super(jugador, nombre, valorHipotecario, nombreImagen);
	this.precioAlquiler = precioAlquiler;
	this.valorDosEstacion = valorDosEstacion;
	this.valorTresEstacion = valorTresEstacion;
	this.valorCuatroEstacion = valorCuatroEstacion;
    }

    /**
     * @return the precioAlquiler
     */
    public Integer getPrecioAlquiler() {
        return precioAlquiler;
    }

    /**
     * @param precioAlquiler the precioAlquiler to set
     */
    public void setPrecioAlquiler(Integer precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }

    /**
     * @return the valorDosEstacion
     */
    public Integer getValorDosEstacion() {
        return valorDosEstacion;
    }

    /**
     * @param valorDosEstacion the valorDosEstacion to set
     */
    public void setValorDosEstacion(Integer valorDosEstacion) {
        this.valorDosEstacion = valorDosEstacion;
    }

    /**
     * @return the valorTresEstacion
     */
    public Integer getValorTresEstacion() {
        return valorTresEstacion;
    }

    /**
     * @param valorTresEstacion the valorTresEstacion to set
     */
    public void setValorTresEstacion(Integer valorTresEstacion) {
        this.valorTresEstacion = valorTresEstacion;
    }

    /**
     * @return the valorCuatroEstacion
     */
    public Integer getValorCuatroEstacion() {
        return valorCuatroEstacion;
    }

    /**
     * @param valorCuatroEstacion the valorCuatroEstacion to set
     */
    public void setValorCuatroEstacion(Integer valorCuatroEstacion) {
        this.valorCuatroEstacion = valorCuatroEstacion;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return super.toString()
		+ "\n\tTarjetaEstacion [precioAlquiler=" + precioAlquiler + ", valorDosEstacion=" + valorDosEstacion
		+ ", valorTresEstacion=" + valorTresEstacion + ", valorCuatroEstacion=" + valorCuatroEstacion + "]";
    }
    
}
