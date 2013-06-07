/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.util.CustomHibernateDaoSupport;

import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Repository("tarjetaCompaniaDao")
public class TarjetaCompaniaDao extends CustomHibernateDaoSupport implements ITarjetaCompaniaDao {

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaCompaniaDao#save(monopoly.model.tarjetas.TarjetaCompania)
     */
    @Override
    public void save(TarjetaCompania tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().save(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaCompaniaDao#update(monopoly.model.tarjetas.TarjetaCompania)
     */
    @Override
    public void update(TarjetaCompania tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().update(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaCompaniaDao#delete(monopoly.model.tarjetas.TarjetaCompania)
     */
    @Override
    public void delete(TarjetaCompania tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().delete(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaCompaniaDao#getAll()
     */
    @Override
    public List<TarjetaCompania> getAll() {
	// TODO Auto-generated method stub
	List<TarjetaCompania> tarjetasList = new ArrayList<TarjetaCompania>();
        List<?> list = getHibernateTemplate().find("from TarjetaCompania");
        if (!list.isEmpty()) {
            for (Object obj : list) {
                tarjetasList.add((TarjetaCompania) obj);
            }
        }
        return tarjetasList;
    }

}
