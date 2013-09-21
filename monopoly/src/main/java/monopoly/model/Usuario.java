package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;

@Entity
@Table(name = "usuario", catalog = "monopoly_db")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "usuarioID")
	private long idUsuario;
	
	/**
	 * Nombre de usuario para el login
	 */
	@Column(name = "userName")
	private String userName;

	/**
	 * Nombre completo del usuario
	 */
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	public Usuario(String userName) {
		super();
		this.userName = userName;
		GestorLogs.registrarLog("Nuevo usuario '" + this.getUserName() + "'");
		GestorLogs.registrarDebug(this.toStringAll());
	}
	
	/**
	 * @param userName
	 * @param password
	 */
	public Usuario(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
		GestorLogs.registrarLog("Nuevo usuario '" + this.getUserName() + "'");
		GestorLogs.registrarDebug(this.toStringAll());
	}


	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Cambia el password del usuario
	 * 
	 * @param password
	 *            El password del usuario SIN ENCRIPTAR
	 */
	public void cambiarPassword(String password) {
		this.password = StringUtils.encPass(password);
		GestorLogs.registrarDebug("El password del usuario '"
				+ this.getUserName() + "' se modifico");
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
					+ this.getUserName() + "' CORRECTO");
			return true;
		} else {
			GestorLogs.registrarDebug("Password del usuario '"
					+ this.getUserName() + "' INCORRECTO");
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
		GestorLogs.registrarDebug("El email del usuario '" + this.getUserName()
				+ "' se modifico, ahora es '" + this.getEmail() + "'");
		return false;
	}

	/**
	 * @return the usuario
	 */
	public String getUserName() {
		return userName;
	}

	public String toString() {
		return "Usuario [ usuario=" + this.getUserName() + " ]";
	}

	public String toStringAll() {
		return "Usuario [ usuario=" + this.getUserName() + ", nombre="
				+ this.getNombre() + ", email=" + this.getEmail() + " ]";
	}

}
