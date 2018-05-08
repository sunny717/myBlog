package com.ydemo.base.cores.repository.expand;


import com.ydemo.base.cores.repository.parameter.Operator;
import com.ydemo.base.cores.repository.parameter.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface ExpandJpaRepository<T, ID extends Serializable> extends JpaRepository<T,ID> {

    T findOne(String condition, Object... objects);

    List<T> findAll(String condition, Object... objects);

    List<T> findAll(Iterable<Predicate> predicates, Operator operator);

    List<T> findAll(Iterable<Predicate> predicates, Operator operator, Sort sort);

    Page<T> findAll(Iterable<Predicate> predicates, Operator operator, Pageable pageable);

    long count(Iterable<Predicate> predicates, Operator operator);

    List<T> findAll(String condition, Sort sort, Object... objects);

    //根据sql查询当前对象的集合。比如定义一长串的语句，但是返回的是当前实体。
    List<T> findAllByNativeSql(String sql,Object obj,Object[] objects);

    Page<T> findAllByNativeSql(String sql,Pageable pageable,Object obj,Object[] objects);

    Page<T> findAll(String condition, Pageable pageable, Object... objects);

    long count(String condition, Object... objects);

    void deleteByIds(Iterable<ID> ids);

    Class<T> getEntityClass();

    List<Map<String,Object>> nativeQuery4Map(String sql);

    Page<Map> nativeQuery4Map(String sql, Pageable pageable);

    Object nativeQuery4Object(String sql);

    T findOneByNativeQuery(String sql,Object obj);
}
