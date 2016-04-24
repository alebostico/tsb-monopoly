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
import monopoly.util.exception.EmailInvalidoException;

@Entity
@Table(name = "usuario", catalog = "monopoly_db")
public class Usuario implements Serializable {


	private static final long serialVersionUID = 671456948442187492L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "usuarioID")
	private int idUsuario;

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

	/**
	 * 
	 */
	public Usuario() {
		super();
	}

	public Usuario(String userName) {
		super();
		this.userName = userName;
		GestorLogs.registrarLog("Nuevo usuario '" + this.getUserName() + "'");
		GestorLogs.registrarDebug(this.toString());
	}

	/**
	 * @param userName
	 *            El nombre de usuario
	 * @param password
	 *            El password SIN ENCRIPTAR del usuario
	 */
	public Usuario(String userName, String password) {
		this(userName);
		this.cambiarPassword(password);
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Setea un nuevo nombre de usuario
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Devuelve el nombre completo del usuario
	 * 
	 * @return the nombre completo del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea un nuevo nombre completo de usuario
	 * 
	 * @param nombre
	 *            the Nombre Completo to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el password ENCRIPTADO del usuario
	 * 
	 * @return the password ENCRIPTADO del usario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setea un nuevo password YA ENCRIPTADO
	 * 
	 * @param password
	 *            El password YA ENCRIPTADO
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
	 * Devuelve el email del usuario
	 * 
	 * @return the email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setea un nuevo email. Verifica si el mail tiene un formato v&aacute;lido.
	 * 
	 * @param email
	 *            the email to set
	 * @return true si el mail tiene un formato valido y se setea.
	 * @throws EmailInvalidoException 
	 */
	public boolean setEmail(String email) throws EmailInvalidoException {
		if (!StringUtils.validateEmail(email))
			throw new EmailInvalidoException("El e-mail ingresado es inválido..");

		this.email = email;
		GestorLogs.registrarDebug("El email del usuario '" + this.getUserName()
				+ "' se modifico, ahora es '" + this.getEmail() + "'");
		return true;
	}

	/**
	 * Devuelve el nombre de usuario
	 * 
	 * @return El nombre de usuario
	 */
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "{ Usuario [ id: " + this.getIdUsuario()
				+ ", usuario: " + this.getUserName() 
				+ ", nombre: " + this.getNombre()
				+ ", e-mail: " + this.getEmail() + " ]}";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		Usuario jugador = (Usuario) object;
		if (!this.getUserName().equals(jugador.getUserName()))
			return false;

		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getUserName().hashCode();
	}
	
	

}
