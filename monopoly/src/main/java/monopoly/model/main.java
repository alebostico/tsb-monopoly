package monopoly.model;

import java.awt.List;
import java.util.ArrayList;

public class main {

    /**
     * @param args
     */
    public static ArrayList<Ficha> fichas;
    
    
    public static void main(String[] args) {
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
