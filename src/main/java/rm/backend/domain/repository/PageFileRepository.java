package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.PageFile;
import rm.backend.domain.repository.custom.PageFileRepositoryCustom;
import rm.backend.enumeration.ImageLocation;

import java.util.List;

public interface PageFileRepository extends JpaRepository<PageFile, Long>, PageFileRepositoryCustom {

    List<PageFile> findByImageLocationOrderByNumberAsc(ImageLocation imageLocation);

    List<PageFile> findByImageLocationAndIsShowOrderByNumberAsc(ImageLocation imageLocation, boolean isShow);
}
