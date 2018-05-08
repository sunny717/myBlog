package com.ydemo.base.app.repository;

import com.ydemo.base.app.entity.Links;
import com.ydemo.base.cores.repository.expand.ExpandJpaRepository;
import com.ydemo.base.cores.repository.parameter.Operator;
import com.ydemo.base.cores.repository.parameter.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by hongbin on 17/3/6.
 */
@Repository
public interface LinksRepository extends ExpandJpaRepository<Links,Long> {

    @Override
    Page<Links> findAll(Iterable<Predicate> predicates, Operator operator, Pageable pageable);


    Links findOne(Long aLong);
}
