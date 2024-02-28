package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project1.shop.domain.entity.Address;
import project1.shop.domain.entity.QAddress;
import project1.shop.domain.entity.QItem;
import project1.shop.domain.entity.QMember;
import project1.shop.domain.repository.custom.AddressRepositoryCustom;

public class AddressRepositoryImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AddressRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }



    @Override
    public Address searchMainAddress(Long memberId) {

        Address address = queryFactory.selectFrom(QAddress.address)
                .join(QAddress.address.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId),
                        QAddress.address.isDefault.eq(true))
                .fetchOne();



        return address;
    }



}
