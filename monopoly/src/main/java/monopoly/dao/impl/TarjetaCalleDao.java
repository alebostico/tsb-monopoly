/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.model.tarjetas.TarjetaCalle;
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
@Repository("tarjetaCalleDao")
public class TarjetaCalleDao extends CustomHibernateDaoSupport implements
		ITarjetaCalleDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaCalleDao#save(monopoly.model.tarjetas.TarjetaCalle)
	 */
	@Override
	public void save(TarjetaCalle tarjeta) {
		getHibernateTemplate().save(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaCalleDao#update(monopoly.model.tarjetas.TarjetaCalle
	 * )
	 */
	@Override
	public void update(TarjetaCalle tarjeta) {
		getHibernateTemplate().update(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaCalleDao#delete(monopoly.model.tarjetas.TarjetaCalle
	 * )
	 */
	@Override
	public void delete(TarjetaCalle tarjeta) {
		getHibernateTemplate().delete(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.ITarjetaCalleDao#getAll()
	 */
	@Override
	public List<TarjetaCalle> getAll() {
		List<TarjetaCalle> tarjetasList = new ArrayList<TarjetaCalle>();
		List<?> list = getHibernateTemplate().find("from TarjetaCalle");
		if (!list.isEmpty()) {
			for (Object obj : list) {
				tarjetasList.add((TarjetaCalle) obj);
			}
		}
		return tarjetasList;
	}

	public TarjetaCalle findByNombre(String nombreCalle) {
		// List<TarjetaCalle> tarjetasList = new ArrayList<TarjetaCalle>();
		Session session = this.getSession();

		List<?> list = session.createCriteria(TarjetaCalle.class)
				.add(Restrictions.eq("nombre", nombreCalle)).list();
		if (!list.isEmpty()) {
			return (TarjetaCalle) list.get(0);
		} else {
			GestorLogs
					.registrarError("No se encontró la TarjetaCalle con nombre "
							+ nombreCalle);
		}
		return null;
	}
}
