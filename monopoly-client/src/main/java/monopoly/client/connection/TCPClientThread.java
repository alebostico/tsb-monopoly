/**
 * 
 */
package monopoly.client.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import monopoly.client.gui.FXMLIniciarSesion;
import monopoly.model.Usuario;
import monopoly.util.EnumMensaje;
import monopoly.util.GestorLogs;

/**
 * @author pablo
 * 
 */
public class TCPClientThread extends Thread {

	private DataInputStream entrada;
	private TCPClient cliente;
	private FXMLIniciarSesion ventanaInicioSesion;

	/*
	 * Atributos para la codificación de mensajes.
	 */
	private String delimitador = "&-&-&";
	private final static EnumMensaje[] mensaje = EnumMensaje.values();

	public TCPClientThread() {
	}

	public TCPClientThread(TCPClient cliente,
			FXMLIniciarSesion ventIniciarSesion) throws IOException {
		this.cliente = cliente;
		this.ventanaInicioSesion = ventIniciarSesion;
	}

	public void run() {
		String mensajeServidor = "";

		while (true) {
			try {
				switch (mensaje[entrada.readInt()]) {
				case LOGIN:
					mensajeServidor = entrada.readUTF();
					ArrayList<String> contenido = analizarCadena(mensajeServidor);
					if (contenido.size() == 1)
						ventanaInicioSesion.resultadoLogueo(false, null);
					else {
						Usuario usuarioLogueado = new Usuario(contenido.get(2));
						usuarioLogueado.setIdUsuario(Integer.parseInt(contenido.get(1)));
						usuarioLogueado.setPassword(contenido.get(3)) ;
						usuarioLogueado.setNombre(contenido.get(4)) ;
						usuarioLogueado.setEmail(contenido.get(5)) ;
						ventanaInicioSesion.resultadoLogueo(true, usuarioLogueado);
					}
					break;

				default:
					break;
				}

			} catch (IOException ex) {
				GestorLogs.registrarError(ex.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param mensajeContenido
	 * @return
	 */
	@SuppressWarnings("resource")
	private ArrayList<String> analizarCadena(String mensajeContenido) {
		ArrayList<String> tokens = new ArrayList<String>();
		Scanner tokenize = new Scanner(mensajeContenido);
		tokenize.useDelimiter(delimitador);
		while (tokenize.hasNext()) {
			tokens.add(tokenize.next());
		}
		return tokens;
	}

}
