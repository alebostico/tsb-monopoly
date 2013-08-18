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
import javax.persistence.Table;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Entity
@Table(name="tarjeta_suerte", catalog = "monopoly_db")
public class TarjetaSuerte extends Tarjeta implements Serializable{

    private static final long serialVersionUID = -2184508726376255906L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarjetaSuerteID")
    private Integer idTarjeta;
    
    @Column(name = "objetivo")
    private String objetivo;

    /**
     * @param idTarjeta
     * @param objetivo
     */
    public TarjetaSuerte(Integer idTarjeta, String objetivo) {
	super();
	this.idTarjeta = idTarjeta;
	this.objetivo = objetivo;
    }

    /**
     * 
     */
    public TarjetaSuerte() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @return the idTarjeta
     */
    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    /**
     * @param idTarjeta the idTarjeta to set
     */
    public void setIdTarjeta(Integer idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    /**
     * @return the objetivo
     */
    public String getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "TarjetaSuerte [idTarjeta=" + idTarjeta + ", objetivo=" + objetivo + "]";
    }

}