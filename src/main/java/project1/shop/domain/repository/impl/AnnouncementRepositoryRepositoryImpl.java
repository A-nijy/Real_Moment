package project1.shop.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project1.shop.domain.entity.Announcement;
import project1.shop.domain.entity.QAnnouncement;
import project1.shop.domain.repository.custom.AnnouncementRepositoryCustom;

import java.util.List;

public class AnnouncementRepositoryRepositoryImpl implements AnnouncementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AnnouncementRepositoryRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Announcement> searchPage(Pageable pageable) {

        List<Announcement> announcementList = queryFactory.selectFrom(QAnnouncement.announcement)
                .orderBy(QAnnouncement.announcement.isFix.desc(),
                        QAnnouncement.announcement.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(QAnnouncement.announcement.count())
                .from(QAnnouncement.announcement)
                .fetchOne();


        return new PageImpl<>(announcementList, pageable, total);
    }
}
