package com.yrkim.yrkimapi.repository.jpa;

import com.yrkim.yrkimapi.model.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment , Long> {

}
