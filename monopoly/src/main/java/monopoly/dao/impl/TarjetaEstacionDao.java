/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaEstacionDao;
import monopoly.model.tarjetas.TarjetaEstacion;
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
@Repository("tarjetaEstacionDao")
public class TarjetaEstacionDao extends CustomHibernateDaoSupport implements
		ITarjetaEstacionDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaEstacionDao#save(monopoly.model.tarjetas.TarjetaEstacion
	 * )
	 */
	@Override
	public void save(TarjetaEstacion tarjeta) {
		getHibernateTemplate().save(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.ITarjetaEstacionDao#update(monopoly.model.tarjetas.
	 * TarjetaEstacion)
	 */
	@Override
	public void update(TarjetaEstacion tarjeta) {
		getHibernateTemplate().update(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.ITarjetaEstacionDao#delete(monopoly.model.tarjetas.
	 * TarjetaEstacion)
	 */
	@Override
	public void delete(TarjetaEstacion tarjeta) {
		getHibernateTemplate().delete(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.ITarjetaEstacionDao#getAll()
	 */
	@Override
	public List<TarjetaEstacion> getAll() {
		List<TarjetaEstacion> tarjetasList = new ArrayList<TarjetaEstacion>();
		List<?> list = getHibernateTemplate().find("from TarjetaEstacion");
		if (!list.isEmpty()) {
			for (Object obj : list) {
				tarjetasList.add((TarjetaEstacion) obj);
			}
		}
		return tarjetasList;
	}

	@Override
	public TarjetaEstacion findByNombre(String nombreEstacion) {
		Session session = this.getSession();

		List<?> list = session.createCriteria(TarjetaEstacion.class)
				.add(Restrictions.eq("nombre", nombreEstacion)).list();
		if (!list.isEmpty()) {
			return (TarjetaEstacion) list.get(0);
		} else {
			GestorLogs
					.registrarError("No se encontró la TarjetaEstación con nombre "
							+ nombreEstacion);
		}

		return null;
	}

}
