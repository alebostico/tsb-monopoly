/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.IUsuarioDao;
import monopoly.model.Usuario;
import monopoly.util.CustomHibernateDaoSupport;
import monopoly.util.GestorLogs;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Repository("usuarioDao")
public class UsuarioDao extends CustomHibernateDaoSupport implements
		IUsuarioDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#save(monopoly.model.Usuario)
	 */
	@Override
	public void save(Usuario usuario) {
		getHibernateTemplate().save(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#update(monopoly.model.Usuario)
	 */
	@Override
	public void update(Usuario usuario) {
		getHibernateTemplate().update(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#delete(monopoly.model.Usuario)
	 */
	@Override
	public void delete(Usuario usuario) {
		getHibernateTemplate().delete(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#getAll()
	 */
	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuariosList = new ArrayList<Usuario>();
		Session session = this.getSession();

		List<?> list = session.createCriteria(Usuario.class).list();
		if (!list.isEmpty()) {
			for (Object object : list) {
				usuariosList.add((Usuario) object);
			}
			return usuariosList;
		} else {
			GestorLogs
					.registrarWarning("No existen usuarios en la base de datos.");
		}

		return usuariosList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#validarUsuario(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Usuario validarUsuario(String userName, String password) {
		Session session = this.getSession();
		List<?> list = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("userName", userName))
				.add(Restrictions.eq("password", password)).list();
		if (list.isEmpty()) {
			String log = "No existe un usuario en la base de datos para los parámetros: userName:"
					+ userName + ", password: " + password + ".";
			GestorLogs.registrarWarning(log);
			return null;
		}
		return (Usuario) list.get(0);
	}
	
	@Override
	public Usuario validarUsuario(String userName) {
		Session session = this.getSession();
		List<?> list = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("userName", userName)).list();
		if (list.isEmpty()) {
			String log = "Ya existe un usuario registrado con nombre de usuario: "
					+ userName;
			GestorLogs.registrarWarning(log);
			return null;
		}
		return (Usuario) list.get(0);
	}

}
