package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import monopoly.dao.IFichaDao;
import monopoly.model.Ficha;
import monopoly.util.CustomHibernateDaoSupport;

@Repository("fichaDao")
public class FichaDao extends CustomHibernateDaoSupport implements IFichaDao {

	@Override
	public void save(Ficha ficha) {
		getHibernateTemplate().save(ficha);
	}

	@Override
	public void update(Ficha ficha) {
		getHibernateTemplate().update(ficha);
	}

	@Override
	public void delete(Ficha ficha) {
		getHibernateTemplate().delete(ficha);
	}

	@Override
	public List<Ficha> getAll() {
		List<Ficha> fichasList = new ArrayList<Ficha>();
		List<?> list = getHibernateTemplate().find("from Ficha");
		if (!list.isEmpty()) {
			for (Object obj : list) {
				fichasList.add((Ficha) obj);
			}
		}
		return fichasList;
	}

}
