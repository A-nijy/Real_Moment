package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Admin;
import rm.backend.domain.repository.custom.AdminRepositoryCustom;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {

    Optional<Admin> findByLoginId(String loginId);
}
