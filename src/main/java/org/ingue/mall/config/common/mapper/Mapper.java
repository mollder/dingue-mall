package org.ingue.mall.config.common.mapper;

public interface Mapper<T, E> {

    T mappingDto(E e);
}
