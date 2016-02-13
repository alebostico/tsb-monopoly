/**
 * 
 */
package monopoly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.constantes.EnumEstadoSubasta;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SubastaStatus implements Serializable {

	private static final long serialVersionUID = 2609280188214021724L;

	public final EnumEstadoSubasta estado;

	public final List<History> historyList;

	public final Jugador jugadorActual;
	
	public final TarjetaPropiedad propiedadSubastada;
	
	public final int montoSubasta;
	
	public String mensaje;

	public SubastaStatus(EnumEstadoSubasta pEstado, List<History> pHistoryList,
			Jugador pJugadorActual, TarjetaPropiedad pPropiedadSubastada,int pMontoSubasta) {
		this.estado = pEstado;
		if (pHistoryList == null)
			this.historyList = new ArrayList<History>();
		else
			this.historyList= pHistoryList;
		this.jugadorActual = pJugadorActual;
		this.propiedadSubastada = pPropiedadSubastada;
		this.montoSubasta = pMontoSubasta;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
