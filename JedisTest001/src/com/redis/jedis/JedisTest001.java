package com.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * 使用Jedis初步连接redis库
 * (1)Jedis中的常用方法名和redis的常用命令大多都一致
 * (2)如果从MySQL中向Redis存高频使用的数据，少量的话使用Jedis类，大量数据就需要运维或DBA写脚本执行了；
 * */
public class JedisTest001 {

    public static void main(String[] args) {
        Jedis  jedis = new Jedis("192.168.145.128", 6379);    //注意不同的环境要改变服务器地址
        jedis.auth("123456");     //注意要设置密码
        System.out.println(jedis.ping());
        //添加String类型
        jedis.set("k1", "Tom");
        jedis.set("k2", "Jerry");
        System.out.println("k1==>" + jedis.get("k1") +"\n"+ "k2==>" + jedis.get("k2"));

        //常用命令,
        //1, info : Redis库的信息    ： 前面是redis命令，每条下面是对应的方法名，都一样。
        String info = jedis.info();
        System.out.println("redis info==>" + info);
        //2, type : 查看键对应值的数据类型
        String type = jedis.type("k1");
        System.out.println("type==>" + type);
        //3, select : 选择Redis的库，Redis共16个库，下标从0开始，默认选中0号库
        String select = jedis.select(1);  //选中1号库
        System.out.println("select==>" + select);
        //4, [db] : 查看当前库名，Redis界面中无此命令，界面默认显示库名[1]
        Long dbName = jedis.getDB();
        System.out.println("db name==>" + dbName);
        //5, dbsize : 当前库的键值对个数
        Long dbSize = jedis.dbSize();
        System.out.println(dbName + "号库，键值对个数==>" + dbSize);
        //6, keys k* : 获取键名中含有k的键，“*”是Redis多个字符的通配符
        Set<String> keysNum = jedis.keys("k*");
        //获取所有key
        Set<String>  keys = jedis.keys("*");
        Iterator it = keys.iterator();
        while(it.hasNext()){
            System.out.println("keys * ==>" + it.next());
        }
    }
}
