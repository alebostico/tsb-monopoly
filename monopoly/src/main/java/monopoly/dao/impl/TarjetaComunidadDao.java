/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaComunidadDao;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.util.CustomHibernateDaoSupport;

import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Repository("tarjetaComunidadDao")
public class TarjetaComunidadDao extends CustomHibernateDaoSupport implements ITarjetaComunidadDao {

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaComunidadDao#save(monopoly.model.tarjetas.TarjetaComunidad)
     */
    @Override
    public void save(TarjetaComunidad tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().save(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaComunidadDao#update(monopoly.model.tarjetas.TarjetaComunidad)
     */
    @Override
    public void update(TarjetaComunidad tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().update(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaComunidadDao#delete(monopoly.model.tarjetas.TarjetaComunidad)
     */
    @Override
    public void delete(TarjetaComunidad tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().delete(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaComunidadDao#getAll()
     */
    @Override
    public List<TarjetaComunidad> getAll() {
	// TODO Auto-generated method stub
	List<TarjetaComunidad> tarjetasList = new ArrayList<TarjetaComunidad>();
        List<?> list = getHibernateTemplate().find("from TarjetaComunidad");
        if (!list.isEmpty()) {
            for (Object obj : list) {
                tarjetasList.add((TarjetaComunidad) obj);
            }
        }
        return tarjetasList;
    }

}
