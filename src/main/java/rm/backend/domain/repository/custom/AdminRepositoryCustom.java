package rm.backend.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Admin;
import rm.backend.dto.innerDto.SearchDto;

public interface AdminRepositoryCustom {

    Page<Admin> searchAdmins(SearchDto.Admins searchDto, Pageable pageable);
}
