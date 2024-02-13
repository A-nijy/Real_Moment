package project1.shop.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Admin;
import project1.shop.dto.innerDto.SearchDto;

public interface AdminRepositoryCustom {

    Page<Admin> searchAdmins(SearchDto.Admins searchDto, Pageable pageable);
}
