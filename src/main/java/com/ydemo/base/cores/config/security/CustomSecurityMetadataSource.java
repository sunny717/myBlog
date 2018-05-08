package com.ydemo.base.cores.config.security;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    private static final Logger logger = Logger.getLogger(CustomSecurityMetadataSource .class);

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private PathMatcher pathMatcher = new AntPathMatcher();

    private String urlroles;

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**从配置文件读取权限的构造方法*/
    public CustomSecurityMetadataSource  (String urlroles) {
        super();
        this.urlroles = urlroles;
        resourceMap = loadResourceMatchAuthority();
    }
    /**自定义从数据库中查找权限的构造方法*/
    /*public CustomSecurityMetadataSource  (List<Permission> permissions) {
        super();
        this.permissions = permissions;
        resourceMap = loadResourceMatchAuthorityFromSql();
    }*/
/*
    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthorityFromSql() {

        //这里的主要目的是得到所有的 url= roles  即是路径：权限列表。
        Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();

            if(permissions.size() >0){
                for(Permission p :permissions){
                    //name 保存的数据以,隔开

                    String url = p.getUrl().trim();
                    //下面分别得到 URL和权限的对应列表
                    Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();

                    for(Role role : p.getRoleList()){
                        ConfigAttribute config = new SecurityConfig(role.getName());
                        list.add(config);
                        map.put(url, list);
                    }
                }
        }else{
            logger.error("'数据库中无权限记录。");
        }

        logger.info("Loaded UrlRoles Resources.");
        return map;

    }*/
    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {

        //这里的主要目的是得到所有的 url= roles  即是路径：权限列表。
        Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();

        if(urlroles != null && !urlroles.isEmpty()){
            String[] resouces = urlroles.split(";");
            for(String resource : resouces){
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                for(String role : roles){
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
//                key：url, value：roles
                map.put(urls[0].trim(), list);
            }
        }else{
            logger.error("'securityconfig.urlroles' must be set");
        }

        logger.info("Loaded UrlRoles Resources.");
        return map;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        logger.info("request url is  " + url);

       if(resourceMap == null)
            resourceMap = loadResourceMatchAuthority();

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (pathMatcher.match(resURL,url)) {
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
