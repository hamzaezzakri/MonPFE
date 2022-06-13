package cigma.pfe.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
@Table(name = "ClientsNormal")
@Entity
@PrimaryKeyJoinColumn(name="normal_id")
public class ClientNormal extends Client {

	private int importanceLevel;
	
	public ClientNormal(String name, int importanceLevel) {
	    super(name);
	    this.importanceLevel = importanceLevel;
	}
}
