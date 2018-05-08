package com.ydemo.base.cores.repository;

import com.ydemo.base.cores.entity.User;
import com.ydemo.base.cores.repository.expand.ExpandJpaRepository;
import com.ydemo.base.cores.repository.parameter.Operator;
import com.ydemo.base.cores.repository.parameter.Predicate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by babyant on 17/3/6.
 */
@Repository
public interface UserRepository extends ExpandJpaRepository<User,Long> {
    @Cacheable("userCache")
    User findByUsername(String username);

    /**
     * 添加的时候移除缓存，查找的时候加入缓存。
     * 需要加上 allEntries = true 清楚当前缓存中的集合元素
     * @return
     */
    @CacheEvict(value = "userCache",allEntries = true)
    User save(User user);


    @Cacheable(value = "userCache")
    @Override
    Page<User> findAll(Iterable<Predicate> predicates, Operator operator, Pageable pageable);


    @CacheEvict(value = "userCache",allEntries = true)
    User findOne(Long aLong);
}
