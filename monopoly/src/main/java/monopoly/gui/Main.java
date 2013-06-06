/**
 * 
 */
package monopoly.gui;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.model.tarjetas.TarjetaCalle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Main {
    
    public static void main (String[] args)
    {
	
	ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
	
	ITarjetaCalleDao tarjetaCalleDao= (ITarjetaCalleDao) appContext.getBean("tarjetaCalleDao");
	
	List<TarjetaCalle> tarjetasCallesList = new ArrayList<TarjetaCalle>();
	
	tarjetasCallesList = tarjetaCalleDao.getAll();
	
	for (TarjetaCalle tarjetaCalle : tarjetasCallesList) {
	    System.out.println(tarjetaCalle.toString());
	}
	
    }

}
