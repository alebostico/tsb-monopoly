/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.tarjetas.TarjetaEstacion;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public interface ITarjetaEstacionDao {

    void save(TarjetaEstacion tarjeta);

    void update(TarjetaEstacion tarjeta);

    void delete(TarjetaEstacion tarjeta);
    
    List<TarjetaEstacion> getAll();
    
}
