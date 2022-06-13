package cigma.pfe.services;

import java.util.List;

import cigma.pfe.models.Facture;

public interface FactureService {

	Facture save(Facture f);
	Facture modify(Facture f);
	void removeById(long id);
	Facture getById(long id);
	List<Facture> getAll();
}
