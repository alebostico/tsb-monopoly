package monopoly.model;

import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;

public class Usuario {

	/**
	 * Nombre de usuario para el login
	 */
	private String usuario;

	/**
	 * Nombre completo del usuario
	 */
	private String nombre;

	private String password;

	private String email;

	public Usuario(String usuario) {
		super();
		this.usuario = usuario;
		GestorLogs.registrarLog("Nuevo usuario '" + this.getUsuario() + "'");
		GestorLogs.registrarDebug(this.toStringAll());
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Cambia el password del usuario
	 * 
	 * @param password
	 *            El password del usuario SIN ENCRIPTAR
	 */
	public void setPassword(String password) {
		this.password = StringUtils.encPass(password);
		GestorLogs.registrarDebug("El password del usuario '"
				+ this.getUsuario() + "' se modifico");
	}

	/**
	 * Verifica si el password (SIN ENCRIPTAR) pasado por parámetro es igual al
	 * del usuario. Usado para el login.
	 * 
	 * @param password
	 *            El password del usuario SIN ENCRIPTAR
	 * @return true si el password es correcto. false en caso contrario.
	 */
	public boolean checkPassword(String password) {
		if (this.getPassword().compareTo(StringUtils.encPass(password)) == 0) {
			GestorLogs.registrarDebug("Password del usuario '"
					+ this.getUsuario() + "' CORRECTO");
			return true;
		} else {
			GestorLogs.registrarDebug("Password del usuario '"
					+ this.getUsuario() + "' INCORRECTO");
			return false;
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setea un nuevo email. Verifica si el mail tiene un formato vválido.
	 * 
	 * @param email
	 *            the email to set
	 * @return true si el mail tiene un formato valido y se setea.
	 */
	public boolean setEmail(String email) {
		if (!StringUtils.validateEmail(email))
			return false;

		this.email = email;
		GestorLogs.registrarDebug("El email del usuario '" + this.getUsuario()
				+ "' se modifico, ahora es '" + this.getEmail() + "'");
		return false;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	public String toString() {
		return "Usuario [ usuario=" + this.getUsuario() + " ]";
	}

	public String toStringAll() {
		return "Usuario [ usuario=" + this.getUsuario() + ", nombre="
				+ this.getNombre() + ", email=" + this.getEmail() + " ]";
	}

}
