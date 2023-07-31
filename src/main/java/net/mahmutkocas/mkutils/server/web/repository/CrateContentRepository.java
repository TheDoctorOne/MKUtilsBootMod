package net.mahmutkocas.mkutils.server.web.repository;

import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrateContentRepository  extends JpaRepository<CrateContentDAO, Long> {
}
