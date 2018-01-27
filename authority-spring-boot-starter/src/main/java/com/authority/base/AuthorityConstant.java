package com.authority.base;

import java.util.Arrays;
import java.util.List;

/**
 * 权限常量池
 *      及常用方法
 */
public class AuthorityConstant {

    /**
     *  规定 1000 为菜单
     */
    public final static Short RESOURCE_TYPE_1000 = 1000;

    /**
     *  规定 2000 为操作 包含但不仅限如 add delete update 等
     */
    public final static Short RESOURCE_TYPE_2000 = 2000;

    /**
     *  规定 3000 为文件资源 或者 API资源
     */
    public final static Short RESOURCE_TYPE_3000 = 3000;

    /**
     *  级别
     */
    public final static Short RESOURCE_LEVEL_1 = 1;

    public final static Short RESOURCE_LEVEL_2 = 2;

    public final static Short RESOURCE_LEVEL_3 = 3;

    public final static Short RESOURCE_LEVEL_4 = 4;

    public final static Short RESOURCE_LEVEL_5 = 5;

    // to do


    public static List<Short> resourceTypeList(){
        return Arrays.asList(new Short[]{RESOURCE_TYPE_1000,RESOURCE_TYPE_2000,RESOURCE_TYPE_3000});
    }

    public static List<Short> resourceLevelList(){
        return Arrays.asList(new Short[]{RESOURCE_LEVEL_1,RESOURCE_LEVEL_2,RESOURCE_LEVEL_3,RESOURCE_LEVEL_4,RESOURCE_LEVEL_5});
    }

    public static boolean containResourceType(Short o1){
        if(o1==null){return false;}
        List<Short> l = resourceTypeList();
        return l.contains(o1);
    }
    public static boolean containResourceLevel(Short o1){
        if(o1==null){return false;}
        List<Short> l = resourceLevelList();
        return l.contains(o1);
    }
    public static String assembleResourceLevel(){
        StringBuffer sb = new StringBuffer();
        List<Short> l = resourceLevelList();
        for (int i=0;i<l.size();i++) {
            sb.append(l.get(i));
            if(i<l.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String assembleResourceType(){
        StringBuffer sb = new StringBuffer();
        List<Short> l = resourceTypeList();
        for (int i=0;i<l.size();i++) {
            sb.append(l.get(i));
            if(i<l.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static <T> String convertObjToString(T t){
        if(t==null){return "";}
        else return t.toString();
    }
}
