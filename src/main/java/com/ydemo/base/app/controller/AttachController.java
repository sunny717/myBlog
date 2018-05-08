package com.ydemo.base.app.controller;

import com.ydemo.base.app.entity.Attach;
import com.ydemo.base.app.repository.AttachRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by babyant on 17/4/11.
 */
@Controller
@RequestMapping("/file")
public class AttachController {

    private static Logger log = LoggerFactory.getLogger(AttachController.class);

    private final static Integer SIZE = 12;
    @Autowired
    private AttachRepository attachRepository;

    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        Integer size = SIZE;
        Pageable pages = new PageRequest(page==null?0:page,size,new Sort(Sort.Direction.DESC,"id"));

        Page<Attach> apage = attachRepository.findAll(pages);

        map.put("totals",apage.getTotalPages());
        map.put("page",page+1);
        map.put("attachs",apage.getContent());
        map.put("menu_code","mainfile");
        return "attach/index";
    }
    @RequestMapping("/save")
    @ResponseBody
    public String save(Attach attach){

//        attach.setUserid();
        attach.setCreatedate(new Date());
        attachRepository.save(attach);
        log.info("保存附件");
        return "1";
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id){

        attachRepository.delete(id);
        log.info("删除->id : "+id);

        return "1";
    }
}
