package rm.backend.domain.repository.custom;

import rm.backend.domain.entity.PageFile;
import rm.backend.enumeration.ImageLocation;

import java.util.List;

public interface PageFileRepositoryCustom {

    List<PageFile> searchNumberMoveImgList(ImageLocation imageLocation, int number);
}
