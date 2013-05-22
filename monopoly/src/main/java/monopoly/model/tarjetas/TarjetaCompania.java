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
@Table(name="tarjeta_compania", catalog = "monopoly_db")
@AttributeOverrides({
    @AttributeOverride(name="jugador", column=@Column(name="jugadorID")),
    @AttributeOverride(name="nombre", column=@Column(name="nombre")),
    @AttributeOverride(name="valorHipoticario", column=@Column(name="valorHipoticario"))
})
public class TarjetaCompania extends TarjetaPropiedad {

    private Integer vecesPorUnaCarta;
    
    private Integer vecesPorDosCartas;

    /**
     * 
     */
    public TarjetaCompania() {
	super();
    }

    /**
     * @return the vecesPorUnaCarta
     */
    public Integer getVecesPorUnaCarta() {
        return vecesPorUnaCarta;
    }

    /**
     * @param vecesPorUnaCarta the vecesPorUnaCarta to set
     */
    public void setVecesPorUnaCarta(Integer vecesPorUnaCarta) {
        this.vecesPorUnaCarta = vecesPorUnaCarta;
    }

    /**
     * @return the vecesPorDosCartas
     */
    public Integer getVecesPorDosCartas() {
        return vecesPorDosCartas;
    }

    /**
     * @param vecesPorDosCartas the vecesPorDosCartas to set
     */
    public void setVecesPorDosCartas(Integer vecesPorDosCartas) {
        this.vecesPorDosCartas = vecesPorDosCartas;
    }
    
}
