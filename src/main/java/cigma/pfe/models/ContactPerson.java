package cigma.pfe.models;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ContactPerson {

	private String firstName;
	private String lastName;
	private String phone;
}
