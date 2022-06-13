package cigma.pfe.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode
@Embeddable
public class LigneFactureId implements Serializable{

	private long idProduit;
	private long idFacture;
}
