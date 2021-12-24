package com.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * 使用Jedis接口实现Redis的常用操作
 * */
public class JedisTest002 {


    public static void main(String[] args) {
        stringCommand();
    }

    private static void stringCommand() {
        //String类型常用命令
        //1, set k1 v1 /  get k1 :
        Jedis  jedis = new Jedis("192.168.145.128", 6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        jedis.set("k1", "Tom");
        jedis.set("k2", "100");
        jedis.set("k3", "MacJones");   //这里存完之后，去Linux执行redis-cli查看即可看到数据已添加进去了。
        jedis.set("k4", "60");
        jedis.set("k5", "abcdefghijk");
        System.out.printf("%s\n%s\n%s\n",jedis.get("k1"), jedis.get("k2"), jedis.get("k3"));   //printf()方法练习，String类型对应 “%s”

        //2, append k1  xyz : 拼接字符串
        jedis.append("k1", " Brady");
        System.out.println("append k1==>" + jedis.get("k1"));

        //3, strlen k3 : (StringLength) 获取String类型值的长度
        Long strlen = jedis.strlen("k3");
        System.out.println("k3 strlen==>" + strlen);

        //4, getrange k5  0 4 : 按下表和长度截取k5对应字符串值，获取0到4下标的值,首尾下标值都包含
        String  substring = jedis.getrange("k5", 0 , 4);
        System.out.println("substring==>" + substring);

        //5, setrange k5 0 xyz : 从下标0开始把字符替换为xyz
        jedis.setrange("k5", 0, "xyz");
        System.out.println("setrange  k5===> " + jedis.get("k5"));

        //6, incr k2 : 自增1，要求值必须是数字类型的,否则报错
        //jedis.incr("k1");  //k1不是数字, 报错
        jedis.incr("k2");
        System.out.printf("%s\n%s\n", jedis.get("k1"),jedis.get("k2"));

        //7, incrby k2 5 : 指定自增的值
        jedis.incrBy("k2", 5);
        System.out.println("k2 incrBy 5 ==>" + jedis.get("k2"));

        //8, decr k4 : 自减1
        jedis.decr("k4");
        System.out.println("k4 decr==>" + jedis.get("k4"));

        //9, decrby k4 2 : 自减指定值
        jedis.decrBy("k4", 2);
        System.out.println("k4 decrBy==>" + jedis.get("k4"));

        //10, setex k6 15 Lily : (set with expire) 添加时定好存活时间
        jedis.setex("k6", 15, "Lily");
        try {
            System.out.println(Thread.currentThread().getName() + "休眠2秒。。。。。");
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("k6 ttl==>" + jedis.ttl("k6"));

        //11, setnx k6 Jane : (set if not exists)如果不存在该键则添加，存在则不可覆盖,set命令是覆盖的
        jedis.setnx("k6","Jane");
        System.out.println("k6 setnx==>" + jedis.get("k6"));

        /*//3, del k1 : 删除键
        jedis.del("k1");   //Long del(String... keys) :形参为可变长度，可删除多个key
        jedis.del("k2", "k3");
        System.out.println("del result==>" + jedis.get("k1"));*/




    }

}
