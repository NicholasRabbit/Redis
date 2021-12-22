package com.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * 使用Jedis初步连接redis库
 * Jedis中的常用方法名和redis的常用命令大多都一致
 * */
public class JedisTest001 {

    public static void main(String[] args) {
        Jedis  jedis = new Jedis("192.168.139.131", 6379);
        jedis.auth("123456");     //注意要设置密码
        System.out.println(jedis.ping());
        //添加String类型
        jedis.set("k1", "Tom");
        jedis.set("k2", "Jerry");
        System.out.println("k1==>" + jedis.get("k1") +"\n"+ "k2==>" + jedis.get("k2"));
        //获取所有key
        Set<String>  keys = jedis.keys("*");
        Iterator it = keys.iterator();
        while(it.hasNext()){
            System.out.println("keys==>" + it.next());
        }


    }
}
