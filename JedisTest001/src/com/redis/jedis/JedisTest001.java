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
        jedis.set("k3", "Mann");
        System.out.println("k1==>" + jedis.get("k1") +"\n"+ "k2==>" + jedis.get("k2"));

        //常用命令,
        //1, info : Redis库的信息    ： 前面是redis命令，每条下面是对应的方法名，它们都一样。
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
        Set<String> keysStar = jedis.keys("k*");
        System.out.println("keysStar==>" + keysStar);

        //7, keys * : 获取当天库的所有键, 对应方法keys("*")
        Set<String>  keys = jedis.keys("*");
        Iterator it = keys.iterator();
        while(it.hasNext()){
            System.out.println("keys * ==>" + it.next());
        }

        //8, move k1 2 : 把k1键值对移到2号库中
        Long isSuccess =jedis.move("k1", 2);  //成功则返回1， 失败(或没有此键)0，对应redis界面的integer(1/0)
        System.out.println("isSuccess==>" + isSuccess);
        jedis.select(2);  //选择2号库验证是否成功
        System.out.println("database[2] : k1 ==>" + jedis.get("k1"));

        //9, exists key02 : 是否存在key02, 结果是integer(1)表示有，integer(0)表示没有
        jedis.select(0);    //回到0号库
        boolean exist = jedis.exists("k1");
        System.out.println("exist k1==>" + exist);

        //10, expire k2 10 : 设置键的存活时间,单位是秒
        jedis.expire("k2",30);

        //11, ttl k2: 查看k2的寿命
        Long ttl = jedis.ttl("k2");
        System.out.println("k1 time to live ==> " + ttl);

        //12, flushdb : 清空当前库
        String flushDBInfo = jedis.flushDB();
        System.out.printf("%s\n",flushDBInfo);    //输出：ok

        //13, flushall : 清空所有库
        String flushAllInfo = jedis.flushAll();
        System.out.printf("%s\n", flushAllInfo);  //输出：ok
    }
}
