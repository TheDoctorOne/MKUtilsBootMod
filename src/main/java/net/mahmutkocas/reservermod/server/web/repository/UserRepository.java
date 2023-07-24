package net.mahmutkocas.reservermod.server.web.repository;

import net.mahmutkocas.reservermod.server.web.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {
    Optional<UserDAO> findByUsername(String username);
}
