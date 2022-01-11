package com.redis.jedis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 这里使用Redis连接池获取Jedis对象，进行连接操作Redis库
 * */
public class JedisPoolTest {

    public static void main(String[] args) {

        //获取JedisPool对象，并验证它是否是单例的
        JedisPool  jedisPool = JedisPoolUtil.getJedisPoolInstance();
        JedisPool  jedisPool02 = JedisPoolUtil.getJedisPoolInstance();
        System.out.println(jedisPool == jedisPool02);  //结果为true，说明两次获取的是同一个对象

        //通过JedisPool对象的gerResource()方法获取一个Jedis对象，即一个Redis库的链接
        //要用try,catch捕捉肯能链接不上或超时等异常
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            System.out.println(jedis.ping() + ":dbsize" + jedis.dbSize());
            jedis.select(1);
            jedis.set("k11", "v11");
            jedis.set("k22", "v22");
            System.out.println("k11==>" + jedis.get("k11"));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //用完链接后在这里归还
            jedisPool.returnResource(jedis);
        }






    }
}
