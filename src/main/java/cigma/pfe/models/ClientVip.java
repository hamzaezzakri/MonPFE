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
@Table(name = "ClientsVip")
@Entity
@PrimaryKeyJoinColumn(name="vip_id")
public class ClientVip extends Client {

	private String preferences;
	
	public ClientVip(String name, String preferences) {
	    super(name);
	    this.preferences = preferences;
	}
}
