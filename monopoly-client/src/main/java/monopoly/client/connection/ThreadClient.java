/**
 * 
 */
package monopoly.client.connection;

import java.io.DataInputStream;
import java.io.IOException;

import monopoly.util.EnumMensaje;
import monopoly.util.GestorLogs;

/**
 * @author pablo
 * 
 */
public class ThreadClient extends Thread {

	private DataInputStream entrada;
	private TCPClient cliente;
	private final static EnumMensaje[] mensaje = EnumMensaje.values();
	
	public ThreadClient(){}
	
	public ThreadClient(TCPClient cliente, DataInputStream entrada) throws IOException
	{
		this.cliente = cliente;
		this.entrada = entrada;
	}
	
	public void run()
	{
		String mensajeServidor = "";
		
		while(true){
			try
			{	
				switch (mensaje[entrada.readInt()]) {
				case LOGIN:
					mensajeServidor = entrada.readUTF();
					break;

				default:
					break;
				}
				
			}catch(IOException ex)
			{
				GestorLogs.registrarError(ex.getMessage());
			}
		}
	}

}
