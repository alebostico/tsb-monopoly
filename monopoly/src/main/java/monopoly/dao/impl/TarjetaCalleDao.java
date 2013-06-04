/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.util.CustomHibernateDaoSupport;

import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Repository("tarjetaCalleDao")
public class TarjetaCalleDao extends CustomHibernateDaoSupport implements ITarjetaCalleDao {

    /*
     * (non-Javadoc)
     * 
     * @see monopoly.dao.ITarjetaCalleDao#save(monopoly.model.tarjetas.TarjetaCalle)
     */
    @Override
    public void save(TarjetaCalle tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().save(tarjeta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see monopoly.dao.ITarjetaCalleDao#update(monopoly.model.tarjetas.TarjetaCalle)
     */
    @Override
    public void update(TarjetaCalle tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().update(tarjeta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see monopoly.dao.ITarjetaCalleDao#delete(monopoly.model.tarjetas.TarjetaCalle)
     */
    @Override
    public void delete(TarjetaCalle tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().delete(tarjeta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see monopoly.dao.ITarjetaCalleDao#getAll()
     */
    @Override
    public List<TarjetaCalle> getAll() {
	// TODO Auto-generated method stub
	List<TarjetaCalle> tarjetasList = new ArrayList<TarjetaCalle>();
        List<?> list = getHibernateTemplate().find("from TarjetaCalle");
        if (!list.isEmpty()) {
            for (Object obj : list) {
                tarjetasList.add((TarjetaCalle) obj);
            }
        }
        return tarjetasList;
    }

}
