/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaSuerteDao;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.CustomHibernateDaoSupport;

import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Repository("tarjetaSuerteDao")
public class TarjetaSuerteDao extends CustomHibernateDaoSupport implements
		ITarjetaSuerteDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaSuerteDao#save(monopoly.model.tarjetas.TarjetaSuerte
	 * )
	 */
	@Override
	public void save(TarjetaSuerte tarjeta) {
		getHibernateTemplate().save(tarjeta);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaSuerteDao#update(monopoly.model.tarjetas.TarjetaSuerte
	 * )
	 */
	@Override
	public void update(TarjetaSuerte tarjeta) {
		getHibernateTemplate().update(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.dao.ITarjetaSuerteDao#delete(monopoly.model.tarjetas.TarjetaSuerte
	 * )
	 */
	@Override
	public void delete(TarjetaSuerte tarjeta) {
		getHibernateTemplate().delete(tarjeta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.dao.ITarjetaSuerteDao#getAll()
	 */
	@Override
	public List<TarjetaSuerte> getAll() {
		List<TarjetaSuerte> tarjetasList = new ArrayList<TarjetaSuerte>();
		List<?> list = getHibernateTemplate().find("from TarjetaSuerte");
		if (!list.isEmpty()) {
			for (Object obj : list) {
				TarjetaSuerte ts = ((TarjetaSuerte) obj);
				ts.setObjetivo(ts.getObjetivo().replace("\n", "&#10;"));
				tarjetasList.add(ts);
			}
		}
		return tarjetasList;
	}

}
