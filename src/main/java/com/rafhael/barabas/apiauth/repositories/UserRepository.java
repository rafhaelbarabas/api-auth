package com.rafhael.barabas.apiauth.repositories;

import com.rafhael.barabas.apiauth.entities.Permission;
import com.rafhael.barabas.apiauth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username =:username")
    User findByUsername(@Param("username") String username);

}
