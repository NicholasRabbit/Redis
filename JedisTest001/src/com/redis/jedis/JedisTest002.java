package com.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

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
        Jedis  jedis = new Jedis("8.142.134.196", 6379);
        jedis.auth("$C&ayman1463852^");
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
        jedis.setex("k6", 15, "Lily");  //设定存活时间15秒
        try {
            System.out.println(Thread.currentThread().getName() + "休眠2秒。。。。。");
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("k6 ttl==>" + jedis.ttl("k6"));

        //11, setnx k6 Jane : (set if not exists)如果不存在该键则添加，存在则不可覆盖,set命令是覆盖的
        jedis.setnx("k5","Jane");
        System.out.println("k5 setnx==>" + jedis.get("k5"));

        //12, mset k1 v1 k2 v2 : 一次添加多个键值对
        jedis.mset("k7","v7", "k8","v8");
        System.out.println("k7==>" + jedis.get("k7") + "\n" + "k8==>" + jedis.get("k8"));

        //13, mget k1 k2 k3 : 一次获取多个值
        List<String> mgetKeys = jedis.mget("k1","k2","k3");
        System.out.println("mget k1 k2 k3  ==>" + mgetKeys);

        //14, msetnx k5 NFL k6  football : (mset if not exists)如果键不存在则进行添加，存在则不添加
        jedis.msetnx("k5","NFL", "k6","football");
        System.out.println("msetnx k5 k6==>" + jedis.mget("k5","k6") );  //结果，[xyzdefghijk, Lily]，k5,k6存在添加失败

        //15, getset k2  HelloRedis : 先获取k2的值，然后在改为HelloRedis
        String k2 = jedis.getSet("k2", "HelloRedis");
        System.out.println("getset former k2 ==>" + k2);
        System.out.println("getset present k2 ==> " + jedis.get("k2"));


        //16, del k1 : 删除键
        jedis.del("k1");   //Long del(String... keys) :形参为可变长度，可删除多个key
        jedis.del("k2", "k3");
        System.out.println("k1 exists ==>" + jedis.exists("k1"));




    }

}
