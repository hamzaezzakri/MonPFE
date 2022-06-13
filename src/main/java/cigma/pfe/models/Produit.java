package cigma.pfe.models;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Table(name = "Produits")
@Entity
public class Produit{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String designation;
	private float prix;
	private Integer stock;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.idProduit")
	private List<LigneFacture> ligneFactures;
	
}
