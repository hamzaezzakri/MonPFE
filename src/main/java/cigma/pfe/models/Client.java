package cigma.pfe.models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Clients")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	private long id;
	@NotBlank(message = "the name is mendatory")
	@Column(unique = true)
	private String name;

	@OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "client")
	private List<Facture> factures;
	
	@ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinTable(name="my_join_table_client_promotion",joinColumns = @JoinColumn(
			name = "client_fk",
			referencedColumnName = "id"
			),
			inverseJoinColumns = @JoinColumn(
			name = "promotion_fk",
			referencedColumnName = "id"
			))
	private List<Promotion> promotions;

	@OneToOne(cascade = {CascadeType.PERSIST},mappedBy = "client")
	private CarteFidelio carteFidelio;
	
	@OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "client")
	private List<Adresse> adresses;
	
	public Client(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return "Client [id=" + id + ", name=" + name + "]";
	}
	
	
}
