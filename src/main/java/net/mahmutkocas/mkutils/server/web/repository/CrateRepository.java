package net.mahmutkocas.mkutils.server.web.repository;

import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrateRepository extends JpaRepository<CrateDAO, Long> {
    CrateDAO findByName(String name);
}
