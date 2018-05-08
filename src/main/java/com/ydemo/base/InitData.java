package com.ydemo.base;

import com.ydemo.base.app.entity.Metas;
import com.ydemo.base.app.repository.MetasRepository;
import com.ydemo.base.cores.entity.Role;
import com.ydemo.base.cores.entity.User;
import com.ydemo.base.cores.repository.RoleRepository;
import com.ydemo.base.cores.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by babyant on 17/3/17.
 */
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MetasRepository metasRepository;


    @Override
    public void run(String... strings) throws Exception {
        System.out.println("-------数据初始化程序---start-----");

        List<User> users = userRepository.findAllByNativeSql("select * from t_user u where u.username='admin' ", new User(),null);

        /**
         * 添加一个admin用户带有admin权限
         */
        System.out.println(users.size());
        if(users.size()>0){
            System.out.println("已存在数据");
            System.out.println("-------数据初始化程序---end-----");
            return ;
        }
        Role role  = new Role();
        role.setName("admin");
        roleRepository.save(role);

        User user = new User();
        user.setCreatedate(new Date());
        user.setUsername("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));

        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        Metas metas = metasRepository.findOne(Long.valueOf(1));

        metas.setType("category");
        metas.setName("默认分类");

        metasRepository.save(metas);




        System.out.println("-------数据初始化程序---end------");

    }
}
