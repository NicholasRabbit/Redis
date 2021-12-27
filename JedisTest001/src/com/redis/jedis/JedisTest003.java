package com.redis.jedis;


import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisTest003 {

    public static void main(String[] args) {
        redisList();
    }

    private static void redisList() {
        /* List类型数据相关方法范例
         * List集合特性：有序可重复，跟java一样，因此对一个list类型的键多次添加值，不会覆盖原来的值，即使相同也不覆盖。
         *  */
        Jedis jedis = new Jedis("8.142.134.196", 6379);
        jedis.auth("$C&ayman1463852^");

        jedis.flushDB();

        //1, lpush list01 1,2,3,4,5 : 左压栈式添加，即先添加的在栈底部，遵循先进后出，后进先出原则
        //注意，如果多次执行此命令会在值list集合后面持续添加元素，而不会覆盖前面已添加的元素，即结果是：1234512345
        jedis.lpush("list01","1","2","3","4","5");
        System.out.println("lpush list01==>" + jedis.lrange("list01",0,-1));  //输出：[5, 4, 3, 2, 1]

        //2, rpush list02 10,11,12,13,14,15 : 右压栈，与左压栈相反，遵循先进先出，后进后出原则
        jedis.rpush("list02","10","11","12","13","14","15");
        System.out.println("rpush list02==>" + jedis.lrange("list02",0,-1));

        //3, lpop list01 : (left pop) 弹出栈顶元素,即“5”, 原list值里就没有此元素了
        String lpopValue = jedis.lpop("list01");
        System.out.println("list01 lpopValue==>" + lpopValue);
        System.out.println("list01 after lpop==>" + jedis.lrange("list01",0,-1));

        //4, rpop list02 : (right pop) 弹出栈底元素，即“15”
        String rpopValue = jedis.rpop("list02");
        System.out.println("list02 rpopValue==>" + rpopValue);
        System.out.println("list02 after rpop==>" + jedis.lrange("list02",0,-1));

        //5, lrange list01 0 2 : 显示值中下标0~2的所有元素, 首尾下标都包含, 0 ~ -1表示显示所有的元素；
        List<String> list01 = jedis.lrange("list01", 0, 2);
        System.out.println("lrange list01 0,2==>" + list01);

        //6, llen list02 : (list length)获取键“list02”对应的值list集合的长度
        Long llen = jedis.llen("list02");
        System.out.println("lsit02 llen==>" + llen);

        //7, ltrim  list02 0 3 : 修剪集合中0-3下标的值，再赋值给list02, 即只留0-3下标的值
        jedis.ltrim("list02",0,3);
        System.out.println("list02 after ltrim==>" + jedis.lrange("list02",0,-1));

        //8, lrem list03  2 a : 移除集合list03中的两个a
        jedis.lpush("list03","a","a","b","b","c");
        jedis.lrem("list03",2,"a");
        System.out.println("list03 after lrem 2 a ==>" + jedis.lrange("list03",0,-1));

        //9, lset list03 1 x : 把集合list03中的下标1的值替换为x
        jedis.lset("list03",1,"x");
        System.out.println("list03 after lset 1 x ==>" + jedis.lrange("list03",0,-1));

        //10, rpoplpush list02 list03 : 把list02栈底元素弹出，以左压栈的方式给list03
        String rpoplpushValue = jedis.rpoplpush("list02", "list03");
        System.out.println("rpoplpushValue==>" + rpoplpushValue
                            +"\n list02==>" + jedis.lrange("list02",0,-1)
                            +"\nlist03==>" + jedis.lrange("list03",0,-1));

        //11, linsert list02 before/after 11 h : 在list02中的元素“11”前或后插入“h”
        jedis.linsert("list02", BinaryClient.LIST_POSITION.BEFORE, "11", "h");  //形参BEFORE/AFTER是枚举
        System.out.println("list02 linsert==>" + jedis.lrange("list02",0,-1));


    }
}
