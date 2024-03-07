package project1.shop.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.QItemFile;
import project1.shop.domain.entity.S3File;
import project1.shop.domain.repository.custom.ItemFileRepositoryCustom;

import java.util.List;

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
                        QItemFile.itemFile.mainOrServe.eq("main"))
                .fetch();

        return mainImgList;
    }



    @Override
    public List<S3File> searchServeImgList(Item item) {

        List<S3File> serveImgList = queryFactory.select(QItemFile.itemFile.s3File)
                .from(QItemFile.itemFile)
                .where(QItemFile.itemFile.item.eq(item),
                        QItemFile.itemFile.mainOrServe.eq("serve"))
                .fetch();

        return serveImgList;
    }
}
