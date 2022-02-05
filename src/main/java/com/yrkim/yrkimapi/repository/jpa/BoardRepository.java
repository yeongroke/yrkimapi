package com.yrkim.yrkimapi.repository.jpa;

import com.yrkim.yrkimapi.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board , Long> {
}
