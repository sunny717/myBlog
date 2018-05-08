package com.ydemo.base.cores.controller;


import com.ydemo.base.cores.entity.Role;
import com.ydemo.base.cores.obj.AjaxResult;
import com.ydemo.base.cores.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by babyant on 17/3/6.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    private static final Integer SIZE = 10;

    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){

        logger.info("进行查询操作");
        //由于前后台分页差1 查询的时候 要-1  返回去的时候+1
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        Integer size = SIZE;
        Pageable pages = new PageRequest(page==null?0:page,size,new Sort(Sort.Direction.DESC,"id"));


        //这里需要得到所有的角色列表。

        Page<Role> rolePage = roleRepository.findAll(pages);

        List<Role> roleList = rolePage.getContent();
        Integer totals = rolePage.getTotalPages();

        map.put("totals",totals);
        map.put("page",page+1);
        map.put("roleList",roleList);
        map.put("menu_code","mainrole");
        return "role/index";
    }

    @RequestMapping("/add")
    public ModelAndView create(ModelMap map){



        logger.info("进行新增操作");
        return new ModelAndView("role/add");
    }

    @RequestMapping("/save")
    public AjaxResult save(Role role){

        roleRepository.save(role);

        logger.info("添加/更新的角色 ： "+role.toString());

        return  new AjaxResult(true,"200",role);
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id){

        Role role = roleRepository.findOne(id);
        roleRepository.delete(role);
        logger.info("删除->id : "+id);

        return "1";
    }

    @RequestMapping(value = "/deleteAll",method = RequestMethod.POST)
    public String deleteAll(HttpServletRequest req){

        String ids = req.getParameter("ids");
        if(!StringUtils.isEmpty(ids)){
            String idList[] = ids.split("-");
            for (int i = 0; i < idList.length; i++) {
                Long id = Long.valueOf(idList[i]);

                roleRepository.delete(id);
            }
            return "1";
        }else{
            return "0";
        }
    }


    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Long id, ModelMap map){

        Role role = roleRepository.findOne(id);

        map.put("role",role);
        logger.info("编辑->role : "+role.toString());

        return new ModelAndView("role/edit");
    }



}
