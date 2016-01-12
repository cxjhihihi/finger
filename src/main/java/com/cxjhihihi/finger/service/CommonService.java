package com.cxjhihihi.finger.service;

import java.util.List;

/**
 * @author hzcaixinjia
 * @param <T>
 */
public interface CommonService<T> {
    int add(T t);

    int deleteByIds(List<Integer> ids);

    int update(T t);

    T queryById(Integer id);

    List<T> queryAll();

}
