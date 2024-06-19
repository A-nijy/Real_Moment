package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.PageFile;
import project1.shop.domain.repository.custom.PageFileRepositoryCustom;
import project1.shop.enumeration.ImageLocation;

import java.util.List;

public interface PageFileRepository extends JpaRepository<PageFile, Long>, PageFileRepositoryCustom {

    List<PageFile> findByImageLocationOrderByNumberAsc(ImageLocation imageLocation);

    List<PageFile> findByImageLocationAndIsShowOrderByNumberAsc(ImageLocation imageLocation, boolean isShow);
}
