package com.cy.microserviceapi.util;

import java.util.Date;

/**
 * @作者 chenyi
 * @date 2019/5/31 15:18
 */
public class DateUtil {
    public static Date expire(Long expire){
        return new Date(System.currentTimeMillis()+expire);
    }
}
