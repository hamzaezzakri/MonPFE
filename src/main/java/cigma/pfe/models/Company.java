package cigma.pfe.models;

import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "Companies")
@Entity
@IdClass(CompanyId.class)
public class Company {

	@Id
	private long rc;
	@Id
	private long idTribunal;
	private String name;
	private String address;
	private String phone;
	@Embedded
	@AttributeOverride(name = "phone",column = @Column(name = "PHONE_PERSON"))
	private ContactPerson contactPerson;
}
