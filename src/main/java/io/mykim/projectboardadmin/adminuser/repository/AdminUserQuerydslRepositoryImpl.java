package io.mykim.projectboardadmin.adminuser.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.entity.QAdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static io.mykim.projectboardadmin.adminuser.entity.QAdminUser.adminUser;
import static io.mykim.projectboardadmin.global.jpa.QuerydslUtils.getOrderSpecifier;

public class AdminUserQuerydslRepositoryImpl implements AdminUserQuerydslRepository {

    private JPAQueryFactory jpaQueryFactory;

    public AdminUserQuerydslRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<AdminUser> findAllAdminUsers(Pageable pageable, String searchKeyword) {
        List<AdminUser> adminUsers = jpaQueryFactory
                                        .selectFrom(adminUser)
                                        .where(createUniversalSearchCondition(searchKeyword))
                                        .orderBy(getOrderSpecifier(pageable.getSort(), adminUser.getType(), adminUser.getMetadata())
                                                .stream()
                                                .toArray(OrderSpecifier[]::new))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetch();

        Long count = jpaQueryFactory
                        .select(adminUser.count())
                        .from(adminUser)
                        .where(createUniversalSearchCondition(searchKeyword))
                        .fetchOne();

        return new PageImpl<>(adminUsers, pageable, count);
    }

    private BooleanExpression createUniversalSearchCondition(String keyword) {
        return !StringUtils.hasLength(keyword) ? null : adminUser.username.contains(keyword)
                                                            .or(adminUser.nickname.contains(keyword))
                                                            .or(adminUser.email.contains(keyword));
    }
}
