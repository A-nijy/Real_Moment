package project1.shop.domain.repository.custom;

import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemFile;
import project1.shop.domain.entity.S3File;

import java.util.List;
import java.util.Optional;

public interface ItemFileRepositoryCustom {

    List<S3File> searchMainImgList(Item item);

    List<S3File> searchServeImgList(Item item);

    Optional<ItemFile> searchFirstMainImg(Item item);
}
