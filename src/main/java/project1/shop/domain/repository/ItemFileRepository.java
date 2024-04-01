package project1.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemFile;
import project1.shop.domain.repository.custom.ItemFileRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long>, ItemFileRepositoryCustom {

    List<ItemFile> findByItem(Item item);

    Optional<ItemFile> findByNumber(int number);

    Optional<ItemFile> findByItemAndS3File_S3FileId(Item item, Long s3FileId);

    List<ItemFile> findByNumberGreaterThanEqual(int number);
}
