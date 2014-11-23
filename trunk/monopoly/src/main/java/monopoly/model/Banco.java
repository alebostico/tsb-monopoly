/*
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Banco implements Serializable {

	/**
	 * 
	 */
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

}
