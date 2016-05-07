package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import monopoly.dao.IJuegoDao;
import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.CustomHibernateDaoSupport;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Repository("juegoDao")
public class JuegoDao extends CustomHibernateDaoSupport implements IJuegoDao {

	public JuegoDao() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IJuegoDao#save(monopoly.model.Juego)
	 */
	@Override
	public void save(Juego juego) {
		getHibernateTemplate().save(juego);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IJuegoDao#update(monopoly.model.Juego)
	 */
	@Override
	public void update(Juego juego) {
		getHibernateTemplate().update(juego);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IJuegoDao#delete(monopoly.model.Juego)
	 */
	@Override
	public void delete(Juego juego) {
		getHibernateTemplate().delete(juego);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IJuegoDao#getAll()
	 */
	@Override
	public List<Juego> getAll() {
		List<Juego> juegosList = new ArrayList<Juego>();
		Session session = this.getSession();

		List<?> list = session.createCriteria(Juego.class).list();
		if (!list.isEmpty()) {
			for (Object object : list) {
				juegosList.add((Juego) object);
			}
			return juegosList;
		} else {
			GestorLogs
					.registrarWarning("No existen juegos en la base de datos.");
		}

		return juegosList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.IJuegoDao#getJuegoGuardados(monopoly.model.Usuario)
	 */
	@Override
	public List<Juego> getJuegoGuardados(Usuario usuario) {
		Session session = this.getSession();
		List<Juego> juegosLista = new ArrayList<Juego>();
		List<?> list = session.createCriteria(Juego.class)
				.add(Restrictions.eq("owner", usuario))
				.add(Restrictions.isNull("fechaRestaurado")).list();
		if (list.isEmpty()) {
			String log = "No existen juegos en la base de datos para el usuario "
					+ usuario.getUserName() + " (" + usuario.getNombre() + ").";
			GestorLogs.registrarWarning(log);
			return null;
		} else {
			for (Object object : list) {
				juegosLista.add((Juego) object);
			}
			return juegosLista;
		}
	}

	@Override
	public Juego findJuegoByName(String nombre) {
		Session session = this.getSession();
		List<?> list = session.createCriteria(Juego.class)
				.add(Restrictions.eq("nombreJuego", nombre)).list();
		if (list.isEmpty()) {
			String log = "No existen juegos con el nombre " + nombre + ".";
			GestorLogs.registrarWarning(log);
			return null;
		} else {
			return (Juego) list.get(0);
		}
	}

	/**
	 * Busca un juego en la base de datos a partir de uniqueID
	 * 
	 * @param uniqueID
	 *            El {@code uniqueID} del juego que se quiere buscar
	 * @return El {@code Juego} o {@code null} si no se encuentra
	 */
	@Override
	public Juego findJuegoByUniqueId(String uniqueID) {
		Session session = this.getSession();
		List<?> list = session.createCriteria(Juego.class)
				.add(Restrictions.eq("uniqueID", uniqueID)).list();
		if (list.isEmpty()) {
			String log = "No existen juegos con el ID " + uniqueID + ".";
			GestorLogs.registrarWarning(log);
			return null;
		} else {
			return (Juego) list.get(0);
		}
	}
}
