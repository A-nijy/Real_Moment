package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Admin;
import project1.shop.domain.repository.custom.AdminRepositoryCustom;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {

    Optional<Admin> findByLoginId(String loginId);
}
