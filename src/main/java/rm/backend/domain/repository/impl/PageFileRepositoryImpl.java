package rm.backend.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import rm.backend.domain.entity.PageFile;
import rm.backend.domain.entity.QPageFile;
import rm.backend.domain.repository.custom.PageFileRepositoryCustom;
import rm.backend.enumeration.ImageLocation;

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
