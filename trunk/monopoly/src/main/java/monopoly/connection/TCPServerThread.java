/**
 * 
 */
package monopoly.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import monopoly.util.EnumMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPServerThread extends Thread {

	private static Socket socket;
	static String socketip;
	static int socketport;
	private static List<TCPServerThread> clientesActivos = new ArrayList<TCPServerThread>();
	private DataInputStream entrada = null;
	private DataOutputStream salida = null;
	private final static EnumMensaje[] mensaje = EnumMensaje.values();
	private String nombreUsuario = "";

	public TCPServerThread(Socket s) {
		// TODO Auto-generated constructor stub
		socket = s;
		socketip = s.getInetAddress().getHostAddress();
		socketport = s.getPort();
	}

	public void run() {
		try {
			entrada = new DataInputStream(socket.getInputStream());
			salida = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String mensajeCliente = "";
//		int numeroCliente = 0;
		
		while (true) {
			try {
				switch (mensaje[entrada.readInt()]) {
				case LOGIN:// envio de mensage a todos
					mensajeCliente = entrada.readUTF();
					enviarMensaje(mensajeCliente);
					break;
				case INICIAR_PARTIDA:// envio de lista de activos
//					numUsers = clientesActivos.size();
//					salida.writeInt(numUsers);
//					for (int i = 0; i < numUsers; i++)
//						salida.writeUTF(clientesActivos.get(i).nameUser);
					break;
				default: // envia mensage a uno solo
//					amigo = entrada.readUTF();// captura nombre de amigo
//					mencli = entrada.readUTF();// mensage enviado
//					enviaMsg(amigo, mencli);
					break;
				}
			} catch (IOException e) {
				System.out.println("El cliente termino la conexion");
				break;
			}
		}
		clientesActivos.remove(this);
		try {
//			ventana.actualizarChat("Se desconecto un usuario");
			socket.close();
		} catch (Exception et) {
//			ventana.actualizarChat("no se puede cerrar el socket");
		}
	}

	private void enviarMensaje(String mensaje) {
		TCPServerThread user=null;
        for(int i=0;i<clientesActivos.size();i++)
        {
           try{
              user=clientesActivos.get(i);
              user.salida.writeInt(1);//opcion de mensage 
              user.salida.writeUTF(this.getNombreUsuario()+" >"+ mensaje + "\n");
            }catch (IOException e)
            	{e.printStackTrace();}
        }
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

}
