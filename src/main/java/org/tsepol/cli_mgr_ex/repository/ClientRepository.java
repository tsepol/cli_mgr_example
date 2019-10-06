package org.tsepol.cli_mgr_ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tsepol.cli_mgr_ex.models.Client;

import java.util.Collection;
import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByNif(String Nif);

    @Query("FROM Client WHERE name LIKE CONCAT('%', ?1, '%') AND is_active = true")
    List<Client> findClientsContainsName(String name);

    List<Client> findByName(String name);
}
