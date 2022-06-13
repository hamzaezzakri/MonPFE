package cigma.pfe.services;

import java.util.List;

import cigma.pfe.models.Facture;
import cigma.pfe.repositories.FactureRepository;

public class FactureServiceImpl implements FactureService {

	FactureRepository factureRepository;
	
	public FactureServiceImpl(FactureRepository factureRepository) {
		this.factureRepository = factureRepository;
	}

	public FactureServiceImpl() {
		
	}
	
	@Override
	public Facture save(Facture f) {
		return factureRepository.save(f);
	}

	@Override
	public Facture modify(Facture f) {
		return factureRepository.update(f);
	}

	@Override
	public void removeById(long id) {
		factureRepository.deleteById(id);
	}

	@Override
	public Facture getById(long id) {
		return factureRepository.findById(id);
	}

	@Override
	public List<Facture> getAll() {
		return factureRepository.findAll();
	}
}
