/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.tarjetas.TarjetaCompania;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 *
 */
public interface ITarjetaCompaniaDao {

    void save(TarjetaCompania tarjeta);

    void update(TarjetaCompania tarjeta);

    void delete(TarjetaCompania tarjeta);
    
    List<TarjetaCompania> getAll();
    
    public TarjetaCompania findByNombre(String nombreCompania);
    
}
