package com.ydemo.base.cores.service;


import com.ydemo.base.cores.entity.User;
import com.ydemo.base.cores.model.UserQo;
import com.ydemo.base.cores.repository.UserRepository;
import com.ydemo.base.cores.repository.parameter.LinkEnum;
import com.ydemo.base.cores.repository.parameter.Operator;
import com.ydemo.base.cores.repository.parameter.PredicateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        User user = userRepository.findOne(id);
        return user;
    }

    public User create(User user) {
        User newUser = userRepository.save(user);

        return newUser;
    }

    public User update(User user) {

        return userRepository.save(user);
    }

    public void delete(Long id) {

        userRepository.delete(id);
    }

    public Page<User> findPage(UserQo userQo){
       Pageable pageable = new PageRequest(userQo.getPage(), userQo.getSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb  = new PredicateBuilder();

        if (!StringUtils.isEmpty(userQo.getName())) {
            pb.add("name","%" + userQo.getName() + "%", LinkEnum.LIKE);
        }
        if (!StringUtils.isEmpty(userQo.getCreatedateStart())) {
            pb.add("createdate",userQo.getCreatedateStart(), LinkEnum.GE);
        }
        if (!StringUtils.isEmpty(userQo.getCreatedateEnd())) {
            pb.add("createdate",userQo.getCreatedateEnd(), LinkEnum.LE);
        }

        return userRepository.findAll(pb.build(), Operator.AND, pageable);
    }

}
