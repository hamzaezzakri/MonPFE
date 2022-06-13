package cigma.pfe.controllers;

import java.util.List;

import cigma.pfe.models.Facture;
import cigma.pfe.services.FactureService;

public class FactureController {

	FactureService factureService;
	
	public FactureController(FactureService factureService) {
		this.factureService = factureService;
	}

	public FactureController() {
		
	}

	public void setFactureService(FactureService factureService) {
		this.factureService = factureService;
	}

	public void save(Facture f){
		factureService.save(f);
	}
	
	public void modify(Facture f){
		factureService.modify(f);
	}
	
	public void removeById(long id){
		factureService.removeById(id);
	}
	
	public Facture getById(long id){
		return factureService.getById(id);
	}
	
	public List<Facture> getAll(){
		return factureService.getAll();
	}
}
