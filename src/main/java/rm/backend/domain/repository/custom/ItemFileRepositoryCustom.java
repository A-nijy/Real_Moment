package rm.backend.domain.repository.custom;

import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.ItemFile;
import rm.backend.domain.entity.S3File;

import java.util.List;
import java.util.Optional;

public interface ItemFileRepositoryCustom {

    List<S3File> searchMainImgList(Item item);

    List<S3File> searchServeImgList(Item item);

    Optional<ItemFile> searchFirstMainImg(Item item);


    Optional<ItemFile> searchChangeImg(Item item, String mainOrSub, int number);

    List<ItemFile> searchNumberMoveImgList(Item item, String mainOrSub, int number);
}
