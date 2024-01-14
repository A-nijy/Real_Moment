package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
