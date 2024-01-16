package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
