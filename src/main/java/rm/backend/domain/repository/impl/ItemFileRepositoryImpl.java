package rm.backend.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import rm.backend.domain.entity.Item;
import rm.backend.domain.entity.ItemFile;
import rm.backend.domain.entity.QItemFile;
import rm.backend.domain.entity.S3File;
import rm.backend.domain.repository.custom.ItemFileRepositoryCustom;

import java.util.List;
import java.util.Optional;

public class ItemFileRepositoryImpl implements ItemFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemFileRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }



    @Override
    public List<S3File> searchMainImgList(Item item) {

        List<S3File> mainImgList = queryFactory.select(QItemFile.itemFile.s3File)
                .from(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrSub.eq("main"))
                .orderBy(QItemFile.itemFile.number.asc())
                .fetch();

        return mainImgList;
    }



    @Override
    public List<S3File> searchServeImgList(Item item) {

        List<S3File> serveImgList = queryFactory.select(QItemFile.itemFile.s3File)
                .from(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrSub.eq("sub"))
                .orderBy(QItemFile.itemFile.number.asc())
                .fetch();

        return serveImgList;
    }

    @Override
    public Optional<ItemFile> searchFirstMainImg(Item item) {

        ItemFile firstMainImg = queryFactory.selectFrom(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrSub.eq("main"),
                        QItemFile.itemFile.number.eq(0))
                .fetchOne();

        return Optional.ofNullable(firstMainImg);
    }

    @Override
    public Optional<ItemFile> searchChangeImg(Item item, String mainOrSub, int number) {

        ItemFile changeImg = queryFactory.selectFrom(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrSub.eq(mainOrSub),
                        QItemFile.itemFile.number.eq(number))
                .fetchOne();

        return  Optional.ofNullable(changeImg);
    }

    @Override
    public List<ItemFile> searchNumberMoveImgList(Item item, String mainOrSub, int number) {

        List<ItemFile> moveImgList = queryFactory.selectFrom(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrSub.eq(mainOrSub),
                        QItemFile.itemFile.number.goe(number))
                .fetch();

        return  moveImgList;
    }
}
