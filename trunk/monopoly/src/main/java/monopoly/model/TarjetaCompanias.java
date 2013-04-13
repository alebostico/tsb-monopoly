/**
 * 
 */
package monopoly.model;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class TarjetaCompanias extends TarjetaPropiedad {

    private Integer vecesPorUnaCarta;
    
    private Integer vecesPorDosCartas;

    /**
     * 
     */
    public TarjetaCompanias() {
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
