package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.custom.AddressRepositoryCustom;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;

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



    @Override
    public Page<Address> searchMyAddressList(Long memberId, Pageable pageable) {

        List<Address> addressList = queryFactory.selectFrom(QAddress.address)
                .join(QAddress.address.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId))
                .orderBy(QAddress.address.isDefault.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QAddress.address.count())
                .from(QAddress.address)
                .join(QAddress.address.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(addressList, pageable, total);
    }


}
