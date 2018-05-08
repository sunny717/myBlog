package com.ydemo.base.app.service;

import com.ydemo.base.app.entity.Content;
import com.ydemo.base.app.entity.Links;
import com.ydemo.base.app.entity.Metas;
import com.ydemo.base.app.model.Archive;
import com.ydemo.base.app.model.SearchQo;
import com.ydemo.base.app.repository.ContentRepository;
import com.ydemo.base.app.repository.LinksRepository;
import com.ydemo.base.app.repository.MetasRepository;
import com.ydemo.base.cores.enumobj.MetaType;
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

import java.util.*;

/**
 * Created by babyant on 17/4/13.
 */
@Service
public class SiteService {
    @Autowired
    private MetasRepository metasRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private LinksRepository linksRepository;

    public List<Archive> getArchivesList(){


        List<Archive> archives = new ArrayList<Archive>();
        List<Map<String,Object>>  lists = new ArrayList<Map<String,Object>>();

        String sql = "SELECT DATE_FORMAT(c.createdate,'%Y-%m') as datestr , COUNT(*) as count from t_content c GROUP BY datestr ORDER BY  datestr desc";

        lists = metasRepository.nativeQuery4Map(sql);

        if(lists.size()>0){
            for(int i=0;i<lists.size();i++){
                Map<String,Object> map = lists.get(i);
                Archive a = new Archive();
                a.setDatestr(map.get("datestr").toString());
                a.setCount(Integer.valueOf(map.get("count").toString()));
                String csql ="SELECT * from t_content c where DATE_FORMAT(c.createdate,'%Y-%m') = ? ";
                List<Content> cs = contentRepository.findAllByNativeSql(csql,new Content(),new Object[]{a.getDatestr()});

                a.setContents(cs);
                archives.add(a);
            }
        }
        return  archives;
    }


    public List<Links> getLinksList(){

        return linksRepository.findAll();
    }

    public Page<Content> findContentByKeywords(SearchQo sq){

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb  = new PredicateBuilder();

        if (!StringUtils.isEmpty(sq.getTitle())) {
            pb.add("title","%" + sq.getTitle() + "%", LinkEnum.LIKE);
        }


        return contentRepository.findAll(pb.build(), Operator.AND, pageable);

    }


    public Page<Content> findContentByTags(SearchQo sq){


        Metas metas = metasRepository.findByNameAndType(sq.getTag(), MetaType.TAGS.getName());
        if(metas == null) return null;

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";



        return contentRepository.findAllByNativeSql(sql,pageable,new Content(),new Object[]{metas.getId()});

    }

    public Page<Content> findContentByCategory(SearchQo sq){


        Metas metas = metasRepository.findOne(Long.valueOf(sq.getCategory()));
        if(metas == null) return null;

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";



        return contentRepository.findAllByNativeSql(sql,pageable,new Content(),new Object[]{metas.getId()});

    }

    public Set<Content> getRecentArticles(){

        Set<Content> contents = new HashSet<Content>();

        //mysql随机从数据库中取出数据 根据页面需要 取出随机8条
         String randsql ="SELECT * FROM t_content t \n" +
                 "WHERE t.id >= (SELECT floor(RAND() * (SELECT MAX(id) FROM t_content)))  \n" +
                 "ORDER BY id LIMIT 1";
         for (int i=0;i<8;i++){
             Content c = contentRepository.findOneByNativeQuery(randsql,new Content());
             if(null != c) contents.add(c);
         }


        return contents;
    }
}
