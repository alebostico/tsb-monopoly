/**
 * 
 */
package monopoly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaEstacionDao;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.util.CustomHibernateDaoSupport;

import org.springframework.stereotype.Repository;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Repository("tarjetaEstacionDao")
public class TarjetaEstacionDao extends CustomHibernateDaoSupport implements ITarjetaEstacionDao {

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaEstacionDao#save(monopoly.model.tarjetas.TarjetaEstacion)
     */
    @Override
    public void save(TarjetaEstacion tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().save(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaEstacionDao#update(monopoly.model.tarjetas.TarjetaEstacion)
     */
    @Override
    public void update(TarjetaEstacion tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().update(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaEstacionDao#delete(monopoly.model.tarjetas.TarjetaEstacion)
     */
    @Override
    public void delete(TarjetaEstacion tarjeta) {
	// TODO Auto-generated method stub
	getHibernateTemplate().delete(tarjeta);
    }

    /* (non-Javadoc)
     * @see monopoly.dao.ITarjetaEstacionDao#getAll()
     */
    @Override
    public List<TarjetaEstacion> getAll() {
	// TODO Auto-generated method stub
	List<TarjetaEstacion> tarjetasList = new ArrayList<TarjetaEstacion>();
        List<?> list = getHibernateTemplate().find("from TarjetaEstacion");
        if (!list.isEmpty()) {
            for (Object obj : list) {
                tarjetasList.add((TarjetaEstacion) obj);
            }
        }
        return tarjetasList;
    }

}
