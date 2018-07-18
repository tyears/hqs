package com.cccuu.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 序列发生器生成数据库记录唯一的标识
 *
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:29:30
 * @Description
 */
public abstract class UniqueIDGen {

    private static long uniqueID = System.currentTimeMillis();

    /**
     * 生成序列号
     *
     * @return long
     */
    public static synchronized Long getUniqueID() {
        uniqueID++;
        return (uniqueID * 1000 + (new Random()).nextInt(999));
    }

    public static synchronized String getOrdercode() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sf.format(date) + new Random().nextInt(1000);
    }
}