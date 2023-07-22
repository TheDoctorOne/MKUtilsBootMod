package net.mahmutkocas.reservermod.web.repository;

import net.mahmutkocas.reservermod.web.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {
}
