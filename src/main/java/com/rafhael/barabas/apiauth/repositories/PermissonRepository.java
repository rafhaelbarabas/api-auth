package com.rafhael.barabas.apiauth.repositories;

import com.rafhael.barabas.apiauth.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissonRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p WHERE p.description =:description")
    Permission findByDescription(@Param("description") String description);

}
