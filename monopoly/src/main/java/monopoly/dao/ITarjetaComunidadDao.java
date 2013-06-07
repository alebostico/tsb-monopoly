/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.tarjetas.TarjetaComunidad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public interface ITarjetaComunidadDao {

    void save(TarjetaComunidad tarjeta);

    void update(TarjetaComunidad tarjeta);

    void delete(TarjetaComunidad tarjeta);
    
    List<TarjetaComunidad> getAll();
    
}
