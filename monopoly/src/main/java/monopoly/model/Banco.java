/*
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.tablero.Casillero;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
public class Banco implements Serializable {

	private static final long serialVersionUID = -3371251147808213945L;

	private List<TarjetaPropiedad> tarjPropiedadList;
	private int idBanco;
	private int nroCasas;
	private int nroHoteles;

	public Banco(List<TarjetaPropiedad> list, int nroCasas, int nroHoteles) {
		// TODO cargar todas las propiedades
		tarjPropiedadList = new ArrayList<TarjetaPropiedad>();
		tarjPropiedadList.addAll(list);
		this.nroCasas = nroCasas;
		this.nroHoteles = nroHoteles;
	}

	/**
	 * @return the tarjPropiedadList
	 */
	public List<TarjetaPropiedad> getTarjPropiedadList() {
		return tarjPropiedadList;
	}

	/**
	 * @param tarjPropiedadList
	 *            the tarjPropiedadList to set
	 */
	public void setTarjPropiedadList(List<TarjetaPropiedad> tarjPropiedadList) {
		this.tarjPropiedadList = tarjPropiedadList;
	}

	/**
	 * @return the nroCasas
	 */
	public int getNroCasas() {
		return nroCasas;
	}

	/**
	 * @param nroCasas
	 *            the nroCasas to set
	 */
	public void setNroCasas(int nroCasas) {
		this.nroCasas = nroCasas;
	}

	/**
	 * @return the nroHoteles
	 */
	public int getNroHoteles() {
		return nroHoteles;
	}

	/**
	 * @param nroHoteles
	 *            the nroHoteles to set
	 */
	public void setNroHoteles(int nroHoteles) {
		this.nroHoteles = nroHoteles;
	}

	/**
	 * Agrega {@code nroHoteles} hoteles al banco
	 * 
	 * @param nroHoteles
	 *            La cantidad de hoteles a agregar (si es negativo se restan)
	 * @return La nueva cantidad de hoteles disponibles
	 */
	public int addHoteles(int nroHoteles) {
		return this.nroHoteles += nroHoteles;
	}

	/**
	 * Elimina {@code nroHoteles} hoteles del banco
	 * 
	 * @param nroHoteles
	 *            La cantidad de hoteles a eliminar (si es negativo se agregan)
	 * @return La nueva cantidad de hoteles disponibles
	 */
	public int removeHoteles(int nroHoteles) {
		return this.nroHoteles -= nroHoteles;
	}

	/**
	 * Agrega {@code nroCasas} casas al banco
	 * 
	 * @param nroCasas
	 *            La cantidad de casas a agregar (si es negativo se restan)
	 * @return La nueva cantidad de casas disponibles
	 */
	public int addCasas(int nroCasas) {
		return this.nroCasas += nroCasas;
	}

	/**
	 * Elimina {@code nroCasas} casas del banco
	 * 
	 * @param nroCasas
	 *            La cantidad de casas a eliminar (si es negativo se agregan)
	 * @return La nueva cantidad de casas disponibles
	 */
	public int removeCasas(int nroCasas){
		return this.nroCasas -= nroCasas;
	}
	
	public int getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(int bancoId) {
		this.idBanco = bancoId;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		Banco banco = (Banco) object;
		if (this.idBanco != banco.getIdBanco())
			return false;

		return true;
	}

	/**
	 * @return the tarjetasPropiedadList
	 */
	public TarjetaPropiedad getTarjetaPropiedad(String key) {
		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			if (tarjetaPropiedad.getNombrePropiedad().equals(key))
				return tarjetaPropiedad;
		}
		return null;
	}

	/**
	 * Obtiene la tarjeta propiedad a partir del casillero.
	 * 
	 * @param pCasillero
	 *            Casillero del tablero
	 * @return Tarjeta de la propiedad a la cual pertenece el casillero.
	 */
	public TarjetaPropiedad getTarjetaPropiedadByCasillero(Casillero pCasillero) {
		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			if (tarjetaPropiedad.getCasillero().getNumeroCasillero() == pCasillero
					.getNumeroCasillero())
				return tarjetaPropiedad;
		}
		return null;
	}
}
