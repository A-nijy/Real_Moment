package rm.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.ItemFile;
import rm.backend.domain.repository.custom.ItemFileRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long>, ItemFileRepositoryCustom {

    List<ItemFile> findByItem(Item item);

    Optional<ItemFile> findByItemAndS3File_S3FileId(Item item, Long s3FileId);

}
