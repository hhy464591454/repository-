package com.authority.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ListUtils {

    /**
     *    list 转 map
     * @param list 集合
     * @param keyMethodName 需要设置为key的方法名
     * @param c
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> listToMap(List<V> list, String keyMethodName, Class<V> c) {
        Map<K, V> map = new LinkedHashMap<K, V>();
        if (list != null) {
            try {
                Method methodGetKey = c.getMethod(keyMethodName);
                for (int i = 0; i < list.size(); i++) {
                    V value = list.get(i);
                    @SuppressWarnings("unchecked")
                    K key = (K) methodGetKey.invoke(list.get(i));
                    map.put(key, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("field can't match the key!");
            }
        }
        return map;
    }

    /**
     *  list互转
     *      如：List<A> 转  List<B>
     * @param source 需要转换的源List
     * @param target 目标集合class
     * @param <T1>
     * @param <T2>
     * @return  返回转换后的实体List
     */
    public static  <T1,T2> List<T2> listCopy(List<T1> source, Class<T2> target) {
        List<T2> list = new ArrayList<>();
        if(source==null || source.size()<=0){
            return list;
        }
        if(target==null){
            throw new RuntimeException("target is null.");
        }
        for (T1 t1:source) {
            list.add(copyBean(t1,target));
        }
        return list;
    }

    /**
     *  将一个对象转换为另一个对象
     * @param source 被转化的对象
     * @param target 转换后的类class
     * @param <T1>
     * @param <T2>
     * @return  转换后的对象
     */
    public static  <T1,T2> T2 copyBean(T1 source, Class<T2> target) {
        T2 returnModel = null;
        try {
            returnModel = target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建"+target.getName()+"对象失败");
        }
        List<Field> fieldlist = new ArrayList<Field>(); //要转换的字段集合
        while (target != null && !target.getName().toLowerCase().equals("java.lang.object")) {
            fieldlist.addAll(Arrays.asList(target.getDeclaredFields()));
            target = (Class<T2>) target.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fieldlist) {
            PropertyDescriptor getpd = null;
            PropertyDescriptor setpd = null;
            try {
                getpd= new PropertyDescriptor(field.getName(), source.getClass());
                setpd=new PropertyDescriptor(field.getName(), returnModel.getClass());
            } catch (Exception e) {
                continue;
            }
            try {
                Method getMethod = getpd.getReadMethod();
                Object transValue = getMethod.invoke(source);
                Method setMethod = setpd.getWriteMethod();
                setMethod.invoke(returnModel, transValue);
            } catch (Exception e) {
                throw  new RuntimeException("cast "+source.getClass().getName()+"to "
                        +target.getName()+" failed");
            }
        }
        return returnModel;
    }



//    public static void main(String[] args){
//        A a1 = new A();//源
//        a1.setA(new A());
//        a1.setFlag(false);
//        a1.setId(11111111L);
//        a1.setMoney(new BigDecimal(40.8));
//        a1.setNum(90);
//        a1.setText("sdfasdfsafdafsadfasf");
//        a1.setTime(new Date());
//        A a2 = new A();//源
//        a2.setA(new A());
//        a2.setFlag(true);
//        a2.setId(2222222222222L);
//        a2.setMoney(new BigDecimal(342.9));
//        a2.setNum(220);
//        a2.setText("HHHSDFAFASDFSDAFSFSAF");
//        a2.setTime(new Date());
//
//        B b1 = new B();
//        List<A> alist = new ArrayList<>();
//        alist.add(a1);alist.add(a2);
//        List<B> list = listCopy(alist,B.class);
//        System.out.println(list);
//        list.stream().forEach((p)->System.out.println(p.getId()));
//    }
//
//    static class A {
//        private Long id;
//        private Integer num;
//        private String text;
//        private Date time;
//        private Boolean flag;
//        private BigDecimal money;
//        private A a;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Integer getNum() {
//            return num;
//        }
//
//        public void setNum(Integer num) {
//            this.num = num;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public Date getTime() {
//            return time;
//        }
//
//        public void setTime(Date time) {
//            this.time = time;
//        }
//
//        public Boolean getFlag() {
//            return flag;
//        }
//
//        public void setFlag(Boolean flag) {
//            this.flag = flag;
//        }
//
//        public BigDecimal getMoney() {
//            return money;
//        }
//
//        public void setMoney(BigDecimal money) {
//            this.money = money;
//        }
//
//        public A getA() {
//            return a;
//        }
//
//        public void setA(A a) {
//            this.a = a;
//        }
//    }
//
//    static class B {
//        private Long id;
//        private Integer num;
//        private String text;
//        private Date time;
//        private Boolean flag;
//        private BigDecimal money;
//        private B b;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Integer getNum() {
//            return num;
//        }
//
//        public void setNum(Integer num) {
//            this.num = num;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//
//        public Date getTime() {
//            return time;
//        }
//
//        public void setTime(Date time) {
//            this.time = time;
//        }
//
//        public Boolean getFlag() {
//            return flag;
//        }
//
//        public void setFlag(Boolean flag) {
//            this.flag = flag;
//        }
//
//        public BigDecimal getMoney() {
//            return money;
//        }
//
//        public void setMoney(BigDecimal money) {
//            this.money = money;
//        }
//
//        public B getB() {
//            return b;
//        }
//
//        public void setB(B b) {
//            this.b = b;
//        }
//    }
}
