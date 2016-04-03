/**
 * 
 */
package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.Estado.EstadoJugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCalle.EnumColor;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.exception.SinDineroException;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
@Entity
@Table(name = "jugador", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Jugador implements Serializable {

	private static final long serialVersionUID = -3652571253408007334L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "jugadorID")
	private int idJugador;

	@Transient
	private String nombre;

	@Transient
	private Ficha ficha;

	@Transient
	private Casillero casilleroActual;

	@Transient
	private List<TarjetaPropiedad> tarjPropiedadList;

	@Transient
	private Juego juego;

	@Transient
	private int dinero;

	@Transient
	private Dado tiradaInicial;

	@Transient
	private Dado ultimoResultado;

	@Transient
	private List<Tarjeta> tarjetaCarcelList;

	@Transient
	private int nroCasas;

	@Transient
	private int nroHoteles;

	@Transient
	private int contTurnosCarcel;

	@Transient
	private boolean isPreso;

	@Transient
	private int contDadosDobles;

	@Transient
	private EstadoJugador estadoJugador;
	
	@Transient
	private boolean enSubasta;

	/**
	 * Constructor por defecto. inicializa el arraylist tarjetaCarcelList, que
	 * será utilizado para almacenar las tarjetas de comunidad y suerte que
	 * permiten salir de la cárcel.
	 */
	public Jugador(String nombre, Ficha ficha, Juego juego,
			Casillero casilleroActual) {
		this.nombre = nombre;
		this.ficha = ficha;
		this.juego = juego;
		this.dinero = 1500;
		this.nroCasas = 0;
		this.nroHoteles = 0;
		this.casilleroActual = casilleroActual;
		this.tarjetaCarcelList = new ArrayList<>();
		this.tarjPropiedadList = new ArrayList<>();
		this.isPreso = false;
		this.contDadosDobles = 0;
		this.contTurnosCarcel = 0;
		this.enSubasta = false;
	}

	/**
	 * @return the ficha
	 */
	public Ficha getFicha() {
		return ficha;
	}

	/**
	 * @param ficha
	 *            the ficha to set
	 */
	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	/**
	 * @return the casilleroActual
	 */
	public Casillero getCasilleroActual() {
		return casilleroActual;
	}

	/**
	 * @param casilleroActual
	 *            the casilleroActual to set
	 */
	public void setCasilleroActual(Casillero casilleroActual) {
		this.casilleroActual = casilleroActual;
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
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 *            the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	/**
	 * Cantidad de dinero que posee el Jugador
	 * 
	 * @return el dinero en efectivo que posee el jugador
	 */
	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	/**
	 * @return the idJugador
	 */
	public Integer getIdJugador() {
		return idJugador;
	}

	/**
	 * @param idJugador
	 *            the idJugador to set
	 */
	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Determina si el jugador se encuentra en la cárcel.
	 * 
	 * @return {@code true} si el jugador está preso {@code true} si el jugador
	 *         no está preso
	 */
	public boolean estaPreso() {
		return isPreso;
	}

	public void setPreso(boolean isPreso) {
		this.isPreso = isPreso;
	}

	/**
	 * Indica la cantidad de veces que el jugador saco valores iguales en la
	 * tirada de dados.
	 * 
	 * @return Nro de veces
	 */
	public int getCatidadDadosDobles() {
		return contDadosDobles;
	}

	public void resetCatidadDadosDobles() {
		this.contDadosDobles = 0;
	}

	/**
	 * Incrementa el valor cuando el jugador ha sacado valores iguales en la
	 * tirada de dado.
	 */
	public void incrementarCantidadDadosDobles() {
		this.contDadosDobles++;
	}

	/**
	 * Indica la cantidad de veces en la que el jugador ha estado en la cárcel
	 * cuando le tocaba su turno.
	 * 
	 * @return
	 */
	public int getCantidadTurnosCarcel() {
		return contTurnosCarcel;
	}

	/**
	 * Incrementa la cantidad de veces en la que el jugador ha estado en la
	 * cárcel cuando le tocó su turno.
	 */
	public void incrementarCantidadTurnosCarcel() {
		this.contTurnosCarcel++;
	}

	public void resetCantidadTurnosCarcel() {
		this.contTurnosCarcel = 0;
	}

	public int getUltimoResultado() {
		return ultimoResultado.getSuma();
	}

	public String getParDados() {
		return String.format("(%s - %s)", ultimoResultado.getValorDado(1),
				ultimoResultado.getValorDado(2));
	}

	public void setUltimoResultado(Dado ultimoResultado) {
		this.ultimoResultado = ultimoResultado;
	}

	public boolean tiroDobles() {
		return ultimoResultado.EsDoble();
	}

	/**
	 * Devuelve todas las propiedades del jugador que pueden ser hipotecadas.
	 * Una propiedad puede ser hipotecada si no está hipotecada y si no tiene
	 * edificaciones (casas u hoteles construídos).
	 * 
	 * @return Una lista con las propiedades del jugador que pueden ser
	 *         hipotecadas
	 */
	public List<TarjetaPropiedad> getPropiedadesHipotecables() {
		List<TarjetaPropiedad> list = new ArrayList<TarjetaPropiedad>();

		for (TarjetaPropiedad tarjetaPropiedad : this.tarjPropiedadList) {
			if (!tarjetaPropiedad.isHipotecada()) {
				if (tarjetaPropiedad.isPropiedadCalle()) {
					if (((CasilleroCalle) tarjetaPropiedad.getCasillero())
							.getNroCasas() == 0) {
						list.add(tarjetaPropiedad);
					}
				} else {
					list.add(tarjetaPropiedad);
				}
			}
		}
		return list;
	}

	/**
	 * Devuelve un listado de las propiedades del jugador que están hipotecadas.
	 * 
	 * @return Una lista con las propiedades del jugador que están hipotecadas y
	 *         pueden ser deshipotecadas
	 */
	public List<TarjetaPropiedad> getPropiedadesDeshipotecables() {
		List<TarjetaPropiedad> list = new ArrayList<TarjetaPropiedad>();

		for (TarjetaPropiedad tarjetaPropiedad : this.tarjPropiedadList) {
			if (tarjetaPropiedad.isHipotecada())
				list.add(tarjetaPropiedad);
		}

		return list;
	}

	/**
	 * Devuelve todos los colores de las propiedades del jugador que se pueden
	 * contruir
	 * 
	 * @return Una lista de colores construibles
	 */
	public List<String> getCallesConstruibles() {
		List<String> list = new ArrayList<String>();
		TarjetaCalle tarjetaCalle;
		EnumColor color;

		for (TarjetaPropiedad tarjetaPropiedad : this.tarjPropiedadList) {
			if (tarjetaPropiedad.isPropiedadCalle()) {
				tarjetaCalle = (TarjetaCalle) tarjetaPropiedad;
				color = tarjetaCalle.getEnumColor();

				if (this.poseeColorCompleto(color))
					if (!list.contains(color))
						list.add(color.getColor());
			}

		}

		return list;
	}

	/**
	 * Devuelve todos los conjuntos de calles del jugador que tienen
	 * construcciones y se pueden vender
	 * 
	 * @return Los monopolios con construcciones
	 */
	public List<String> getCallesDesconstruibles() {
		List<String> list = new ArrayList<String>();
		CasilleroCalle casilleroCalle;

		for (TarjetaPropiedad tarjetaPropiedad : this.tarjPropiedadList) {
			if (tarjetaPropiedad.isPropiedadCalle()) {
				casilleroCalle = (CasilleroCalle) tarjetaPropiedad
						.getCasillero();
				if (casilleroCalle.getNroCasas() > 0)
					list.add(((TarjetaCalle) tarjetaPropiedad).getColor());
			}
		}
		return list;
	}

	/**
	 * Retorna si el jugador posee todas las calles de un color
	 * 
	 * @param color
	 *            El Color de las tarjetas
	 * @return {@code true} si el jugador posee todas las terjetas del color
	 */
	public boolean poseeColorCompleto(TarjetaCalle.EnumColor color) {
		int contador = 0;
		TarjetaCalle tarjetaCalle;

		for (TarjetaPropiedad tarjetaPropiedad : this.getTarjPropiedadList()) {
			if (tarjetaPropiedad.isPropiedadCalle()) {
				tarjetaCalle = (TarjetaCalle) tarjetaPropiedad;
				if (tarjetaCalle.getEnumColor().getColor()
						.equals(color.getColor()))
					contador++;
			}
		}
		return (color.getCantMonopoly() == contador);
	}

	/**
	 * @return the tarjetaCarcelList
	 */
	public List<Tarjeta> getTarjetaCarcelList() {
		return tarjetaCarcelList;
	}

	/**
	 * @param tarjetaCarcelList
	 *            the tarjetaCarcelList to set
	 */
	public void setTarjetaCarcelList(List<Tarjeta> tarjetaCarcelList) {
		this.tarjetaCarcelList = tarjetaCarcelList;
	}

	/**
	 * @return the estadoJugador
	 */
	public EstadoJugador getEstadoJugador() {
		return estadoJugador;
	}

	/**
	 * @param estadoJugador
	 *            the estadoJugador to set
	 */
	public void setEstadoJugador(EstadoJugador estadoJugador) {
		this.estadoJugador = estadoJugador;
	}

	public boolean isEnSubasta() {
		return enSubasta;
	}

	public void setEnSubasta(boolean enSubasta) {
		this.enSubasta = enSubasta;
	}

	/**
	 * Devuelve la cantidad de turnos que el jugador estuvo en la carcel.
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int getContTurnosCarcel() {
		return contTurnosCarcel;
	}

	/**
	 * Suma uno al contador de turnos que el jugador está en la carcel.casas
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int addTurnoCarcel() {
		// TODO: verificar que no sea mas de tres y tirar una excepcion
		this.contTurnosCarcel++;
		return this.contTurnosCarcel;
	}

	/**
	 * Pone en cero el contador de turnos en la carcel del jugador.
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int resetTurnoCarcel() {
		int tmpTurnos = this.contTurnosCarcel;
		this.contTurnosCarcel = 0;
		return tmpTurnos;
	}

	/**
	 * Agrega una propiedad a la lista de propiedades del jugador y asigna el
	 * jugador a la propiedad
	 * 
	 * @param tarjeta
	 *            La propiedad que compra
	 * @return true si se agrego correctamente
	 */
	public boolean adquirirPropiedad(TarjetaPropiedad tarjeta) {
		if (!this.tarjPropiedadList.add(tarjeta))
			return false;
		tarjeta.setJugador(this);
		GestorLogs.registrarDebug("El jugador " + this.getNombre() + " compró "
				+ tarjeta.getNombre());
		return true;
	}

	/**
	 * Le vende una propiedad a otro jugador. Si la propiedad es una calle, no
	 * debe tener construcciones para ser vendida.
	 * 
	 * @param tarjeta
	 *            La tarjeta de la propiedad que se vende
	 * @param jugador
	 *            El jugador al cual se le vende la propiedad
	 * @return true si puede vender
	 * @throws SinDineroException
	 *             Si el jugador que compra no tiene dinero suficiente para
	 *             pagar la propiedad, se lanza una {@code SinDineroException}
	 */
	public boolean venderPropiedad(TarjetaPropiedad tarjeta, Jugador jugador,
			int monto) throws SinDineroException {
		if (tarjeta.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE
				&& ((CasilleroCalle) tarjeta.getCasillero()).getNroCasas() != 0)
			return false;

		jugador.pagarAJugador(this, monto);
		this.getTarjPropiedadList().remove(tarjeta);
		GestorLogs.registrarDebug("El jugador " + this.getNombre() + " vendió "
				+ tarjeta.getNombre() + " al jugador " + jugador.getNombre());
		return jugador.adquirirPropiedad(tarjeta);

	}

	/**
	 * Obtiene la propiedad del jugador.
	 * 
	 * @param tarjeta
	 * @return
	 */
	public TarjetaPropiedad getPropiedad(TarjetaPropiedad tarjeta){
		for(TarjetaPropiedad tarj : this.tarjPropiedadList){
			if(tarj.equals(tarjeta))
				return tarj;
		}
		return null;
	}
	
	/**
	 * Devuelte <strong>True<strong> si el jugador es propietario de la tarjeta.
	 * <strong>False</strong> caso contrario.
	 * 
	 * @param tarjeta Propiedad.
	 * 
	 * @return
	 */
	public boolean esPropietario(TarjetaPropiedad tarjeta){
		if(getPropiedad(tarjeta) != null)
			return true;
		return false;
	}
	
	/**
	 * Hipoteca una propiedad del jugador y cobra el monto de la hipoteca. Se
	 * verifica que la propiedad no esté ya hipotecada y que no tenga edificios
	 * en el caso de que sea una calle.
	 * 
	 * @param tarjeta
	 *            La propiedad que se quiere hipotecar.
	 * @return el valor hipotecario (o sea, lo que cobró el jugador por la
	 *         hipoteca) o 0 si no se hipoteca (porque la propiedad no es del
	 *         jugador, porque ya está hipotecada o porque tiene edificios).
	 */
	public int hipotecarPropiedad(TarjetaPropiedad tarjeta) {
		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			if (tarjetaPropiedad.equals(tarjeta)) {
				if (!tarjetaPropiedad.isHipotecada()) {
					if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE
							&& ((CasilleroCalle) tarjetaPropiedad
									.getCasillero()).getNroCasas() != 0)
						return 0;

					tarjetaPropiedad.setHipotecada(true);
					this.cobrar(tarjetaPropiedad.getValorHipotecario());
					GestorLogs.registrarDebug("El jugador " + this.getNombre()
							+ " ha hipotecado " + tarjetaPropiedad.getNombre());
					return tarjetaPropiedad.getValorHipotecario();
				}
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Deshipoteca una propiedad del jugador y paga el monto de la deshipoteca
	 * (valorhipoteca + 10% de interés). Se verifica que la propiedad esté
	 * hipotecada.
	 * 
	 * @param tarjeta
	 *            La propiedad que se quiere deshipotecar.
	 * @return el valor de la deshipotecario (o sea, lo que pagó el jugador para
	 *         deshipotecar) o 0 si no se deshipoteca (porque la propiedad no es
	 *         del jugador o porque no está hipotecada).
	 */
	public int dehipotecarPropiedad(TarjetaPropiedad tarjeta) {
		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			if (tarjetaPropiedad.equals(tarjeta)) {
				if (tarjetaPropiedad.isHipotecada()) {

					// valorDeshipoteca = valorhipoteca + 10%
					int valorDeshipoteca = (int) (tarjetaPropiedad
							.getValorHipotecario() * 1.10);

					if (this.getCapital() < valorDeshipoteca)
						return 0;

					tarjetaPropiedad.setHipotecada(false);
					this.pagar(valorDeshipoteca);
					GestorLogs.registrarDebug("El jugador " + this.getNombre()
							+ " ha deshipotecado "
							+ tarjetaPropiedad.getNombre());
					return valorDeshipoteca;
				}
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Devuelve true si el jugador puede pagar el monto indicado. Para el
	 * cálculo se tiene en cuenta el dinero en efectivo, el dinero que se puede
	 * obtener de la venta de casas/hoteles de sus propiedades y el valor de la
	 * hipoteca de todas sus propiedades. Si el Jugador no puede pagar el monto
	 * se debe declarar en bancarrota.
	 * 
	 * @param monto
	 *            el monto que se quiere consultar
	 * @return true si el jugador puede pagar ese monto
	 */
	public boolean puedePagar(int monto) {
		return (this.cuantoPuedePagar() >= monto);
	}

	/**
	 * Devuelve true si el jugador puede pagar el monto indicado solo con
	 * efectivo.
	 * 
	 * @param monto
	 *            el monto que se quiere consultar
	 * @return true si el jugador puede pagar ese monto
	 */
	public boolean puedePagarConEfectivo(int monto) {
		if (this.getDinero() >= monto)
			return true;
		return false;
	}

	/**
	 * resta del dinero del jugador el monto indicado
	 * 
	 * @param monto
	 *            el monto a pagar por el jugador
	 */
	public boolean pagar(int monto) {
		if (puedePagarConEfectivo(monto)) {
			GestorLogs.registrarDebug("(Jugador.java) El jugador "
					+ this.getNombre() + " pagó "
					+ StringUtils.formatearAMoneda(monto));
			this.setDinero(this.getDinero() - monto);
		} else {
			return false;
		}
		return true;
	}

	/**
	 * suma al dinero del jugador el monto indicado
	 * 
	 * @param monto
	 *            el monto a cobrar por el jugador
	 */
	public void cobrar(int monto) {
		GestorLogs.registrarDebug("El jugador " + this.getNombre() + " cobró "
				+ StringUtils.formatearAMoneda(monto));
		this.setDinero(this.getDinero() + monto);
	}

	/**
	 * Paga un monto de dinero a un jugador
	 * 
	 * @param jugador
	 * @param monto
	 * @throws SinDineroException
	 */
	public void pagarAJugador(Jugador jugador, int monto)
			throws SinDineroException {
		if (this.pagar(monto)) {
			GestorLogs.registrarDebug("El jugador " + this.getNombre()
					+ " pagó " + StringUtils.formatearAMoneda(monto)
					+ " al jugador " + jugador.getNombre());
			jugador.cobrar(monto);
		} else
			throw new SinDineroException(
					String.format(
							"El jugador %s no posee dinero suficiente para pagar %s € al jugador %s",
							this.getNombre(), monto, jugador.getNombre()));
	}

	/**
	 * Calula la cantidad de casas que tiene el jugador en todo el tablero
	 * 
	 * @return La cantidad total de casas que tiene el jugador
	 */
	public int getNroCasas() {
		int cantCasas = 0;

		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList)
			if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
				int tmpCasas = ((CasilleroCalle) tarjetaPropiedad
						.getCasillero()).getNroCasas();
				cantCasas += (tmpCasas < 5 ? tmpCasas : 0);
			}

		return cantCasas;
	}

	/**
	 * Calcula la cantidad de hoteles que tiene el jugador en todo el tablero
	 * 
	 * @return La cantidad total de hoteles que tiene el jugador
	 */
	public int getNroHoteles() {
		int cantHoteles = 0;

		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList)
			if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
				int tmpHoteles = ((CasilleroCalle) tarjetaPropiedad
						.getCasillero()).getNroCasas();
				cantHoteles += (tmpHoteles == 5 ? 1 : 0);
			}

		return cantHoteles;
	}

	/**
	 * Devuelve la cantidad de total de propiedades que tiene el jugador
	 * 
	 * @return La cantidad de propiedades que tiene el jugador
	 */
	public int getCantPropiedades() {
		return this.tarjPropiedadList.size();
	}

	/**
	 * Aumenta la cantidad de casas en "{@code cantidad}"
	 * 
	 * @param cantidad
	 *            La cantidad de casas que se quiere sumar
	 * @return El nuevo nro de casas
	 */
	public int incrementNroCasas(int cantidad) {
		nroCasas += cantidad;
		return nroCasas;
	}

	/**
	 * Aumenta la cantidad de hoteles en "{@code cantidad}"
	 * 
	 * @param cantidad
	 *            La cantidad de hoteles que se quiere sumar
	 * @return El nuevo nro de hoteles
	 */
	public int incrementNroHoteles(int cantidad) {
		nroHoteles += cantidad;
		return nroHoteles;
	}

	/**
	 * @return the tiradaInicial
	 */
	public Dado getTiradaInicial() {
		return tiradaInicial;
	}

	/**
	 * @param tiradaInicial
	 *            the tiradaInicial to set
	 */
	public void setTiradaInicial(Dado tiradaInicial) {
		this.tiradaInicial = tiradaInicial;
	}

	/**
	 * Calcula el capital total del jugador. Se calcula como la sumatoria de:
	 * <ul>
	 * <li>el valor de todas las propiedades que posea,</li>
	 * <li>el valor de las casas/hoteles que posea en sus propiedades y</li>
	 * <li>el dinero en efectivo.</li>
	 * </ul>
	 * 
	 * @return El capital total del jugador
	 */
	public int getCapital() {

		int tmpCapital = 0;
		CasilleroCalle tmpCasillero;
		TarjetaCalle tmpTarjeta;

		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			tmpCapital += tarjetaPropiedad.getValorPropiedad();

			if (tarjetaPropiedad.getCasillero().getTipoCasillero()
					.equals(Casillero.CASILLERO_CALLE)) {
				tmpTarjeta = (TarjetaCalle) tarjetaPropiedad;
				tmpCasillero = (CasilleroCalle) tarjetaPropiedad.getCasillero();
				tmpCapital += tmpCasillero.getNroCasas()
						* tmpTarjeta.getPrecioCadaCasa();
			}

		}
		tmpCapital += this.getDinero();
		return tmpCapital;
	}

	/**
	 * Calcula cual es el monto total que puede pagar el jugador. El método
	 * tiene en cuenta el dinero en efectivo, la venta de casas/hoteles y la
	 * hipoteca de propiedades. Se calcula como la sumatoria de:
	 * <ul>
	 * <li>el <b>valor de hipoteca</b> de las propiedades que posea,</li>
	 * <li>el <b>valor de venta</b> de las casas/hoteles que posea en sus propiedades
	 * (la mitad del valor de la construcción) y</li>
	 * <li>el dinero en efectivo.</li>
	 * </ul>
	 * 
	 * @return El monto total que el jugador puede pagar
	 */
	public int cuantoPuedePagar() {
		int tmpCapitalVenta = 0;
		CasilleroCalle tmpCasillero;
		TarjetaCalle tmpTarjeta;

		for (TarjetaPropiedad tarjetaPropiedad : tarjPropiedadList) {
			tmpCapitalVenta += tarjetaPropiedad.getValorHipotecario();

			if (tarjetaPropiedad.getCasillero().getTipoCasillero()
					.equals(Casillero.CASILLERO_CALLE)) {
				tmpTarjeta = (TarjetaCalle) tarjetaPropiedad;
				tmpCasillero = (CasilleroCalle) tarjetaPropiedad.getCasillero();
				tmpCapitalVenta += tmpCasillero.getNroCasas()
						* tmpTarjeta.getPrecioCadaCasa() / 2;
			}

		}
		tmpCapitalVenta += this.getDinero();
		return tmpCapitalVenta;
	}

	/**
	 * Devuelve true si el Jugador es un Jugador Virtual
	 * 
	 * @return true si el jugador es una instancia de {@code JugadorVirtual}
	 */
	public boolean isVirtual() {
		return this instanceof JugadorVirtual;
	}

	/**
	 * Devuelve true si el Jugador es un Jugador Humano
	 * 
	 * @return true si el jugador es una instancia de {@code JugadorHumano}
	 */
	public boolean isHumano() {
		return this instanceof JugadorHumano;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNombre().toLowerCase().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		return this.getNombre().toLowerCase()
				.equals(((Jugador) object).getNombre().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador [nombre: " + nombre);
		sb.append((this.juego != null) ? ", " + this.juego.toString()
				: "<SIN JUEGO>");
		sb.append((this.ficha != null) ? ", " + this.ficha.toString()
				: "<SIN FICHA>");
		sb.append("] }");

		return sb.toString();
	}
}
