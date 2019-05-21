package org.ingue.mall.config.common.mapper;

public interface Mapper<T, E> {

    public T mappingDto(E e);
}
