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
 * @author Oliva Pablo
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
		// TODO Auto-generated method stub
		getHibernateTemplate().save(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#update(monopoly.model.Usuario)
	 */
	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#delete(monopoly.model.Usuario)
	 */
	@Override
	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(usuario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IUsuarioDao#getAll()
	 */
	@Override
	public List<Usuario> getAll() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Session session = this.getSession();
		Usuario usuario = (Usuario)session.createCriteria(Usuario.class)
				.add(Restrictions.like("userName", userName))
				.add(Restrictions.like("password", password)).setMaxResults(1);
		if(usuario == null)
		{
			String log = "No existe un usuario en la base de datos para los parámetros: userName:" 
						+ userName + ", password: " + password + ".";
			GestorLogs
					.registrarWarning(log);
			return null;
		}
		return usuario;
	}

}
