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
        Jedis  jedis = new Jedis("192.168.145.128", 6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        jedis.set("k1", "Tom");
        jedis.set("k2", "Jerry");
        jedis.set("k3", "MacJones");   //这里存完之后，去Linux执行redis-cli查看即可看到数据已添加进去了。
        System.out.printf("%s\n%s\n%s",jedis.get("k1"), jedis.get("k2"), jedis.get("k2"));
    }

}
