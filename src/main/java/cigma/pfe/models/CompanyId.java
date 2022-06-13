package cigma.pfe.models;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode
@Data
public class CompanyId implements Serializable {

	private long rc;
	private long idTribunal;
}
