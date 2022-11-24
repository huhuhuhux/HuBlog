package com.huhuhux.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {

    }

    public static <V> V beanCopy(Object source,Class<V> clazz){
        V result = null;
        try {
            result = clazz.newInstance();

            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static <V> List<V> beanCopyList(List<?> list,Class<V> clazz){
        List<V> resultList = list.stream()
                .map(o -> beanCopy(o, clazz))
                .collect(Collectors.toList());
        return resultList;
    }

}