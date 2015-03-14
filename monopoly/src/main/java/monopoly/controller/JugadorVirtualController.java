/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monopoly.model.Estado.EstadoJugador;
import monopoly.model.JugadorVirtual;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class JugadorVirtualController {

	/**
	 * Método por el cual un jugador paga una cantidad. Si la cantidad es
	 * positiva, el jugador pagará dinero, en caso de ser negativa, el jugador
	 * estará cobrando dinero. Al pagar, se debe restar la cantidad del dinero
	 * total y del capital. Si no tiene dinero suficiente se tratará de
	 * conseguir automáticamente mediante hipotecas y ventas de edificios. Si
	 * aun así no consigue el dinero suficiente se declara como moroso. Si es
	 * moroso devuelve -1.
	 */
	public static int pagar(Casillero[] casillerosList, JugadorVirtual jugador,
			int cantidad) {
		// 1. Intento vender edificios si no tengo dinero suficiente
		if (cantidad > jugador.getDinero()) {
			int dineroNecesario = cantidad - jugador.getDinero();
			switch (jugador.getTipoJugador()) {
			// Jugador basado en reglas
			case TJ_MAGNATE: {
				List<CasilleroCalle> construidos;
				// Busca la mejor opción
				construidos = calculoUtilidadEsperadaVender(dineroNecesario);
				if (!construidos.isEmpty()) {
					// Vende lo que le indica el algoritmo anterior
					for (int i = 0; i != construidos.size(); i++) {
						/*
						 * TODO: vender las casas y edificios cout <<
						 * get_nombre() << " ha eliminado un edificio de " <<
						 * construidos[i]->get_nombre() << endl;
						 * construidos[i]->venderSinComprobar();
						 */
					}
				}
				break;
			}
			// Jugador con comportamiento aleatorio
			case TJ_EMPRESARIO:
			case TJ_COMPRADOR_PRIMERIZO:
				venderAleatorio(jugador, dineroNecesario);
				break;
			}
		}

		// 2. Si aún no dispongo de dinero suficiente, intento hipotecar
		// propiedades
		if (cantidad > jugador.getDinero()) {
			int dineroNecesario = cantidad - jugador.getDinero();
			switch (jugador.getTipoJugador()) {
			case TJ_MAGNATE: // Jugador inteligente
			{
				List<Casillero> hipotecas;
				// Buscar la mejor opcion para hipotecar
				hipotecas = calculoUtilidadEsperadaHipotecar(dineroNecesario);
				if (!hipotecas.isEmpty()) {
					// Hipotecar las propiedades elegidas por el algoritmo
					// anterior
					for (int i = 0; i != hipotecas.size(); i++) {
						// TODO: Hipotecar las propiedades
						// cout << get_nombre() << " ha hipotecado " <<
						// hipotecas[i]->get_nombre() << endl;
						// hipotecas[i]->hipotecar();
					}
				}
				break;
			}
			case TJ_EMPRESARIO:
			case TJ_COMPRADOR_PRIMERIZO: // Jugador aleatorio
				hipotecarAleatorio(dineroNecesario);
				break;
			}
		}

		// PAGAR:
		// Si tengo dinero suficiente, se paga (descontando de dinero total y
		// capital). Aumentando si es cobrar
		if (cantidad <= jugador.getDinero()) {
			jugador.setDinero(jugador.getDinero() - cantidad); // TODO: OJO,
																// verificar que
																// no se haya
																// descontado ya
																// el dinero del
																// jugador
			// TODO set_dinero_total ( get_dinero_total() - cantidad );
			// TODO set_capital ( get_capital() - cantidad );
		}
		// Declaro al jugador moroso
		else {
			// Bancarrota
			jugador.setEstadoJugador(EstadoJugador.EJ_BANCARROTA);
			return -1;
		}
		return jugador.getDinero();
	}

	/**
	 * Método para vender casas del jugador aleatorio. Venderá todas las casas
	 * desde la propiedad más cara hasta la más barata hasta superar la cantidad
	 * necesaria. Así se asegura alcanzar el máximo dinero posible.
	 */
	private static void venderAleatorio ( JugadorVirtual jugador, int cantidad )
	{
	 //1- Selecciono el monopolio a construir entre todos los posibles.
	 // Para ello busco todas las calles con permiso de construcción y las ordeno en una lista
	 // Creare otra para meter las calles que ya se han comprobado y no repetir sobre el mismo
	 List<CasilleroCalle> propiedadesEdificadas;
	 
	 propiedadesEdificadas = new ArrayList<CasilleroCalle>();
	 	 
	for (Iterator<TarjetaPropiedad> iterator = jugador.getTarjPropiedadList().iterator();
			iterator.hasNext();) {
		TarjetaPropiedad tarjeta = (TarjetaPropiedad) iterator.next();
		
		if(tarjeta instanceof TarjetaCalle){
			CasilleroCalle casillero = (CasilleroCalle) tarjeta.getCasillero();
			if (casillero.getNroCasas() > 0){
				int h = 0;
				while ( h != propiedadesEdificadas.size () 
						&& ((TarjetaCalle)propiedadesEdificadas.get(h).
								getTarjetaCalle()).getPrecioCadaCasa() > 
								((TarjetaCalle) tarjeta).getPrecioCadaCasa()){
					
					h++;
				}
				propiedadesEdificadas.add(h, casillero);
			}
		}
			
	}
			 
	
	
	 // Selecciono el monopolio a vender -> El primero, si no hay dinero suficiente, el segundo...
	 int i = 0;
	 List<CasilleroCalle> callesTestadas;
	 List<TarjetaPropiedad> vender;
	 boolean posible = true;
	 // Mientras siga haciendo falta dinero y no haya llegado al final de la lista de calles edificadas.
	 while ( cantidad > jugador.getDinero() && i != propiedadesEdificadas.size() )
	   {      
	     int j = 0;
	     while ( j != callesTestadas.size() && posible )
	     {
		  if ( propiedadesEdificadas.get(i) == callesTestadas.get(j) )
		    posible = false;
		  j++;
		}
	     //Si se ha encontrado un monopolio vendible.
	     //Se marca para no volver a venderle y se venden sus casas hasta acabar con el dinero
	     if ( posible ){
		  //TODO: vender = propiedadesEdificadas[i]->get_monopolioCompleto ( propiedadesEdificadas[i] );
	    	 
	    	 for (TarjetaPropiedad tarjetaPropiedad : vender) {
				if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE){
					callesTestadas.remove(((TarjetaCalle)tarjetaPropiedad).getCasillero());
				}
			}
	     

		  boolean existenEdificios = true;
		  while ( cantidad > jugador.getDinero() && existenEdificios )
		    {
			  Iterator<TarjetaPropiedad> iterVender = vender.iterator();
		      
		      //Vender de una calle
		      existenEdificios = false;
		      if (iterVender.hasNext()){  
		    	  TarjetaPropiedad tp = iterVender.next();
		    	  if (tp.getCasillero().getTipoCasillero()==TipoCasillero.C_CALLE){
		    		  CasilleroCalle cc = (CasilleroCalle) tp.getCasillero();
		    		  
		    	  }
		    	  
		      }
		      
		      
		      
		      if ( iterVender != vender.end() )
			if ( calle *calleV = dynamic_cast <calle*> (*iterVender) )
			  if ( calleV->puedeVenderEdificio() && ( calleV->get_numHoteles() > 0 || calleV->get_numCasas() > 0 ) )
			    {		    
			      if ( calleV->get_numHoteles() == 1 && tablero::obtenerCasasDisponibles () >= 4 )
				{
				  cout << get_nombre() << " vende un hotel de " << calleV->get_nombre() << endl;
				  calleV->venderSinComprobar();
				  existenEdificios = true;
				}
			      else if ( calleV->get_numCasas() > 0 )
				{
				  cout << get_nombre() << " vende una casa de " << calleV->get_nombre() << endl;
				  existenEdificios = true;
				  calleV->venderSinComprobar();
				}
			    }
		      iterVender ++; 	 
		    }
		}
	     i++;
	   }  
	 return;
	}

	/**
	 * Método que hipoteca una serie de propiedades del jugador con
	 * comportamiento aleatorio. Hipotecará todas las propiedades desde la más
	 * cara hasta la más barata hasta que cubra la deuda.
	 */
	private static void hipotecarAleatorio ( int cantidad )
	{
	  //1- Copiar ordenadamente las propiedades hipotecables
	  
	  vector <propiedad*> propiedadesHipotecables;
	  list <propiedad*>::iterator p; 
	  for ( p = _posee.begin(); p != _posee.end(); p++ )
	    {
	      if ( (*p)->get_hipotecable() )
		//Insertar ordenado de mayor a menor hipoteca
		{
		  int h = 0; 
		  while ( h != propiedadesHipotecables.size () && propiedadesHipotecables[h]->get_hipoteca() < (*p)->get_hipoteca ()  )
		    {
		      h++;
		    }
		  propiedadesHipotecables.insert( propiedadesHipotecables.begin() + h, (*p) );
		}
	    }
	
	  //2- Hipotecar desde la más cara hasta alcanzar el dinero necesario.
	  int i = 0;
	  while ( cantidad > 0 && i != propiedadesHipotecables.size () )
	    {
	      cout << get_nombre() << " ha hipotecado " << propiedadesHipotecables[i]->get_nombre() << endl;
	      propiedadesHipotecables[i]->hipotecar();
	      cantidad -= propiedadesHipotecables[i]->get_hipoteca();
	      i++;
	    }
	}

	/**
	 * Método que busca los edificios vendibles y buscará la mejor alternativa
	 * para superar la cantidad minimizando las pérdidas, usando el método
	 * alternativasVenderImprimir. Una lista de edificios se representa como una
	 * lista de calles, cada aparición de una calle, representa un edificio.
	 */
	private static List<CasilleroCalle> calculoUtilidadEsperadaVender ( int cantidad )
	{ 
	  
	  //1- Copiar ordenadamente las propiedades hipotecables
	  
	  vector <calle*> propiedadesVendibles;
	  list <propiedad*>::iterator p; 
	  for ( p = _posee.begin(); p != _posee.end(); p++ )
	    {
	      if ( calle *c = dynamic_cast <calle*> (*p) )
		{	  
		  int numHoteles = c->get_numHoteles ();
		  int numCasas = c->get_numCasas ();
		  int edificios;
		  if ( numHoteles == 1 )
		    edificios = 5;
		  else 
		    edificios = numCasas;
		  if ( edificios > 0 )
		    //Insertar ordenado de mayor a menor hipoteca
		    {
		      int h = 0;
		      while ( h != propiedadesVendibles.size () && propiedadesVendibles[h]->get_precioCasa() > c->get_precioCasa ()  )
			{
			  h++;
			}
		      propiedadesVendibles.insert( propiedadesVendibles.begin() + h, edificios, c );
		    }
		}
	    }   
	   
	  if ( propiedadesVendibles.empty() )
	    return propiedadesVendibles;
	 
	  //2- Buscar la mejor alternativa
	  vector <calle*> mejor;
	  mejor.clear();
	  if ( sumaVenta ( propiedadesVendibles ) <= cantidad )
	    return propiedadesVendibles;
	  int i = 0; 
	  while (i != propiedadesVendibles.size() )
	    {     
	      vector <calle*> aux;      
	      aux = alternativasVender ( cantidad, propiedadesVendibles, i );
	      if ( sumaVenta ( aux ) >= cantidad )
		if ( controlFactibilidadVenta ( aux ) )
		  if ( utilidadEsperadaVender( propiedadesVendibles, aux ) < utilidadEsperadaVender ( propiedadesVendibles, mejor ) 
		       || mejor.empty() )
		    {
		      mejor.clear();
		      mejor = aux;	    
		    }
	      //Situa el puntero en la siguiente propiedad
	      int j = i;
	      while ( propiedadesVendibles[j] == propiedadesVendibles[i] && j != propiedadesVendibles.size() )
		j++;
	      if ( j == i )
		i++;
	      else
		i = j;
	    }  
	  
	  // Si no hay posibilidades posibles, se vende todo
	  if ( mejor.empty() )
	    mejor = propiedadesVendibles;
	  
	  return mejor;
	}

	/**
	 * Método que busca las propiedades hipotecables y buscará la mejor
	 * alternativa para superar la cantidad minimizando las pérdidas, usando el
	 * método alternativasHipotecarImprimir.
	 */
	private static List<Casillero> calculoUtilidadEsperadaHipotecar ( int cantidad )
	{ 
	  //1- Copiar ordenadamente las propiedades hipotecables
		List<Casillero> propiedadesHipotecables;
	  list <propiedad*>::iterator p; 
	  for ( p = _posee.begin(); p != _posee.end(); p++ )
	    {
	      if ( (*p)->get_hipotecable() )
		//Insertar ordenado de mayor a menor hipoteca
		{
		  int h = 0; 
		  while ( h != propiedadesHipotecables.size () && propiedadesHipotecables[h]->get_hipoteca() > (*p)->get_hipoteca ()  )
		    {
		      h++;
		    }
		  propiedadesHipotecables.insert( propiedadesHipotecables.begin() + h, (*p) );
		}
	    }
	  
	 
	   //2- Buscar la mejor alternativa
	  vector <propiedad*> mejor;
	  mejor.clear();
	  //Cada vez lo intentará desde una propiedad diferente en busca de la mejor.
	  for (int i = 0; i != propiedadesHipotecables.size(); i++)
	    {    
	      vector <propiedad*> aux;      
	      aux = alternativasHipoteca ( cantidad, propiedadesHipotecables, i );           
	      if ( sumaHipoteca ( aux ) >= cantidad )
		if ( utilidadEsperadaAlquiler( aux, 1 ) < utilidadEsperadaAlquiler ( mejor, 1 ) || mejor.empty() )
		  {
		    mejor.clear();
		    mejor = aux;	    
		  }
	    }  
	  return mejor;
	}
}
