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

import monopoly.model.Juego;
import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */



@Entity
@Table(name="tarjeta_comunidad", catalog = "monopoly_db")
public class TarjetaComunidad  extends Tarjeta implements Serializable{

    private static final long serialVersionUID = -8002053704891216523L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarjetaComunidadID")
    private Integer idTarjeta;
    
    @Column(name = "objetivo")
    private String objetivo;

    /**
     * @param idTarjeta
     * @param objetivo
     */
    public TarjetaComunidad(Integer idTarjeta, String objetivo) {
	super();
	this.idTarjeta = idTarjeta;
	this.objetivo = objetivo;
    }

    /**
     * 
     */
    public TarjetaComunidad() {
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
    
    /**
     * 1,PAGA POR TU POLIZA DE SEGUROS 50
     * 2,EN TU CUMPLEAÑOS RECIBES DE CADA JUGADOR 10
     * 3,COLOCATE EN LA CASILLA DE SALIDA
     * 4,PAGA LA FACTURA DEL MEDICO 50
     * 5,HAS GANADO EL SEGUNDO PREMIO DE BELLEZA, RECIBE 10
     * 6,ERROR DE LA BANCA A TU FAVOR, RECIBE 200
     * 7,VE A LA CARCEL, VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y SIN COBRAR LOS 200
     * 8,HACIENDA TE DEVUELVE 20
     * 9,COBRAS UNA HERENCIA DE 100
     * 10,RECIBE 100 POR LOS INTERESES DE TU PLAZO FIJO
     * 11,PAGA AL HOSPITAL 100
     * 12,RETROCEDE HASTA LA RONDA DE VALENCIA
     * 13,QUEDAS LIBRE DE LA CARCEL, ESTA TARJETA PUEDE VENDERSE O CONSERVARSE HASTA UTILIZARSE
     * 14,LA VENTA DE TUS ACCIONES TE PRODUCE 50
     *
     */
    
    public boolean jugarTarjeta(Juego j, Jugador k)
    {
	switch (idTarjeta) {
	    case  1: return j.getBanco().pagar(k, 50);
	    case  2: return j.getBanco().cobrarATodosPagarAUno(j.getJugadoresList(), k, 10);
	    case  3: return (j.getTablero().moverACasillero(k, 1)!=null);//salida
	    case  4: return j.getBanco().pagar(k, 50);
	    case  5: return j.getBanco().cobrar(k, 10);
	    case  6: return j.getBanco().cobrar(k, 200);
	    case  7: return (j.getTablero().irACarcel(k)!=null);
	    case  8: return j.getBanco().pagar(k, 20);
	    case  9: return j.getBanco().pagar(k, 100);
	    case 10: return j.getBanco().pagar(k, 100);
	    case 11: return j.getBanco().cobrar(k, 100);
	    case 12: return (j.getTablero().retrocederA(k, 2)!=null);
	    //TODO: como hago aca?
	    case 13: k.setTarjeta(this); return true;
	    case 14: return j.getBanco().pagar(k, 50);
	    default:
		return false;
	}
    }
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "TarjetaComunidad [idTarjeta=" + idTarjeta + ", objetivo=" + objetivo + "]";
    }
    
}
