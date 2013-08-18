/**
 * 
 */
package monopoly.gui;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.model.Ficha;
import monopoly.model.Jugador;
import monopoly.model.Ronda;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.util.GestorLogs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Main {
    
    /**
     * @param args
     */
    public static ArrayList<Ficha> fichas;
    
    public static void main (String[] args)
    {
	
	ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
	
	ITarjetaCalleDao tarjetaCalleDao= (ITarjetaCalleDao) appContext.getBean("tarjetaCalleDao");
	
	List<TarjetaCalle> tarjetasCallesList = new ArrayList<TarjetaCalle>();
	
	tarjetasCallesList = tarjetaCalleDao.getAll();
	
	for (TarjetaCalle tarjetaCalle : tarjetasCallesList) {
	    System.out.println(tarjetaCalle.toString() + "\n");
	}
	
	GestorLogs.registrarLog("Mostrar lista de tarjetas de propiedad");
	
	fichas=crearFichas();
	Ronda ronda= new Ronda();
	System.out.print("Fichas");
	System.out.print(fichas.toString());
	for(int i=0;i<12;i++)
	{
	    Jugador j=new Jugador();
	    j.setFicha(fichas.get(i));
	    ronda.agregarJugador(j);
	}
	
    }
    
    public static ArrayList<Ficha> crearFichas()
	{
		fichas= new ArrayList<Ficha>();
		Ficha carretilla= new Ficha();
		carretilla.setNombre("carretilla");
		fichas.add(carretilla);
		Ficha buque= new Ficha();
		buque.setNombre("buque");
		fichas.add(buque);
		Ficha sacoDinero= new Ficha();
		sacoDinero.setNombre("sacoDinero");
		fichas.add(sacoDinero);
		Ficha jineteCaballo= new Ficha();
		jineteCaballo.setNombre("jineteCaballo");
		fichas.add(jineteCaballo);
		Ficha vehiculo= new Ficha();
		vehiculo.setNombre("vehiculo");
		fichas.add(vehiculo);
		Ficha tren= new Ficha();
		tren.setNombre("tren");
		fichas.add(tren);
		Ficha dedal= new Ficha();
		dedal.setNombre("dedal");
		fichas.add(dedal);
		Ficha obus= new Ficha();
		obus.setNombre("obus");
		fichas.add(obus);
		Ficha zapato= new Ficha();
		zapato.setNombre("zapato");
		fichas.add(zapato);
		Ficha perro= new Ficha();
		perro.setNombre("perro");
		fichas.add(perro);
		Ficha plancha= new Ficha();
		plancha.setNombre("plancha");
		fichas.add(plancha);
		Ficha sombreroCopa= new Ficha();
		sombreroCopa.setNombre("sombreroCopa");
		fichas.add(sombreroCopa);
		
		return fichas;
	}

}
