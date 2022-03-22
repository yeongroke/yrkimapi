package com.yrkim.yrkimapi.repository.querydsl;

import com.yrkim.yrkimapi.model.entity.User;
import com.yrkim.yrkimapi.repository.querydsl.support.QuerydslRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryRepository extends QuerydslRepositorySupport {
    public UserQueryRepository() {
        super(User.class);
    }
}
