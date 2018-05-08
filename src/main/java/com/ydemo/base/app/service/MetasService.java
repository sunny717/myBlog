package com.ydemo.base.app.service;

import com.ydemo.base.app.repository.MetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by babyant on 17/4/13.
 */
@Service
public class MetasService {
    @Autowired
    private MetasRepository metasRepository;

    public List<Map<String,Object>> getByCategoryOrTags(String type){

        List<Map<String,Object>>  lists = new ArrayList<Map<String,Object>>();

        String sql = "select a.*, count(b.cid) as count from t_metas a left join `t_relationships` b on a.id = b.mid  " +
                "  where a.type = '"+type+"' group by a.id order by count desc, a.id desc  ";

        lists = metasRepository.nativeQuery4Map(sql);

        return  lists;
    }
}
