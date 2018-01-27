package com.authority.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class JpaUtils {
    @Autowired
    private EntityManagerFactory emf;
    private static JpaUtils jpaUtils;

    @PostConstruct
    public void init() {
        jpaUtils = this;jpaUtils.emf=this.emf;
    }

    private static EntityManager createEntityManager(){
        return jpaUtils.emf.createEntityManager();
    }

    /**
     *  原生sql查询该
     * @param c
     * @param sql
     * @param <T>
     * @return
     */
    public static  <T> List<T> nativeQueryForList(Class c,String sql){
        return createEntityManager().createNativeQuery(sql,c).getResultList();
    }
}
