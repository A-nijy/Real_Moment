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

    // 특정 상품의 메인 또는 서브 이미지 중에서 몇 번째 이미지 조회
    // Optional<ItemFile> findByItemAndMainsubAndNumber(Item item, String mainsub, int number);

    Optional<ItemFile> findByItemAndS3File_S3FileId(Item item, Long s3FileId);

    List<ItemFile> findByNumberGreaterThanEqual(int number);

    // 특정 상품의 메인 또는 서브 이미지 중에서 몇 번째 부터 리스트 조회
    //List<ItemFile> findByItemAndMainsubAndNumberGreaterThanEqual(Item item, String mainsub, int number);

}
