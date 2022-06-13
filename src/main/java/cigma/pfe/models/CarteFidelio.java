package cigma.pfe.models;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "CartesFidelio")
@Entity
public class CarteFidelio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String code;
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;

}
