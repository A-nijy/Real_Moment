package project1.shop.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Address;
import project1.shop.domain.entity.QAddress;
import project1.shop.domain.entity.QItem;
import project1.shop.domain.entity.QMember;
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
    public Page<Address> searchMyAddressList(Long memberId, SearchDto.Addresses searchDto, Pageable pageable) {

        List<Address> addressList = queryFactory.selectFrom(QAddress.address)
                .join(QAddress.address.member, QMember.member)
                .where(QMember.member.memberId.eq(memberId))
                .orderBy(QAddress.address.isDefault.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return null;
    }


}
