package rm.backend.domain.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rm.backend.domain.entity.Admin;
import rm.backend.domain.entity.QAdmin;
import rm.backend.domain.repository.custom.AdminRepositoryCustom;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;

public class AdminRepositoryImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdminRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Admin> searchAdmins(SearchDto.Admins searchDto, Pageable pageable) {

        List<Admin> admins = queryFactory
                .selectFrom(QAdmin.admin)
                .where(loginIdContains(searchDto.getLoginId()),
                        nameContains(searchDto.getName()),
                        rolesEq(searchDto.getRoles()))
                .orderBy(QAdmin.admin.roles.asc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(QAdmin.admin.count())
                .from(QAdmin.admin)
                .where(loginIdContains(searchDto.getLoginId()),
                        nameContains(searchDto.getName()),
                        rolesEq(searchDto.getRoles()))
                .fetchOne();


        return new PageImpl<>(admins, pageable, total);
    }


    //--------------------------------------------------------------
    private BooleanExpression loginIdContains(String loginId){

        if(loginId == null){
            return null;
        }
        return loginId.isEmpty() ? null : QAdmin.admin.loginId.contains(loginId);
    }

    private BooleanExpression nameContains(String name){

        if(name == null){
            return null;
        }
        return name.isEmpty() ? null : QAdmin.admin.name.contains(name);
    }

    private BooleanExpression rolesEq(String roles){

        if(roles == null){
            return null;
        }
        return roles.isEmpty() ? null : QAdmin.admin.roles.eq(roles);
    }
}
