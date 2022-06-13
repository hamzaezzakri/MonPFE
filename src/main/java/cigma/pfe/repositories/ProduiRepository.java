package cigma.pfe.repositories;

import cigma.pfe.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProduiRepository extends JpaRepository<Produit,Long> {
}
