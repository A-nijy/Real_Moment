package project1.shop.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project1.shop.domain.entity.PageFile;
import project1.shop.domain.entity.QPageFile;
import project1.shop.domain.repository.custom.PageFileRepositoryCustom;
import project1.shop.enumeration.ImageLocation;

import java.util.List;

public class PageFileRepositoryImpl implements PageFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PageFileRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public List<PageFile> searchNumberMoveImgList(ImageLocation imageLocation, int number) {

        List<PageFile> pageFileList = queryFactory.selectFrom(QPageFile.pageFile)
                .where(QPageFile.pageFile.imageLocation.eq(imageLocation),
                        QPageFile.pageFile.number.goe(number))
                .fetch();


        return pageFileList;
    }
}
