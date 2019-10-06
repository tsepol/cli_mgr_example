package org.tsepol.cli_mgr_ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tsepol.cli_mgr_ex.models.Client;

import java.util.Collection;
import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("FROM Client WHERE nif = ?1 AND is_active = true")
    Client findByNif(String Nif);

    @Query("FROM Client WHERE name LIKE CONCAT('%', ?1, '%') AND is_active = true")
    List<Client> findClientsContainsName(String name);

    @Query("FROM Client WHERE name = ?1 AND is_active = true")
    List<Client> findByName(String name);

    @Override
    @Query("UPDATE Client cli set cli.is_active=false where cli.id = ?1")
    @Transactional
    @Modifying
    void deleteById(String id);

    @Override
    @Transactional
    default void delete(Client entity) {
        deleteById(entity.getNif());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends Client> entities) {
        entities.forEach(entity -> deleteById(entity.getNif()));
    }

    @Override
    @Query("UPDATE Client cli set cli.is_active=false")
    @Transactional
    @Modifying
    void deleteAll();
}
