///**
// * 
// */
//package monopoly.controller;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import monopoly.model.Estado.EstadoJugador;
//import monopoly.model.Banco;
//import monopoly.model.Juego;
//import monopoly.model.Jugador;
//import monopoly.model.JugadorVirtual;
//import monopoly.model.tablero.Casillero;
//import monopoly.model.tablero.Casillero.TipoCasillero;
//import monopoly.model.tablero.CasilleroCalle;
//import monopoly.model.tarjetas.TarjetaCalle;
//import monopoly.model.tarjetas.TarjetaPropiedad;
//import monopoly.util.GestorLogs;
//
///**
// * @author Bostico Alejandro
// * @author Moreno Pablo
// *
// */
//public class JugadorVirtualControllerOLD extends JugadorController {
//
//	/**
//	 * Método por el cual un jugador paga una cantidad. Si la cantidad es
//	 * positiva, el jugador pagará dinero, en caso de ser negativa, el jugador
//	 * estará cobrando dinero. Al pagar, se debe restar la cantidad del dinero
//	 * total y del capital. Si no tiene dinero suficiente se tratará de
//	 * conseguir automáticamente mediante hipotecas y ventas de edificios. Si
//	 * aun así no consigue el dinero suficiente se declara como moroso. Si es
//	 * moroso devuelve -1.
//	 */
//	public static int pagar(Casillero[] casillerosList, JugadorVirtual jugador,
//			int cantidad) {
//		// 1. Intento vender edificios si no tengo dinero suficiente
//		if (cantidad > jugador.getDinero()) {
//			int dineroNecesario = cantidad - jugador.getDinero();
//			switch (jugador.getTipoJugador()) {
//			// Jugador basado en reglas
//			case TJ_MAGNATE: {
//				List<CasilleroCalle> construidos;
//				// Busca la mejor opción
//				construidos = calculoUtilidadEsperadaVender(dineroNecesario);
//				if (!construidos.isEmpty()) {
//					// Vende lo que le indica el algoritmo anterior
//					for (int i = 0; i != construidos.size(); i++) {
//						/*
//						 * TODO: vender las casas y edificios cout <<
//						 * get_nombre() << " ha eliminado un edificio de " <<
//						 * construidos[i]->get_nombre() << endl;
//						 * construidos[i]->venderSinComprobar();
//						 */
//					}
//				}
//				break;
//			}
//			// Jugador con comportamiento aleatorio
//			case TJ_EMPRESARIO:
//			case TJ_COMPRADOR_PRIMERIZO:
//				venderAleatorio(jugador, dineroNecesario);
//				break;
//			}
//		}
//
//		// 2. Si aún no dispongo de dinero suficiente, intento hipotecar
//		// propiedades
//		if (cantidad > jugador.getDinero()) {
//			int dineroNecesario = cantidad - jugador.getDinero();
//			switch (jugador.getTipoJugador()) {
//			case TJ_MAGNATE: // Jugador inteligente
//			{
//				List<Casillero> hipotecas;
//				// Buscar la mejor opcion para hipotecar
//				hipotecas = calculoUtilidadEsperadaHipotecar(dineroNecesario);
//				if (!hipotecas.isEmpty()) {
//					// Hipotecar las propiedades elegidas por el algoritmo
//					// anterior
//					for (int i = 0; i != hipotecas.size(); i++) {
//						// TODO: Hipotecar las propiedades
//						// cout << get_nombre() << " ha hipotecado " <<
//						// hipotecas[i]->get_nombre() << endl;
//						// hipotecas[i]->hipotecar();
//					}
//				}
//				break;
//			}
//			case TJ_EMPRESARIO:
//			case TJ_COMPRADOR_PRIMERIZO: // Jugador aleatorio
//				hipotecarAleatorio(dineroNecesario);
//				break;
//			}
//		}
//
//		// PAGAR:
//		// Si tengo dinero suficiente, se paga (descontando de dinero total y
//		// capital). Aumentando si es cobrar
//		if (cantidad <= jugador.getDinero()) {
//			jugador.setDinero(jugador.getDinero() - cantidad); // TODO: OJO,
//																// verificar que
//																// no se haya
//																// descontado ya
//																// el dinero del
//																// jugador
//			// TODO set_dinero_total ( get_dinero_total() - cantidad );
//			// TODO set_capital ( get_capital() - cantidad );
//		}
//		// Declaro al jugador moroso
//		else {
//			// Bancarrota
//			jugador.setEstadoJugador(EstadoJugador.EJ_BANCARROTA);
//			return -1;
//		}
//		return jugador.getDinero();
//	}
//
//	/**
//	 * Método para vender casas del jugador aleatorio. Venderá todas las casas
//	 * desde la propiedad más cara hasta la más barata hasta superar la cantidad
//	 * necesaria. Así se asegura alcanzar el máximo dinero posible.
//	 */
//	// TODO: Revisar este método... no se si anda bien. fijarse en
//	// "CallesTesteadas" y "vender"
//	private static void venderAleatorio(JugadorVirtual jugador, int cantidad) {
//		// 1- Selecciono el monopolio a construir entre todos los posibles.
//		// Para ello busco todas las calles con permiso de construcción y las
//		// ordeno en una lista
//		// Creare otra para meter las calles que ya se han comprobado y no
//		// repetir sobre el mismo
//		List<CasilleroCalle> propiedadesEdificadas;
//
//		propiedadesEdificadas = new ArrayList<CasilleroCalle>();
//
//		for (Iterator<TarjetaPropiedad> iterator = jugador
//				.getTarjPropiedadList().iterator(); iterator.hasNext();) {
//			TarjetaPropiedad tarjeta = (TarjetaPropiedad) iterator.next();
//
//			if (tarjeta instanceof TarjetaCalle) {
//				CasilleroCalle casillero = (CasilleroCalle) tarjeta
//						.getCasillero();
//				if (casillero.getNroCasas() > 0) {
//					int h = 0;
//					while (h != propiedadesEdificadas.size()
//							&& ((TarjetaCalle) propiedadesEdificadas.get(h)
//									.getTarjetaCalle()).getPrecioCadaCasa() > ((TarjetaCalle) tarjeta)
//									.getPrecioCadaCasa()) {
//
//						h++;
//					}
//					propiedadesEdificadas.add(h, casillero);
//				}
//			}
//
//		}
//
//		// Selecciono el monopolio a vender -> El primero, si no hay dinero
//		// suficiente, el segundo...
//		int i = 0;
//		List<CasilleroCalle> callesTestadas = new ArrayList<CasilleroCalle>();
//		List<TarjetaPropiedad> vender = new ArrayList<TarjetaPropiedad>();
//		boolean posible = true;
//		// Mientras siga haciendo falta dinero y no haya llegado al final de la
//		// lista de calles edificadas.
//		while (cantidad > jugador.getDinero()
//				&& i != propiedadesEdificadas.size()) {
//			int j = 0;
//			while (j != callesTestadas.size() && posible) {
//				if (propiedadesEdificadas.get(i) == callesTestadas.get(j))
//					posible = false;
//				j++;
//			}
//			// Si se ha encontrado un monopolio vendible.
//			// Se marca para no volver a venderle y se venden sus casas hasta
//			// acabar con el dinero
//			if (posible) {
//				// TODO: vender =
//				// propiedadesEdificadas[i]->get_monopolioCompleto (
//				// propiedadesEdificadas[i] );
//
//				for (TarjetaPropiedad tarjetaPropiedad : vender) {
//					if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
//						callesTestadas.remove(((TarjetaCalle) tarjetaPropiedad)
//								.getCasillero());
//					}
//				}
//
//				boolean existenEdificios = true;
//				while (cantidad > jugador.getDinero() && existenEdificios) {
//					Iterator<TarjetaPropiedad> iterVender = vender.iterator();
//
//					// Vender de una calle
//					existenEdificios = false;
//					if (iterVender.hasNext()) {
//						TarjetaPropiedad tp = iterVender.next();
//						if (tp.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
//							CasilleroCalle cc = (CasilleroCalle) tp
//									.getCasillero();
//
//							JuegoController juegoController = PartidasController
//									.getInstance().buscarControladorJuego(
//											jugador.getJuego().getUniqueID());
//							TableroController tableroController = juegoController
//									.getGestorTablero();
//							Banco banco = juegoController.getGestorBanco()
//									.getBanco();
//
//							if (tableroController.puedeVenderEdificio(cc)) {
//								if (cc.getNroCasas() == 5
//										&& banco.getNroCasas() >= 4) {
//									tableroController.venderSinComprobar(cc,
//											jugador);
//									GestorLogs.registrarLog(jugador.getNombre()
//											+ " vende un hotel de "
//											+ cc.getNombreCalle());
//								} else if (cc.getNroCasas() > 0) {
//									tableroController.venderSinComprobar(cc,
//											jugador);
//									GestorLogs.registrarLog(jugador.getNombre()
//											+ " vende una casa de "
//											+ cc.getNombreCalle());
//								}
//
//							}
//
//						}
//
//					}
//				}
//			}
//		}
//	}
//	
//	
//	
//
//	/**
//	 * Método que hipoteca una serie de propiedades del jugador con
//	 * comportamiento aleatorio. Hipotecará todas las propiedades desde la más
//	 * cara hasta la más barata hasta que cubra la deuda.
//	 */
//	private static void hipotecarAleatorio ( int cantidad )
//	{
//	  //1- Copiar ordenadamente las propiedades hipotecables
//	  
//	  vector <propiedad*> propiedadesHipotecables;
//	  list <propiedad*>::iterator p; 
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {elado2
//	      if ( (*p)->get_hipotecable() )
//		//Insertar ordenado de mayor a menor hipoteca
//		{
//		  int h = 0; 
//		  while ( h != propiedadesHipotecables.size () && propiedadesHipotecables[h]->get_hipoteca() < (*p)->get_hipoteca ()  )
//		    {
//		      h++;
//		    }
//		  propiedadesHipotecables.insert( propiedadesHipotecables.begin() + h, (*p) );
//		}
//	    }
//	
//	  //2- Hipotecar desde la más cara hasta alcanzar el dinero necesario.
//	  int i = 0;
//	  while ( cantidad > 0 && i != propiedadesHipotecables.size () )
//	    {
//	      cout << get_nombre() << " ha hipotecado " << propiedadesHipotecables[i]->get_nombre() << endl;
//	      propiedadesHipotecables[i]->hipotecar();
//	      cantidad -= propiedadesHipotecables[i]->get_hipoteca();
//	      i++;
//	    }
//	}
//
//	
//	/**
//	 * Algoritmo recursivo que busca la mejor alternativa de para vender edificios de un jugador. 
//	 * Se debe conseguir una cantidad de dinero superior a la variable cantidad. 
//	 * Las calles de donde se venden se introducen en el vector L. 
//	 * Index es la posición a partir de la cual se comienza a buscar.
//	 */
//	private static List<CasilleroCalle> alternativasVender ( long cantidad, vector <calle*> L, int index )
//	{   
//	  vector <calle*> mejor;
//	  vector <calle*> aux;
//	  aux.clear();
//	  
//	  // Si llega al final de la lista, devuelve falso
//	  if ( index == L.size ()  )
//	    {
//	      aux.clear();
//	      return aux;
//	    }
//	  // Si cumplo con la cantidad con la casa actual, la devuelvo
//	  if ( cantidad <= ( L [index]->get_precioCasa() / 2 ) )
//	    {      
//	      aux.push_back ( L[ index ] );
//	      return aux;
//	    } 
//	  //Busca la mejor opción
//	  for ( int i = index; i != L.size(); i++ )
//	    {
//	      aux.clear();
//	      aux.push_back ( L[ index ] );
//	      vector <calle*> recursivo;     
//	      //Llamada recursiva
//	      recursivo = alternativasVender ( cantidad - ( L[ index ]->get_precioCasa() / 2 ), L, i + 1 );    
//	      unirCalles ( aux, recursivo );
//	      //Comrpueba que cumple con la cantidad necesaria
//	      if ( sumaVenta ( aux ) >= cantidad )
//		{	  
//		  //Pasa el control de factibilidad
//		  if ( controlFactibilidadVenta ( aux ) )
//		    //Comprueba si es el mejor
//		    if ( utilidadEsperadaVender ( L, aux ) < utilidadEsperadaVender ( L, mejor ) || mejor.empty() )
//		      {
//			mejor.clear();
//			mejor = aux;
//		      }
//		}
//	    }
//	  return mejor;
//	}
//	
//	/**
//	 * Algoritmo recursivo que busca la mejor alternativa de propiedades del jugador a hipotecar. Se debe conseguir
//	 * una cantidad de dinero superior a la variable cantidad. Las propiedades se introducen en el vector L. 
//	 * Index es la posición a partir de la cual se comienza a buscar.
//	 */
//	List<CasilleroCalle> alternativasHipoteca ( long cantidad, List<CasilleroCalle> L, int index )
//	{
//	  vector <propiedad*> mejor;
//	  vector <propiedad*> aux;
//	  aux.clear();
//	  // Si ha yegado al final de la lista devuelve falso
//	  if ( index == L.size ()  )
//	    {
//	      aux.clear();
//	      return aux;
//	    }
//	  // Si completa la cantidad necesaria, devuelve el elemento actual
//	  if ( cantidad <= L [index]->get_hipoteca() )
//	    {      
//	      aux.push_back ( L[ index ] );
//	      return aux;
//	    } 
//	  //Recorre todas las posibilidades buscando la mejor convinacion
//	  for ( int i = index; i != L.size(); i++ )
//	    {
//	      aux.clear();
//	      aux.push_back ( L[ index ] );
//	      vector <propiedad*> recursivo;     
//	      //Llamada recursiva
//	      recursivo = alternativasHipoteca ( cantidad - L[ index ]->get_hipoteca(), L, i + 1 );    
//	      unir ( aux, recursivo );
//	      //Control de factibilidad
//	      if ( sumaHipoteca ( aux ) >= cantidad )
//		{
//		  //Elije el mejor
//		  if ( utilidadEsperadaAlquiler( aux, 1 ) < utilidadEsperadaAlquiler ( mejor, 1 ) || mejor.empty() )
//		    {
//		      mejor.clear();
//		      mejor = aux;
//		    }
//		}
//	    }
//	  return mejor;
//	}
//
//	
//	/**
//	 * Método que busca los edificios vendibles y buscará la mejor alternativa
//	 * para superar la cantidad minimizando las pérdidas, usando el método
//	 * alternativasVenderImprimir. Una lista de edificios se representa como una
//	 * lista de calles, cada aparición de una calle, representa un edificio.
//	 */
//	private static List<CasilleroCalle> calculoUtilidadEsperadaVender ( int cantidad )
//	{ 
//	  
//	  //1- Copiar ordenadamente las propiedades hipotecables
//	  
//	  vector <calle*> propiedadesVendibles;
//	  list <propiedad*>::iterator p; 
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {
//	      if ( calle *c = dynamic_cast <calle*> (*p) )
//		{	  
//		  int numHoteles = c->get_numHoteles ();
//		  int numCasas = c->get_numCasas ();
//		  int edificios;
//		  if ( numHoteles == 1 )
//		    edificios = 5;
//		  else 
//		    edificios = numCasas;
//		  if ( edificios > 0 )
//		    //Insertar ordenado de mayor a menor hipoteca
//		    {
//		      int h = 0;
//		      while ( h != propiedadesVendibles.size () && propiedadesVendibles[h]->get_precioCasa() > c->get_precioCasa ()  )
//			{
//			  h++;
//			}
//		      propiedadesVendibles.insert( propiedadesVendibles.begin() + h, edificios, c );
//		    }
//		}
//	    }   
//	   
//	  if ( propiedadesVendibles.empty() )
//	    return propiedadesVendibles;
//	 
//	  //2- Buscar la mejor alternativa
//	  vector <calle*> mejor;
//	  mejor.clear();
//	  if ( sumaVenta ( propiedadesVendibles ) <= cantidad )
//	    return propiedadesVendibles;
//	  int i = 0; 
//	  while (i != propiedadesVendibles.size() )
//	    {     
//	      vector <calle*> aux;      
//	      aux = alternativasVender ( cantidad, propiedadesVendibles, i );
//	      if ( sumaVenta ( aux ) >= cantidad )
//		if ( controlFactibilidadVenta ( aux ) )
//		  if ( utilidadEsperadaVender( propiedadesVendibles, aux ) < utilidadEsperadaVender ( propiedadesVendibles, mejor ) 
//		       || mejor.empty() )
//		    {
//		      mejor.clear();
//		      mejor = aux;	    
//		    }
//	      //Situa el puntero en la siguiente propiedad
//	      int j = i;
//	      while ( propiedadesVendibles[j] == propiedadesVendibles[i] && j != propiedadesVendibles.size() )
//		j++;
//	      if ( j == i )
//		i++;
//	      else
//		i = j;
//	    }  
//	  
//	  // Si no hay posibilidades posibles, se vende todo
//	  if ( mejor.empty() )
//	    mejor = propiedadesVendibles;
//	  
//	  return mejor;
//	}
//
//	/**
//	 * Método que busca las propiedades hipotecables y buscará la mejor
//	 * alternativa para superar la cantidad minimizando las pérdidas, usando el
//	 * método alternativasHipotecarImprimir.
//	 */
//	private static List<Casillero> calculoUtilidadEsperadaHipotecar ( int cantidad ){ 
//		//1- Copiar ordenadamente las propiedades hipotecables
//		List<Casillero> propiedadesHipotecables;
//		list <propiedad*>::iterator p; 
//		for ( p = _posee.begin(); p != _posee.end(); p++ ){
//		  	if ( (*p)->get_hipotecable() )
//			//Insertar ordenado de mayor a menor hipoteca
//			{
//			  int h = 0; 
//			  while ( h != propiedadesHipotecables.size () && propiedadesHipotecables[h]->get_hipoteca() > (*p)->get_hipoteca ()  ){
//				  h++;
//			  }
//			  propiedadesHipotecables.insert( propiedadesHipotecables.begin() + h, (*p) );
//			}
//	  }
//	  
//	 
//	   //2- Buscar la mejor alternativa
//	  vector <propiedad*> mejor;
//	  mejor.clear();
//	  //Cada vez lo intentará desde una propiedad diferente en busca de la mejor.
//	  for (int i = 0; i != propiedadesHipotecables.size(); i++)
//	    {    
//	      vector <propiedad*> aux;      
//	      aux = alternativasHipoteca ( cantidad, propiedadesHipotecables, i );           
//	      if ( sumaHipoteca ( aux ) >= cantidad )
//		if ( utilidadEsperadaAlquiler( aux, 1 ) < utilidadEsperadaAlquiler ( mejor, 1 ) || mejor.empty() )
//		  {
//		    mejor.clear();
//		    mejor = aux;	    
//		  }
//	    }  
//	  return mejor;
//	}
//	
//	
//	
//	
//	
//	
//	/**
//	 * Polimorfismo de la clase jugador.
//	 * El jugador decidirá al caer sobre una propiedad libre si quiere comprarla o no. La decisión
//	 * se tomará en función del tipo de agente de IA que se trate.
//	 */
//	boolean decidirComprar ( CasilleroCalle p )	{
//	  switch ( get_reglas () )
//	    {
//	    case 1: //Jugador IA basado en reglas
//	      //El caso de las estaciones
//	      if ( estacion *e = dynamic_cast <estacion*> ( p ) )
//		{
//		  long precio = e->get_precio();
//		  // No compra porque no tiene dinero
//		  if ( get_dinero_total () < precio )
//		    {
//		      //cout << "No compra porque no tiene dinero" << endl;
//		      return false;
//		    }
//		  // Compra porque ya posee varias estaciones y tiene una cantidad importante de dinero
//		  if ( ( poseeEstacion () >= 2 ) && ( ( precio * 3 ) < get_dinero_total () ) )
//		    {
//		      //cout << "Compra porque tiene dinero suficiente y ya posee varias estaciones" << endl;
//		      return true;
//		    }
//		  // Compra como inversión de futuro, quedan libres varias estaciones en el juego
//		  if ( tablero::estacionesLibres () >= 2 )
//		    {
//		      //cout << "Compra como inversión de futuro. Quedan libres varias estaciones" << endl;
//		      return true;
//		    }
//		  // Compra porque tiene mucho dinero
//		  if ( ( precio * 7 ) < get_dinero_total () )
//		    {
//		      //cout << "Compra porque tiene mucho dinero" << endl;
//		      return true;
//		    }
//		}
//	      if ( servicio *s = dynamic_cast <servicio*> ( p ) )
//		{
//		  long precio = s->get_precio();
//		  // No compra porque no tiene dinero
//		  if ( get_dinero_total () < precio )
//		    {
//		      //cout << "No compra porque no tiene dinero" << endl;	  
//		      return false;
//		    }
//		  // Compra porque tiene bastante dinero y ya posee alguna utilidad de servicios
//		  if ( ( poseeServicio () > 0 ) && ( ( precio * 2 ) < get_dinero_total () ) )
//		    {
//		      //cout << "Compra porque tiene dinero suficiente y ya posee alguna utilidad de servicios" << endl;	  
//		      return true;
//		    }
//		  // Compra como inversión de futuro proque no hay servicios ocupados
//		  if ( ( tablero::serviciosOcupados () == 0 ) && ( ( precio * 2 ) < get_dinero_total () ) )
//		    {
//		      //cout << "Compra como inversión de futuro. Quedan libres todas las casillas de servicios" << endl;
//		      return true;
//		    }
//		  // Compra porque tiene mucho dinero
//		  if ( ( precio * 7 ) < get_dinero_total () )
//		    {
//		      //cout << "Compra porque tiene mucho dinero" << endl;	  
//		      return true;
//		    }
//		}
//	      if ( calle *c = dynamic_cast <calle*> ( p ) )
//		{
//		  long precio = c->get_precio();
//		  // No compra porque no tiene dinero
//		  if ( get_dinero_total () < precio )
//		    {
//		      //cout << "No compra porque no tiene dinero" << endl;
//		      return false;
//		    }
//		  // Compra porque con ella completa un monopolio
//		  if ( c->ultimaPropiedadMonopolio ( this ) )
//		    {
//		      cout << "Compra porque completa un monopolio" << endl;
//		      return true;
//		    }
//		  //Compra porque esta el monopolio completo libre. Inversión de futuro
//		  if ( c->monopolioLibre () )
//		    {
//		      //cout << "Compra como inversión de futuro. Aun esta todo libre" << endl;
//		      return true;
//		    }
//		  //Compra porque es el único poseedor de propiedades de ese monopolio
//		  if ( c->unicoPoseedorMonopolio ( this ) )
//		    {
//		      //cout << "Compra porque todo el monopolio que está ocupado, lo está por él" << endl;
//		      return true;
//		    }
//		  //Compra porque tiene dinero suficiente aunque no pueda completar el monopolio
//		  if ( ! c->unicoPoseedorMonopolio ( this ) && c->poseeParteMonopolio ( this ) && ( ( precio * 3 ) < get_dinero_total () ) )
//		    {
//		      //cout << "Compra porque tiene dinero suficiente, aunque parte del monopolio no le pertence" << endl;
//		      return true;
//		    }
//		  //Compra porque es la ultima propiedad del monopolio de otro jugador, asi evita que cree el monopolio
//		  if ( c->ultimaPropiedadMonopolioOtroJugador ( this ) )
//		    {
//		      //cout << "Compra para evitar que otro jugador cree un monopolio completo" << endl;
//		      return true;
//		    }
//		  //Compra porque tiene mucho dinero
//		  if ( ( precio * 7 ) < get_dinero_total () )
//		    {
//		      cout << "Compra porque tiene mucho dinero" << endl;
//		      return true;
//		    }
//		}
//	      //cout << get_nombre () << " no quiere esta propiedad." << endl;
//	      return false;
//	      break;
//
//	    case 2: //Jugador Aleatorio
//	      if ( get_dinero_total() > p->get_precio() )
//		{
//		  // Sigue una función probabilistica y un valor aleatorio. Si el valor aleatorio es menor que el obtenido
//		  // en la función, se compra. Si no, no.
//		  int probabilidad = ( get_dinero_total() - p->get_precio () ) * 100 / get_dinero_total();
//		  long tiempo = unsigned(time(NULL));
//		  MTRand_int32 irand( tiempo ); // 32-bit int generator        
//		  int resultado = irand() % 100;      
//		  if ( resultado < probabilidad )
//		    return true;      
//		}
//	      return false;	
//	      break;
//	    }
//	  return false;
//	}
//
//	/**
//	 * Polimorfismo de la clase jugador.
//	 * El jugador decidirá si salir de la cárcel o no según estime que convenga o no pagar la multa.
//	 * Es necesario conocer la multa y se pasa el argumento PropiedadesLibres para que el jugador basado en
//	 * reglas pueda decidir.
//	 */
//	bool jugador_reglas::decidirSalirPagando ( int tpcPropiedadesLibres, long multa )
//	{
//
//	  switch ( get_reglas () )
//	    {
//	    case 1:
//	      //Si quedan menos del x % de propiedades libres para comprar, no se sale de la cárcel
//	      //pq es más seguro para no pagar. Además debe tener dinero suficiente para salir.
//	      //Se ha establecido una escala en función de las propiedades que queden libres.
//	      //También deberé tener dinero para poder comprar y que sea rentable moverse( >50000)
//	      if ( ( tpcPropiedadesLibres == 100 ) && get_dinero_total ()  > multa && ( get_dinero_total() > 50000 ) )
//		{
//		  cout << "Sale de la cárcel pagando porque es necesario comprar propiedades y están todas libres" << endl;
//		  return true;
//		} 
//	      if ( ( tpcPropiedadesLibres >= 50 ) && get_dinero_total () > ( multa * 3 ) && ( get_dinero_total() > 50000 ) )
//		{
//		  cout << "Sale de la cárcel pagando porque aun quedan algunas propiedad libres" << endl;
//		  return true;
//		}
//	      if ( ( tpcPropiedadesLibres >= 20 ) && ( get_dinero_total () * 5 ) > multa && ( get_dinero_total() > 50000 ) )
//		{
//		  cout << "Sale de la cárcel pagando porque aun quedan algunas propiedad libres" << endl;
//		  return true;
//		}
//	      cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//	      return false;
//	    case 2:
//	      if ( get_dinero_total() > multa )
//		{
//		  // Saldrá con una probabilidad dada por la formula que se aplica a contiinuacion
//		  // Si el valor aleatorio con M.Twister es menor que el numero sale.
//		  int probabilidad = 100;
//		  if ( tablero::totalPropiedades () != 0 )
//		    probabilidad = ( get_numPropiedades () ) * 100 / tablero::totalPropiedades ();
//		  long tiempo = unsigned(time(NULL));
//		  MTRand_int32 irand( tiempo ); // 32-bit int generator        
//		  int resultado = irand() % 100;
//		  if ( resultado < probabilidad )
//		    return true;  
//		  cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//		  return false;
//		}
//	      else
//		cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//		return false;
//	    }
//	  cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//	  return false;
//	}
//
//	/**
//	 * Polimorfismo de la clase jugador.
//	 * El jugador decidirá si sale de la cárcel según estime conveniente o no. Sólo es aplicable si tiene
//	 * tarjetas el jugador.
//	 */
//	bool jugador_reglas::decidirSalirTarjeta ( int tpcPropiedadesLibres )
//	{
//	  switch ( get_reglas() )
//	    {
//	    case 1: //Jugador basado en reglas
//	      //Sólo sale si quedan muchas casillas libres ( >20% )
//	      //Debo tener mas de 50000 para que sea rentable al poder comprar otras propiedades
//	      if ( ( tpcPropiedadesLibres >= 20 ) && ( get_dinero_total() > 50000 ) )
//		{
//		  cout << "Sale de la cárcel pagando porque aun quedan algunas propiedad libres" << endl;
//		  return true;
//		} 
//	      cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//	      return false;
//	    case 2: //Jugador aleatorio
//	      // Saldrá con una probabilidad dada por la formula que se aplica a contiinuacion
//	      // Si el valor aleatorio con M.Twister es menor que el numero sale.
//	      int probabilidad = 100;
//	      if ( tablero::totalPropiedades () != 0 )
//		probabilidad = ( get_numPropiedades () ) * 100 / tablero::totalPropiedades ();
//	      long tiempo = unsigned(time(NULL));
//	      MTRand_int32 irand( tiempo ); // 32-bit int generator        
//	      int resultado = irand() % 100;
//	      if ( resultado < probabilidad )
//		return true;
//	      cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//	      return false;
//	    }
//	  cout << "No quiere salir de la cárcel pagando porque no lo considera rentable." << endl;
//	  return false;
//	}
//
//	/**
//	 * Polimorfismo de la clase jugador.
//	 * El jugador inteligente decidirá que opción es más conveniente para cada momento en función 
//	 * de las reglas establecidas.
//	 */
//	int jugador_reglas::decidirAccionTarjeta ( int numAcciones, tarjeta *tjt )
//	{
//	  int salirCarcel = tjt->buscarAccionCarcel ();
//	  int cobrar = tjt->buscarCobrar ();
//	  // Si existe la opción de salir de la cárcel y tiene mucho dinero, la elige
//	  if ( salirCarcel >= 0 && get_dinero_total() > 50000 )
//	    {
//	      cout << "Elige la opción " << salirCarcel << " (tarjeta para salir de la cárcel)" << endl;
//	      return salirCarcel;
//	    }
//	  // Si hay una opción de cobrar, la elige
//	  if ( cobrar >= 0 )
//	    {
//	      cout << "Elije la opción " << cobrar << " (cobrar)" << endl;
//	      return cobrar;
//	    }
//	  cout << "Elije la primera opción." << endl;
//	  return 0;
//	}
//
//	/**
//	 * El jugador decidirá tomar la opción a pagar que menor gasto suponga
//	 */
//	char jugador_reglas::decidirImpuestoEspecial ( long cantidad, int tpc )
//	{
//	  if ( cantidad <= ( ( get_capital () * tpc ) / 100 ) )
//	    {
//	      cout << "C" << endl;
//	      return 'C';      
//	    }
//	  cout << "P" << endl;
//	  return 'P';
//	}
//
//	/**
//	 * Polimorfismo de la clase jugador.
//	 * El jugador decidirá la nueva cantidad a pujar, si lo considerá oportuno. El jugador basado en reglas
//	 * lo hará en función de su razonamiento, y el aleatorio por puro azar.
//	 */
//	long jugador_reglas::pujar( propiedad *p, long maxActual, jugador *ganador )
//	{
//	  
//	  switch ( get_reglas () )
//	    {
//	    case 1: //Jugador basado en reglas
//	      //Si no tiene dinero sale de la puja
//	      if ( maxActual > get_dinero_total () )
//		{
//		  cout << "Puja " << get_nombre() << ": salir " << endl;
//		  return -1;
//		}
//
//	      //Si se comienza la puja y tengo dinero pujo
//	      if ( maxActual == 0 && p->get_precio () < get_dinero_total () )
//		{
//		  long puja = ( ( p->get_precio() * 50 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//	      
//	      //Si es la ultima propiedad del monopolio y tengo dinero, pujo.
//	      if ( p->ultimaPropiedadMonopolio ( this ) && maxActual * 2 < get_dinero_total () )
//		{
//		  long puja = maxActual + ( (maxActual * 30 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//	      
//	      //Si es la última propiedad del monopolio de otro jugador, tengo dinero, y dicho jugador es el ganador, pujo!
//	      if ( p->ultimaPropiedadMonopolio ( ganador ) && maxActual * 2 < get_dinero_total () )
//		{	 
//		  long puja = maxActual + ( (maxActual * 10 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//
//	      // Si soy el unico poseedor del monopolio y tengo dinero, pujo
//	      if ( p->unicoPoseedorMonopolio ( this ) && maxActual * 2 < get_dinero_total () )
//		{
//		  long puja = maxActual + ( (maxActual * 20 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//	      
//	      //Si tengo mucho dinero pujo
//	      if ( maxActual < p->get_precio() && maxActual * 10 < get_dinero_total () )
//		{
//		  long puja = maxActual + ( (maxActual * 30 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//	      //Si tengo mucho dinero pujo
//	      if ( maxActual < p->get_precio() && maxActual * 3 < get_dinero_total () )
//		{
//		  long puja = maxActual + ( (maxActual * 10 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//
//	      // Si tengo mucho dinero pujo
//	      if ( maxActual > p->get_precio() && maxActual * 5 < get_dinero_total () )
//		{
//		  long puja = maxActual + ( (maxActual * 5 ) / 100 );
//		  cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		  return puja;      
//		}
//	      cout << "Puja " << get_nombre() << ": paso"  << endl;
//	      return 0;  
//	    case 2: //Jugador aleatorio
//	      // Si no hay puja
//	      if ( maxActual == 0 )
//		{
//		  //Si tengo dinero, pujo por encima del precio
//		  if ( get_dinero_total() > p->get_precio() + 1 )
//		    {
//		      long puja =  p->get_precio() + 1;
//		      cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		      return ( puja );
//		    }
//		  else
//		    {
//		      cout << "Puja " << get_nombre() << ": salir"  << endl;
//		      return -1;  
//		    }
//		}
//	      // Si ya hay puja
//	      else if ( maxActual + 1 < get_dinero_total () )
//		{
//		  //Decido según el azar si pujo o no, en funcion de la siguiente probabilidad
//		  int probabilidad = ( get_dinero_total() - maxActual ) * 100 / get_dinero_total();
//		  long tiempo = unsigned(time(NULL));
//		  //Obtengo un numero aleatorio
//		  MTRand_int32 irand( tiempo ); // 32-bit int generator        
//		  int resultado = irand() % 100;      
//		  //Si el numero es menor que la probabilidad, pujo
//		  if ( resultado < probabilidad )
//		    {
//		      long puja =  maxActual + 1;
//		      cout << "Puja " << get_nombre() << ":  " << puja << endl;
//		      return ( puja );
//		    }
//		  cout << "Puja " << get_nombre() << ": paso"  << endl;
//		  return 0;  	  
//		}
//	      else
//		{
//		  cout << "Puja " << get_nombre() << ": salir"  << endl;
//		  return -1;  
//		}
//	    }
//	  cout << "Puja " << get_nombre() << ": salir"  << endl;
//	  return -1;  
//
//	}
//	
//	/**
//	 * Llama a los metodos de deshipoteca en funcion del tipo de agente que se trate
//	 */
//	void jugador_reglas::razonarDeshipotecas ()
//	{
//	  switch ( get_reglas () )
//	    {
//	    case 1: //Jugador basado en reglas
//	      {
//		//Invierte el 70 % del dinero
//		long cantidad = get_dinero_total () * 70 / 100;
//		vector <propiedad*> deshipotecas;  
//		deshipotecas = calculoUtilidadEsperadaDeshipotecar ( cantidad );  
//		//Deshipoteca la mejor opcion
//		for ( int i = 0; i != deshipotecas.size(); i++ )
//		  {
//		    cout << get_nombre() << " ha deshipotecado " << deshipotecas[i]->get_nombre() << endl;
//		    deshipotecas[i]->deshipotecar();
//		  }
//		break;
//	      }
//	    case 2: //Jugador aleatorio
//	      deshipotecarAleatorio ();
//	      break;
//	    }
//	  return;
//	}
//
//	/**
//	 * Deshipoteca las cantidades de una forma aleatoria en función de unas probabilidades dadas
//	 */
//	void jugador_reglas::deshipotecarAleatorio ()
//	{
//	  //1- Copiar ordenadamente las propiedades hipotecables
//	  vector <propiedad*> propiedadesHipotecadas;
//	  list <propiedad*>::iterator p; 
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {
//	      if ( (*p)->get_hipotecado() )
//		//Insertar ordenado de mayor a menor hipoteca
//		{
//		  int h = 0;
//		  while ( h != propiedadesHipotecadas.size () && propiedadesHipotecadas[h]->get_deshipoteca() > (*p)->get_deshipoteca ()  )
//		    {
//		      h++;
//		    }
//		  propiedadesHipotecadas.insert ( propiedadesHipotecadas.begin() + h, (*p) );
//		}
//	    }
//	  
//	  //Para cada una de las propiedades hipotecas, se comprueba si se deben deshipotecar o no, segun una probabilidad
//	  for ( int i = 0; i != propiedadesHipotecadas.size (); i++ )
//	    {
//	      if ( get_dinero_total() > propiedadesHipotecadas[i]->get_deshipoteca() )
//		{
//		  //Calcula la probabilidad segun la funcion:
//		  int probabilidad = ( get_dinero_total() - propiedadesHipotecadas[i]->get_deshipoteca() ) * 100 / get_dinero_total();
//		  long tiempo = unsigned(time(NULL));
//		  MTRand_int32 irand( tiempo ); // 32-bit int generator	  
//		  int resultado = irand() % 100;
//		  //Si el resultado esta dentro de la probabilidad, deshipoteca
//		  if ( resultado < probabilidad )
//		    {
//		      propiedadesHipotecadas [i]->deshipotecar();
//		      cout << get_nombre() << " ha deshipotecado " << propiedadesHipotecadas[i]->get_nombre() << endl;
//		    }
//		}
//	    }
//
//	  return;
//	}
//
//	/**
//	 * Algoritmo recursivo que busca la mejor alternativa de deshipotecas de propiedades. La cantidad máxima que se 
//	 * puede invertir es la cantidad que se marca en la variable 'cantidad'. La lista contiene todas las propiedades
//	 * hipotecadas ordenadas por precio de deshipoteca.
//	 */
//	vector <propiedad*> jugador_reglas::alternativasDeshipotecar ( long cantidad, vector <propiedad*> L, int index )
//	{
//	  vector <propiedad*> mejor;
//	  mejor.clear ();
//	  vector <propiedad*> aux;
//	  aux.clear(); 
//
//	  // Si llega al final de la lista, devuelve falso
//	  if ( index == L.size ()  )
//	    {
//	      aux.clear();
//	      return aux;
//	    }
//	  
//	  //Si no puedo gastar lo suficiente para deshipotecar nada mas, he llegado al final, devuelvo
//	  if ( cantidad < L [ L.size() - 1 ]->get_deshipoteca() )
//	    {      
//	      aux.push_back ( L[ index ] );
//	      return aux;
//	    } 
//	 
//	  for ( int i = index; i != L.size(); i++ )
//	    {       
//	      aux.clear();
//	      aux.push_back ( L[ index ] );
//	      vector <propiedad*> recursivo;            
//	      recursivo = alternativasDeshipotecar ( cantidad - L[ index ]->get_deshipoteca(), L, i + 1 );
//	      unir ( aux, recursivo );
//	      //Control de factibilidad
//	      if ( sumaDeshipoteca ( aux ) < cantidad )
//		{	  	 
//		  // Elige el mejor
//		  if ( utilidadEsperadaAlquiler( aux, 1 ) > utilidadEsperadaAlquiler ( mejor, 1 ) )
//		    {
//		      mejor.clear();
//		      mejor = aux;
//		    }
//		}
//	    }
//	  return mejor;
//	}
//
//	/**
//	 * Método que busca las propiedades hipotecadas y buscará la mejor combinación para deshipotecarlas sin
//	 * superar la cantidad indicada en el atributo cantidad. Utilizará el método alternativasDeshipotecarImprimir.
//	 */
//	vector <propiedad*> jugador_reglas::calculoUtilidadEsperadaDeshipotecar ( long cantidad )
//	{
//	  
//	  //1- Copiar ordenadamente las propiedades hipotecables
//	  vector <propiedad*> propiedadesHipotecadas;
//	  list <propiedad*>::iterator p; 
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {
//	      if ( (*p)->get_hipotecado() )
//		//Insertar ordenado de mayor a menor hipoteca
//		{
//		  int h = 0;
//		  while ( h != propiedadesHipotecadas.size () && propiedadesHipotecadas[h]->get_deshipoteca() > (*p)->get_deshipoteca ()  )
//		    {
//		      h++;
//		    }
//		  propiedadesHipotecadas.insert ( propiedadesHipotecadas.begin() + h, (*p) );
//		}
//	    }    
//	        
//	  //2- Buscar la mejor alternativa
//	  vector <propiedad*> mejor;
//	  mejor.clear();
//	  
//	  for (int i = 0; i != propiedadesHipotecadas.size(); i++)
//	    {    
//	      vector <propiedad*> aux;           
//	      aux = alternativasDeshipotecar ( cantidad, propiedadesHipotecadas, i );
//	      if ( sumaHipoteca ( aux ) < cantidad )
//		if ( utilidadEsperadaAlquiler( aux, 1 ) > utilidadEsperadaAlquiler ( mejor, 1 ) )
//		  {	    
//		    mejor.clear();
//		    mejor = aux;	    
//		  }
//	    }
//	 
//	  return mejor;
//	}
//
//	/**
//	 * Llama a los metodos de construccion en funcion del tipo de agente que se trate
//	 */
//	void jugador_reglas::razonarConstrucciones ()
//	{   
//	  switch ( get_reglas () )
//	    {
//	    case 1: //Jugador basado en reglas
//	      {
//		//El jugador invierte en deshipotecas el 60 % del dinero
//		long cantidad = get_dinero_total () * 60 / 100;	
//		vector <calle*> construcciones;
//		//Si tengo dinero con un colchon de seguridad busco la mejor opcion y la hago efectiva
//		if ( get_dinero_total () > 50000 )
//		  {	    
//		    construcciones = calculoUtilidadEsperadaConstruir ( cantidad );  
//		    for ( int i = 0; i != construcciones.size(); i++ )
//		      {
//			cout << get_nombre() << " ha construido un nuevo edificio en " << construcciones[i]->get_nombre() << endl;
//			construcciones[i]->construirSinComprobar();
//		      }  
//		  }      
//		break;
//	      }
//	    case 2: //Jugador aleatorio
//	      construirAleatorio ();
//	      break;
//	    }
//	  return;
//	}
//
//	/**
//	 * Construye sobre las calles construibles de una forma aleatoria en función de unas probabilidades calculadas
//	 * a partir del dinero total y el precio de construir
//	 */
//	void jugador_reglas::construirAleatorio ()
//	{
//	  //1- Selecciono el monopolio a construir entre todos los posibles.
//	  // Para ello busco todas las calles con permiso de construcción y las ordeno en una lista
//	  // Creare otra para meter las calles que ya se han comprobado y no repetir sobre el mismo
//	  vector <calle*> propiedadesConstruibles;
//	  list <propiedad*>::iterator p; 
//
//	  //Todas las calles construibles ordenadas
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {
//	      if ( calle *c = dynamic_cast <calle*> (*p) )
//		{	  
//		  if ( c->get_numHoteles() == 0 && c->get_permisoConstruccion() )
//		    //Insertar ordenado de mayor a menor precio de casa
//		    {
//		      int h = 0;
//		      while ( h != propiedadesConstruibles.size () && propiedadesConstruibles[h]->get_precioCasa() > c->get_precioCasa ()  )
//			{
//			  h++;
//			}
//		      propiedadesConstruibles.insert( propiedadesConstruibles.begin() + h, c );
//		    }
//		}
//	    }   
//
//	  // Selecciono el monopolio a construir
//	  long tiempo = unsigned(time(NULL));    
//	  MTRand_int32 irand( tiempo ); // 32-bit int generator
//	  int i = 0;
//	  vector <calle*> callesTestadas;
//	  set <propiedad*> construir;
//	  while ( construir.empty() && i != propiedadesConstruibles.size () )
//	    {
//	      bool posible = true;
//	      int j = 0;
//	      while ( j != callesTestadas.size() && posible )
//		{
//		  if ( propiedadesConstruibles[i] == callesTestadas[j] )
//		    posible = false;
//		  j++;
//		}
//
//	      if ( posible )
//		{
//		  if ( get_dinero_total() >= propiedadesConstruibles[i]->get_precioCasa() )
//		    {
//		      int probabilidad = ( get_dinero_total() - propiedadesConstruibles[i]->get_precioCasa() ) * 100 / get_dinero_total();
//		      int resultado = irand() % 100;
//		      //cout << "probabilidad" << probabilidad << endl << "resultado" << resultado << endl;
//		      if ( resultado < probabilidad )	
//			construir = propiedadesConstruibles[i]->get_monopolioCompleto ( propiedadesConstruibles[i] );	
//		      else
//			{
//			  set <propiedad*> testadas = propiedadesConstruibles[i]->get_monopolioCompleto ( propiedadesConstruibles[i] );
//			  set <propiedad*>::iterator test;
//			  for ( test = testadas.begin(); test != testadas.end(); test ++ )
//			    if ( calle *c = dynamic_cast <calle*> (*test) )
//			      callesTestadas.push_back( c );
//			}
//		    }
//		}
//	      i++;
//	    }  
//
//	  //2- Construir sobre el monopolio seleccionado (variable construir)
//	  // Se intentará construir hasta que el azar diga que se pare y sea posible construir algo.
//	  bool seguirConstruyendo = true;
//	  set <propiedad*>::iterator itConstruir;
//	  int posiblesConstrucciones = 1;
//	  while ( seguirConstruyendo && posiblesConstrucciones > 0 )
//	    {
//	      for ( itConstruir = construir.begin(); itConstruir != construir.end(); itConstruir ++ )
//		{	  
//		  if ( calle *c = dynamic_cast <calle*> (*itConstruir) )
//		    if ( seguirConstruyendo && c->esConstruible() && c->get_numHoteles() == 0 )
//		      {
//			int probabilidad = ( get_dinero_total() - c->get_precioCasa() ) * 100 / get_dinero_total();
//			int resultado = irand() % 100;
//			//cout << "probabilidad" << probabilidad << endl << "resultado" << resultado << endl;
//			if ( resultado < probabilidad )
//			  {
//			    cout << get_nombre() << " ha construido un nuevo edificio en " << c->get_nombre() << endl;
//			    int ok = c->construirSinComprobar();
//			  }
//			else 
//			  seguirConstruyendo = false;
//		      }
//		}
//	      
//	      posiblesConstrucciones = 0;
//	      for ( itConstruir = construir.begin(); itConstruir != construir.end(); itConstruir ++ )
//		if ( calle *c = dynamic_cast <calle*> (*itConstruir) )
//		  if ( c->esConstruible() && c->get_numHoteles() == 0 )
//		    posiblesConstrucciones++;         
//	    }
//	  return;
//	}
//
//	/**
//	 * Método que busca todas las alternativas para construir de una lista de calles y devuelve la mejor alternativa.
//	 * Un edificio se representa en la lista por la calle en la que se construirá.
//	 */
//	vector <calle*> jugador_reglas::alternativasConstruir ( long cantidad, vector <calle*> L, int index )
//	{
//	  vector <calle*> mejor;
//	  mejor.clear ();
//	  vector <calle*> aux;
//	  aux.clear(); 
//
//	  if ( index == L.size ()  )
//	    {
//	      aux.clear();
//	      return aux;
//	    }
//	  
//	  if ( cantidad < L [ L.size() - 1 ]->get_precioCasa () )
//	    {      
//	      aux.push_back ( L[ index ] );
//	      return aux;
//	    } 
//	 
//	  for ( int i = index; i != L.size(); i++ )
//	    {
//	      aux.clear();
//	      aux.push_back ( L[ index ] );
//	      vector <calle*> recursivo;
//	      recursivo = alternativasConstruir ( cantidad - L[ index ]->get_precioCasa(), L, i + 1 );
//	      unirCalles ( aux, recursivo );
//	      if ( controlFactibilidadConstruir ( aux ) )     
//		if ( sumaConstruccion ( aux ) < cantidad )	      	    
//		  if ( utilidadEsperadaConstruir ( L, aux ) > utilidadEsperadaConstruir ( L, mejor ) )
//		    {	
//		      mejor.clear();
//		      mejor = aux;
//		    }
//		  
//	    }
//	  return mejor;
//	}
//
//	/**
//	 * Método que usando el metodo alternativasConstruirImprimir busca la mejor alternativa para construir sin 
//	 * sobrepasar la cantidad indicada. Iterativamente buscará la mejor opción indicando como inicio cada vez
//	 * un nodo de la lista.
//	 * La lista de calles debe estar ordenada de mayor a menor precio de edificio.
//	 */
//	vector <calle*> jugador_reglas::calculoUtilidadEsperadaConstruir ( long cantidad )
//	{
//	  //1- Copiar ordenadamente las propiedades hipotecables
//
//	  vector <calle*> propiedadesConstruibles;
//	  list <propiedad*>::iterator p; 
//	  for ( p = _posee.begin(); p != _posee.end(); p++ )
//	    {
//	      if ( calle *c = dynamic_cast <calle*> (*p) )
//		{	  
//		  int numHoteles = c->get_numHoteles ();
//		  int numCasas = c->get_numCasas ();
//		  int edificios;
//		  if ( numHoteles == 1 )
//		    edificios = 0;
//		  else 
//		    edificios = 5 - numCasas;
//		  if ( edificios > 0 && c->get_permisoConstruccion() )
//		    //Insertar ordenado de mayor a menor hipoteca
//		    {
//		      int h = 0;
//		      while ( h != propiedadesConstruibles.size () && propiedadesConstruibles[h]->get_precioCasa() > c->get_precioCasa ()  )
//			{
//			  h++;
//			}
//		      propiedadesConstruibles.insert( propiedadesConstruibles.begin() + h, edificios, c );
//		    }
//		}
//	    }   
//	 
//	  //2- Buscar la mejor alternativa
//	  vector <calle*> mejor;
//	  mejor.clear();
//	  int i = 0;
//	  while (i != propiedadesConstruibles.size() )
//	    {    
//	      vector <calle*> aux;            
//	      aux = alternativasConstruir ( cantidad, propiedadesConstruibles, i );    
//	      //aux = alternativasConstruirImprimir ( cantidad, propiedadesConstruibles, i,true );    
//	      if ( sumaConstruccion ( aux ) < cantidad )
//		if ( controlFactibilidadConstruir ( aux ) )     
//		  {	   
//		    if (utilidadEsperadaConstruir( propiedadesConstruibles, aux ) > utilidadEsperadaConstruir ( propiedadesConstruibles, mejor ) )
//		      {	
//			mejor.clear();
//			mejor = aux;	    
//		      }
//		  }
//	      int j = i;
//	      while ( propiedadesConstruibles[j] == propiedadesConstruibles[i] && j != propiedadesConstruibles.size() )
//		j++;
//	      if ( j == i )
//		i++;
//	      else
//		i = j;
//	    }  
//	  return mejor;
//	}
//	
//	
//	
//}
