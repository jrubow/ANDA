package com.anda.rest.repository;

import com.anda.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for User repository
 * @author Gleb Bereziuk (gl3bert)
 */

public interface UserRepository extends JpaRepository<User, String> {

}
