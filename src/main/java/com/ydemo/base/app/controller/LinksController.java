package com.ydemo.base.app.controller;

import com.ydemo.base.app.entity.Links;
import com.ydemo.base.app.repository.LinksRepository;
import com.ydemo.base.cores.obj.AjaxResult;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by babyant on 17/4/11.
 */
@Controller
@RequestMapping("/links")
public class LinksController {

    private static Logger log = LoggerFactory.getLogger(LinksController.class);

    private final static Integer SIZE = 10;
    @Autowired
    private LinksRepository linksRepository;

    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        Integer size = SIZE;
        Pageable pages = new PageRequest(page==null?0:page,size,new Sort(Sort.Direction.DESC,"id"));

        Page<Links> linksPage = linksRepository.findAll(pages);

        map.put("totals",linksPage.getTotalPages());
        map.put("page",page+1);
        map.put("linkslist",linksPage.getContent());
        map.put("menu_code","mainlinks");
        return "links/index";
    }
    @RequestMapping("/save")
    @ResponseBody
    public String save(Links links){

        if(StringUtils.isEmpty(links.getName())){
            return "0";
        }

        linksRepository.save(links);
        log.info("保存友情链接");
        return "1";
    }

    @RequestMapping(value = "/edit/{id}")
    @ResponseBody
    public AjaxResult edit(@PathVariable Long id, ModelMap map){

        Links links= linksRepository.findOne(id);

        log.info("编辑->links : "+links.toString());

        return new AjaxResult(links);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id){

        linksRepository.delete(id);
        log.info("删除->id : "+id);

        return "1";
    }
}
