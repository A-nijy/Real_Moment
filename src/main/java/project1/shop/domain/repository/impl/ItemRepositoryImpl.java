package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.QCategory;
import project1.shop.domain.entity.QItem;
import project1.shop.domain.repository.custom.ItemRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Item> searchPageSimple(SearchDto.Items searchDto,  Pageable pageable) {

        System.out.println("실행중");
        List<Item> items;

        if (searchDto.getItemSort().equals("new")){

            items = queryFactory.selectFrom(QItem.item)
                                            .join(QItem.item.category, QCategory.category)
                                            .where(itemNameContains(searchDto.getItemName()),
                                                    categoryIdEq(searchDto.getCategoryId()),
                                                    itemDeleteCheck(searchDto.getIsDelete()),
                                                    itemSellCheck(searchDto.getIsSell()))
                                            .orderBy(QItem.item.createdDate.desc().nullsLast())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        } else if (searchDto.getItemSort().equals("sell")){

            items = queryFactory.selectFrom(QItem.item)
                                            .join(QItem.item.category, QCategory.category)
                                            .where(itemNameContains(searchDto.getItemName()),
                                                    categoryIdEq(searchDto.getCategoryId()),
                                                    itemDeleteCheck(searchDto.getIsDelete()),
                                                    itemSellCheck(searchDto.getIsSell()))
                                            .orderBy(QItem.item.sellCount.desc(), QItem.item.createdDate.desc().nullsLast())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        } else if (searchDto.getItemSort().equals("low")){

            items = queryFactory.selectFrom(QItem.item)
                    .join(QItem.item.category, QCategory.category)
                    .where(itemNameContains(searchDto.getItemName()),
                            categoryIdEq(searchDto.getCategoryId()),
                            itemDeleteCheck(searchDto.getIsDelete()),
                            itemSellCheck(searchDto.getIsSell()))
                    .orderBy(QItem.item.sellPrice.asc(), QItem.item.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else if (searchDto.getItemSort().equals("high")){

            items = queryFactory.selectFrom(QItem.item)
                    .join(QItem.item.category, QCategory.category)
                    .where(itemNameContains(searchDto.getItemName()),
                            categoryIdEq(searchDto.getCategoryId()),
                            itemDeleteCheck(searchDto.getIsDelete()),
                            itemSellCheck(searchDto.getIsSell()))
                    .orderBy(QItem.item.sellPrice.desc(), QItem.item.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else if (searchDto.getItemSort().equals("sale")){

            items = queryFactory.selectFrom(QItem.item)
                    .join(QItem.item.category, QCategory.category)
                    .where(itemNameContains(searchDto.getItemName()),
                            categoryIdEq(searchDto.getCategoryId()),
                            itemDeleteCheck(searchDto.getIsDelete()),
                            itemSellCheck(searchDto.getIsSell()))
                    .orderBy(QItem.item.discountRate.desc(), QItem.item.createdDate.desc().nullsLast())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

        } else {

            items = queryFactory.selectFrom(QItem.item)
                                            .join(QItem.item.category, QCategory.category)
                                            .where(itemNameContains(searchDto.getItemName()),
                                                    categoryIdEq(searchDto.getCategoryId()),
                                                    itemDeleteCheck(searchDto.getIsDelete()),
                                                    itemSellCheck(searchDto.getIsSell()))
                                            .orderBy(QItem.item.createdDate.desc().nullsLast())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        }

        Long total = queryFactory
                .select(QItem.item.count())
                .from(QItem.item)
                .join(QItem.item.category, QCategory.category)
                .where(itemNameContains(searchDto.getItemName()),
                        categoryIdEq(searchDto.getCategoryId()),
                        itemDeleteCheck(searchDto.getIsDelete()),
                        itemSellCheck(searchDto.getIsSell()))
                .fetchOne();


        return new PageImpl<>(items, pageable, total);
    }


    //-------------------------------------------------------
    private BooleanExpression itemNameContains(String itemName) {

        if(itemName == null){
            return null;
        }
        return itemName.isEmpty() ? null : QItem.item.name.contains(itemName);
    }

    private BooleanExpression categoryIdEq(Long categoryId) {



        return categoryId == null ? null : QCategory.category.categoryId.eq(categoryId);
    }

    private BooleanExpression itemDeleteCheck(Boolean deleteCheck) {

        if(deleteCheck == null){
            return null;
        }
        return QItem.item.isDelete.eq(deleteCheck);
    }

    private BooleanExpression itemSellCheck(Boolean sellCheck) {

        if(sellCheck == null){
            return null;
        }
        return QItem.item.isSell.eq(sellCheck);
    }
}
