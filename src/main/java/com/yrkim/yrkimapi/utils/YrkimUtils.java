package com.yrkim.yrkimapi.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
* 이름을 머라고 지어야할지 몰라서 일단 그냥 잡동사니 utils....
* */
public class YrkimUtils {
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... getKeys) {
        final Map<List<?>, Boolean> map = new ConcurrentHashMap<>();
        return t -> {
            final List<?> keys = Arrays.stream(getKeys)
                    .map(k -> k.apply(t))
                    .collect(Collectors.toList());
            return map.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
