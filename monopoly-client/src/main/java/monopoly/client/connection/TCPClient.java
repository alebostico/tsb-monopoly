/**
 * 
 */
package monopoly.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;
import monopoly.util.ConstantesMensaje;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;
import monopoly.util.StringUtils;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPClient extends Thread {

	private static final String IPSERVIDOR = LectorXML.getIpServidor();
	private static final int PUERTO = LectorXML.getPuertoDeConexion();
	private static Socket clientSocket;
	private Usuario usuarioLogueado;

	private PrintWriter salida;
	private BufferedReader entrada;

	private LoginController loginController;

	private boolean listening = true;
	private String delimitador = "&-&-&";

	/**
	 * @param jugador
	 */
	public TCPClient(LoginController ventIniciarSesion) {
		super();
		loginController = ventIniciarSesion;
	}

	public void run() {
		if (loginController != null) {

			try {
				clientSocket = new Socket(IPSERVIDOR, PUERTO);
				salida = new PrintWriter(clientSocket.getOutputStream(), true);
				entrada = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				GestorLogs.registrarLog("Cliente en linea...");

				String lineaEntrada = "";
				ArrayList<String> contenidoLineaEntrada = null;

				while (listening) {
					lineaEntrada = entrada.readLine();
					if (lineaEntrada != null) {
						contenidoLineaEntrada = StringUtils
								.analizarCadena(lineaEntrada);
						switch (contenidoLineaEntrada.get(0)) {

						case ConstantesMensaje.LOGIN:
							/**
							 * posicion - parametro 0 - TipoMensaje 1 - existe 2
							 * - idUsuario 3 - userName 4 - pass 5 - nombre 6 -
							 * email
							 * 
							 **/
							if (!Boolean.parseBoolean(contenidoLineaEntrada
									.get(1)))
								loginController.resultadoLogueo(false, null);
							else {
								usuarioLogueado = new Usuario(
										contenidoLineaEntrada.get(3));
								usuarioLogueado
										.setIdUsuario(Integer
												.parseInt(contenidoLineaEntrada
														.get(2)));
								usuarioLogueado
										.setPassword(contenidoLineaEntrada
												.get(4));
								usuarioLogueado.setNombre(contenidoLineaEntrada
										.get(5));
								usuarioLogueado.setEmail(contenidoLineaEntrada
										.get(6));
								loginController.resultadoLogueo(true,
										usuarioLogueado);
							}
							break;

						case ConstantesMensaje.INICIAR_PARTIDA:
							break;
						default:
							break;
						}
					}
				}

			} catch (UnknownHostException ex) {
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	public void iniciarSesion(Usuario usuario) {
		salida.println(ConstantesMensaje.LOGIN + delimitador
				+ usuario.getUserName() + delimitador + usuario.getPassword());
	}

	/**
	 * @return the usuarioLogueado
	 */
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/**
	 * @param usuarioLogueado the usuarioLogueado to set
	 */
	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
}
