/**
 * 
 */
package monopoly.model.tarjetas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import monopoly.model.Jugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
@Entity
@Table(name = "tarjeta_propiedad", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TarjetaPropiedad {
    
    @OneToOne
    @JoinColumn(name = "jugadorID")
    private Jugador jugador;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tarjetaPropiedadID")
    private Integer idTarjeta;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "valorHipoticario")
    private Integer valorHipotecario;
    
    public TarjetaPropiedad(){
	
    }
    
}
