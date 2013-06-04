/**
 * 
 */
package monopoly.gui;

import monopoly.dao.ITarjetaCalleDao;

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
	/*
	TarjetaCalle tc = new TarjetaCalle();
	tc.setIdTarjeta(1);
	tc.setNombre("Suerte");
	tc.setPrecioAlquiler(100);
	tc.setPrecioCadaCasa(100);
	tc.setPrecioCadaHotel(200);
	tc.setValorTresCasas(300);
	tc.setValorDosCasas(200);
	tc.setValorUnaCasa(100);
	
	tarjetaCalleDao.save(tc);
	*/
	
    }

}
