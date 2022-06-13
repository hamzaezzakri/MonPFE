package cigma.pfe.models;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Factures")
@Entity
public class Facture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double amount;
	private String description;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.idFacture")
	private List<LigneFacture> ligneFacture;

}
