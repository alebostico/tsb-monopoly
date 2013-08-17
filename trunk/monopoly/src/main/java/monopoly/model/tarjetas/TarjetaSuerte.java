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
    
    /**
     * 1,VE AL PASEO DEL PRADO
     * 2,VE A LA GLORIETA DE BILBAO. SI PASAS POR LA CASILLA DE SALIDA COBRA 200
     * 3,LA BANCA TE PAGA 50 DE INTERESES
     * 4,COLOCATE EN LA CASILLA DE SALIDA
     * 5,ADELANTATE HASTA LA CALLE CEA BERMUDEZ. SI PASAS POR LA CASILLA DE SALIDA, COBRA 200
     * 6,RECIBES EL RESCATE POR EL SEGURO DE TUS EDIFICIOS. COBRA 150
     * 7,VE A LA CARCEL. VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y SIN COBRAR LOS 200
     * 8,MULTA POR EMBRIAGUEZ 20
     * 9,RETROCEDE TRES CASILLAS
     * 10,HAZ REPARACIONES EN TODOS TUS EDIFICIOS. PAGA POR CADA CASA 25. PAGA POR CADA HOTEL 100
     * 11,LA INSPECCION DE LA CALLE TE OBLIGA A REPARACIONES. PAGA 40.POR CADA CASA. PAGA 115 POR HOTEL
     * 12,QUEDAS LIBRE DE LA CARCEL. Esta carta puede venderse o conservarse hasta que sea utilizada
     * 13,PAGA POR GASTOS ESCOLARES 150
     * 14,VE A LA ESTACIoN DE LAS DELICIAS. SI PASAS POR LA CASILLA DE SALIDA,COBRA 200
     * 
     */
    
    public boolean jugarTarjeta(Juego j, Jugador k)
    {
	switch (idTarjeta) {
	    case  1: return (j.getTablero().moverACasillero(k, 40)!=null);//del prado
	    case  2: return (j.getTablero().moverACasillero(k, 12)!=null);//glorieta de bilbao
	    case  3: return j.getBanco().pagar(k, 50);
	    case  4: return (j.getTablero().moverACasillero(k, 1)!=null);//salida
	    case  5: return (j.getTablero().moverACasillero(k, 25)!=null);//calle bermudez
	    case  6: return j.getBanco().pagar(k, 150);
	    case  7: return (j.getTablero().irACarcel(k)!=null);
	    case  8: return j.getBanco().cobrar(k, 20);
	    case  9: return (j.getTablero().moverAtras(k, 3)!=null);
	    case 10: return j.getBanco().cobrarPorEdificioYhotel(k, 25, 100);
	    case 11: return j.getBanco().cobrarPorEdificioYhotel(k, 40, 115);
	    //TODO: como hago aca?
	    case 12: k.setTarjeta(this); return true;
	    case 13: return j.getBanco().cobrar(k, 150);
	    case 14: return (j.getTablero().moverACasillero(k, 16)!=null);//las delicias
	    default:
		return false;
	}
    }
    
    
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "TarjetaSuerte [idTarjeta=" + idTarjeta + ", objetivo=" + objetivo + "]";
    }

}
