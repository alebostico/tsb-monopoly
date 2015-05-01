/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.tarjetas.TarjetaSuerte;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 *
 */
public interface ITarjetaSuerteDao {

    void save(TarjetaSuerte tarjeta);

    void update(TarjetaSuerte tarjeta);

    void delete(TarjetaSuerte tarjeta);
    
    List<TarjetaSuerte> getAll();
    
}
