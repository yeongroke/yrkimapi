package com.yrkim.yrkimapi.utils;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LIstUtils {

    /**
    * 제네릭 타입의 Collection을 하나의 Collection으로 리턴
    * @param lists
    * @return Collection<T>
    * */
    public static <T> Collection<T> listToMergeCollection(Collection<T>... lists) {
        return Stream.of(lists).flatMap(s -> {
            return s.stream();
        }).collect(Collectors.toList());
    }
}
