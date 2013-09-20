/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.Ficha;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public interface IFichaDao {

	void save(Ficha ficha);

	void update(Ficha ficha);

	void delete(Ficha ficha);

	List<Ficha> getAll();

}
