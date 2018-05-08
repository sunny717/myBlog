package com.ydemo.base.cores.repository;


import com.ydemo.base.cores.entity.Role;
import com.ydemo.base.cores.repository.expand.ExpandJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by babyant on 17/3/6.
 */
@Repository
public interface RoleRepository extends ExpandJpaRepository<Role,Long> {

    @CacheEvict(value = "roleCache",allEntries = true)
    @Override
    Role save(Role role);

    @Cacheable(value = "roleCache")
    @Override
    Page<Role> findAll(Pageable pageable);
}
