package com.yrkim.yrkimapi.repository.querydsl.support;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Repository
public abstract class QuerydslRepositorySupport {
    private final Class domainClass;
    private Querydsl querydsl;
    private JPAQueryFactory query;
    @Autowired
    private EntityManager em;

    @PostConstruct
    public void validate() {
        Assert.notNull(querydsl, "querydsl must not be null");
        Assert.notNull(query, "query must not be null");
        Assert.notNull(em, "em must not be null");
    }

    public QuerydslRepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "domainClass must not be null");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEntityManager() {
        JpaEntityInformation jpaEntityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath path = resolver.createPath(jpaEntityInformation.getJavaType());
        // querydsl sort를 이용하기위해 적용
        this.querydsl = new Querydsl(em, new PathBuilder<Object>(path.getType(), path.getMetadata()));
        this.query = new JPAQueryFactory(em);
    }

    protected JPAQueryFactory getQuery() {
        return query;
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected EntityManager getEm() {
        return em;
    }

    protected <T> JPAQuery<T> select(Expression<T> expression) {
        return getQuery().select(expression);
    }

    protected <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getQuery().selectFrom(from);
    }

    protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery) {
        JPAQuery jpaQuery = contentQuery.apply(getQuery());
        List<T> content = getQuerydsl().applyPagination(pageable, jpaQuery).fetch();
        return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount);
    }

    protected <T> Page<T> applyPagination(Pageable pageable, Function<JPAQueryFactory, JPAQuery> contentQuery, Function<JPAQueryFactory, JPAQuery> countQuery) {
        JPAQuery jpaContentQuery = contentQuery.apply(getQuery());
        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();
        JPAQuery countResult = countQuery.apply(getQuery());
        return PageableExecutionUtils.getPage(content, pageable, countResult::fetchCount);
    }
}
