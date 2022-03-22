package com.yrkim.yrkimapi.repository.querydsl;

import com.yrkim.yrkimapi.model.entity.Board;
import com.yrkim.yrkimapi.repository.querydsl.support.QuerydslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static com.yrkim.yrkimapi.model.entity.QBoard.board;

@Repository
public class BoardQueryRepository extends QuerydslRepositorySupport {

    public BoardQueryRepository() {
        super(Board.class);
    }

    public Page<Board> selectBoardList(Pageable pageable) {
        return applyPagination(pageable,
                contentQuery -> contentQuery.selectFrom(board));
    }
}
