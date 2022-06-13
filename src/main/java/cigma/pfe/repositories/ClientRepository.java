package cigma.pfe.repositories;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cigma.pfe.models.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {

	List<Client> findByName(String name);
	@Query("SELECT c FROM Client c")
	List<Client> findAllClients(Sort sort);
	@Query("SELECT c FROM Client c WHERE c.name = ?1")
	Client findClientByNameIndexedParam(String name);
	@Query("SELECT c FROM Client c WHERE c.name = :name")
	Client findClientByNameNamedParam(@Param("name") String name);
	List<Client> findByNameLike(String name);
	void deleteByName(String name);
}
