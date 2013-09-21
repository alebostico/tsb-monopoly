/**
 * 
 */
package monopoly.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import monopoly.controller.GestorJuego;
import monopoly.controller.GestorLogin;
import monopoly.model.Usuario;
import monopoly.util.EnumMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPServerThread extends Thread {

	/*
	 * Datos del socket cliente.
	 */
	private static Socket socket;
	static String socketip;
	static int socketport;
	
	/*
	 * Datos del servidor padre.
	 */
	private TCPServer servidorPadre;
	private int idThreadServer = 0;
	
	/*
	 * Stream para enviar y recibir datos.
	 */
	private DataInputStream entrada = null;
	private DataOutputStream salida = null;

	/*
	 * Atributos para la lógica del juego.
	 */
	private GestorJuego gestorJuegos = GestorJuego.getInstance();
	private Usuario usuarioLogueado = null;

	/*
	 * Atributos para la codificación de los mensajes.
	 */
	private final static EnumMensaje[] mensaje = EnumMensaje.values();
	private String delimitador = "&-&-&";

	public TCPServerThread(Socket s, TCPServer server, int idThreadServidor) {
		// TODO Auto-generated constructor stub
		socket = s;
		socketip = s.getInetAddress().getHostAddress();
		socketport = s.getPort();
		this.servidorPadre = server;
		this.idThreadServer = idThreadServidor;
	}

	public void run() {
		try {
			entrada = new DataInputStream(socket.getInputStream());
			salida = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String mensajeCliente = "";
		// int numeroCliente = 0;

		while (true) {
			try {
				switch (entrada.readInt()) {
				case 1:// envio de mensage a todos
					mensajeCliente = entrada.readUTF();
					usuarioLogueado = GestorLogin
							.validarUsuario(analizarCadena(mensajeCliente));

					servidorPadre.avisarResultadoLogueo(usuarioLogueado, idThreadServer);
					break;
				case 2:// envio de lista de activos
					// numUsers = clientesActivos.size();
					// salida.writeInt(numUsers);
					// for (int i = 0; i < numUsers; i++)
					// salida.writeUTF(clientesActivos.get(i).nameUser);
					break;
				default: // envia mensage a uno solo
					// amigo = entrada.readUTF();// captura nombre de amigo
					// mencli = entrada.readUTF();// mensage enviado
					// enviaMsg(amigo, mencli);
					break;
				}
			} catch (IOException e) {
				System.out.println("El cliente termino la conexion");
				break;
			}
		}
		try {
			// ventana.actualizarChat("Se desconecto un usuario");
			socket.close();
		} catch (Exception et) {
			// ventana.actualizarChat("no se puede cerrar el socket");
		}
	}
	
	void avisarResultadoLogueo(String contenidoLineaEntrada)
	{
		try {
			salida.writeInt(EnumMensaje.LOGIN.getIdMensaje());
			salida.writeUTF(contenidoLineaEntrada);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Resultado de login
		
		
	}

	private void enviarMensaje(String mensaje) {
		// TCPServerThread user=null;
		// for(int i=0;i<clientesActivos.size();i++)
		// {
		// try{
		// user=clientesActivos.get(i);
		// user.salida.writeInt(1);//opcion de mensage
		// user.salida.writeUTF(this.getNombreUsuario()+" >"+ mensaje + "\n");
		// }catch (IOException e)
		// {e.printStackTrace();}
		// }
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
