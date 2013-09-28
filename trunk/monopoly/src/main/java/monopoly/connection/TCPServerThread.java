/**
 * 
 */
package monopoly.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import monopoly.controller.GestorJuego;
import monopoly.controller.GestorLogin;
import monopoly.model.Usuario;
import monopoly.util.ConstantesMensaje;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;

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
	private Socket socketCliente;
	static String socketip;
	static int socketport;
	private boolean corre = true;

	/*
	 * Datos del servidor padre.
	 */
	private TCPServer servidorPadre;
	private int idThreadServer = 0;

	/*
	 * Stream para enviar y recibir datos.
	 */
	private PrintWriter salida;
	private BufferedReader entrada;

	/*
	 * Atributos para la lógica del juego.
	 */
	private GestorJuego gestorJuegos = GestorJuego.getInstance();
	private Usuario usuarioLogueado = null;

	public TCPServerThread(Socket socketClient, TCPServer server,
			int idThreadServidor) {
		// TODO Auto-generated constructor stub
		socketCliente = socketClient;
		socketip = socketClient.getInetAddress().getHostAddress();
		socketport = socketClient.getPort();
		this.servidorPadre = server;
		this.idThreadServer = idThreadServidor;
	}

	public void run() {
		try {
			salida = new PrintWriter(socketCliente.getOutputStream(), true);
			entrada = new BufferedReader(new InputStreamReader(
					socketCliente.getInputStream()));
		} catch (IOException e) {
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
		}
		String lineaEntrada;
		ArrayList<String> contenidoLineaEntrada = null;

		while (corre) {
			try {
				lineaEntrada = entrada.readLine();
				if (lineaEntrada != null) {
					contenidoLineaEntrada = StringUtils
							.analizarCadena(lineaEntrada);
					switch (contenidoLineaEntrada.get(0)) {
					
					case ConstantesMensaje.LOGIN:
						System.out
								.println("Se recibió un mensaje para loguearse en el juego");
						
						usuarioLogueado = GestorLogin
								.validarUsuario(contenidoLineaEntrada.get(1), contenidoLineaEntrada.get(2));
						servidorPadre.avisarResultadoLogueo(usuarioLogueado,
								idThreadServer);
						break;
					case ConstantesMensaje.INICIAR_PARTIDA:
						System.out
								.println("Se recibió un mensaje para iniciar partida en el juego");
						// numUsers = clientesActivos.size();
						// salida.writeInt(numUsers);
						// for (int i = 0; i < numUsers; i++)
						// salida.writeUTF(clientesActivos.get(i).nameUser);
						break;
					default:
						System.out.println("valor por defecto");
						// amigo = entrada.readUTF();// captura nombre de amigo
						// mencli = entrada.readUTF();// mensage enviado
						// enviaMsg(amigo, mencli);
						break;
					}
				}
			} catch (IOException e) {
				System.out.println("El cliente termino la conexión..\n" + e.getMessage());
				break;
			}
		}
		try {
			socketCliente.close();
		} catch (Exception et) {
			System.out.println("El cliente termino la conexión..\n" + et.getMessage());
		}
	}

	void avisarResultadoLogueo(String contenidoLineaEntrada) {
		salida.println(contenidoLineaEntrada);
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

}
