package project1.shop.domain.repository.custom;

import project1.shop.domain.entity.PageFile;
import project1.shop.enumeration.ImageLocation;

import java.util.List;

public interface PageFileRepositoryCustom {

    List<PageFile> searchNumberMoveImgList(ImageLocation imageLocation, int number);
}
