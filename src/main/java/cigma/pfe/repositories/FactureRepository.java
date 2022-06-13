package cigma.pfe.repositories;

import java.util.List;

import cigma.pfe.models.Facture;

public interface FactureRepository {

	Facture save(Facture f);
	Facture update(Facture f);
	void deleteById(long idFacture);
	Facture findById(long idFacture);
	List<Facture> findAll();
}
