/**
 * 
 */
package monopoly.model.tarjetas;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name="tarjeta_estacion", catalog = "monopoly_db")
@AttributeOverrides({
    @AttributeOverride(name="jugador", column=@Column(name="jugadorID")),
    @AttributeOverride(name="nombre", column=@Column(name="nombre")),
    @AttributeOverride(name="valorHipoticario", column=@Column(name="valorHipoticario"))
})
public class TarjetaEstacion extends TarjetaPropiedad {

    @Column(name = "precioAlquiler")
    private Integer precioAlquiler;

    @Column(name = "valorUnaEstacion")
    private Integer valorUnaEstacion;

    @Column(name = "valorDosEstacion")
    private Integer valorDosEstacion;

    @Column(name = "valorTresEstacion")
    private Integer valorTresEstacion;

    @Column(name = "valorCuatroEstacion")
    private Integer valorCuatroEstacion;

    /**
     * 
     */
    public TarjetaEstacion() {
	super();
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
     * @return the valorUnaEstacion
     */
    public Integer getValorUnaEstacion() {
        return valorUnaEstacion;
    }

    /**
     * @param valorUnaEstacion the valorUnaEstacion to set
     */
    public void setValorUnaEstacion(Integer valorUnaEstacion) {
        this.valorUnaEstacion = valorUnaEstacion;
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
    
}
