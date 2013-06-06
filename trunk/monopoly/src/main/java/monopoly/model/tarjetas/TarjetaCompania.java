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
@Table(name="tarjeta_compania", catalog = "monopoly_db")
//@AttributeOverrides({
//    @AttributeOverride(name="jugador", column=@Column(name="jugadorID")),
//    @AttributeOverride(name="nombre", column=@Column(name="nombre")),
//    @AttributeOverride(name="valorHipoticario", column=@Column(name="valorHipoticario"))
//})
@PrimaryKeyJoinColumn(name="tarjetaPropiedadID")
public class TarjetaCompania extends TarjetaPropiedad implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "vecesPorUnaCarta")
    private Integer vecesPorUnaCarta;
    
    @Column(name = "vecesPorDosCartas")
    private Integer vecesPorDosCartas;

    /**
     * 
     */
    public TarjetaCompania() {
	super();
    }

    /**
     * @param jugador
     * @param nombre
     * @param valorHipotecario
     * @param vecesPorUnaCarta
     * @param vecesPorDosCartas
     */
    public TarjetaCompania(Jugador jugador, String nombre, Integer valorHipotecario, Integer vecesPorUnaCarta,
	    Integer vecesPorDosCartas, String nombreImagen) {
	super(jugador, nombre, valorHipotecario, nombreImagen);
	this.vecesPorUnaCarta = vecesPorUnaCarta;
	this.vecesPorDosCartas = vecesPorDosCartas;
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
