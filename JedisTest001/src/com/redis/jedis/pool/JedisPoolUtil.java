package com.redis.jedis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPoolUtil工具类范例，在这里设置连接池，从连接池里获取连接，进行redis相关操作
 * 这里把RedicConfig和JedisUtil结合起来了，实际生产中是分开的
 *
 * 1,JedisPoolUtil个工具,使用双重验证的方式创建一个单例类JedisPool，在这里创建一个Redis连接池
 *
 * */
public class JedisPoolUtil {

    //Redis连接池对象，使用volatile修饰符，保证jedisPool对象的内存可见性，保证高并发下的事务的一致性
    private static volatile JedisPool jedisPool = null;
    //单例模式，构造函数要私有化
    private JedisPoolUtil(){

    }

    public static JedisPool getJedisPoolInstance(){
        //使用双重验证
        if(null == jedisPool){
            JedisPoolConfig  jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxActive(1000);   //设置连接池最多活跃线程数，即最多连接数
            jedisPoolConfig.setMaxIdle(32);    //设置最多限制的线程数
            jedisPoolConfig.setMaxWait(100*1000);  //设置最大的等待数量，如果超过此数量，再连接redis会报异常
            jedisPoolConfig.setTestOnBorrow(true);  //设置连接前进行测试是否联通
            //设置连接IP,端口，等待超时时间(单位毫秒)，密码
            jedisPool = new JedisPool(jedisPoolConfig,"8.142.134.196",6379,1000*60*5,"$C&ayman1463852^");

        }
        return jedisPool;
    }

    //使用完线程池的连接，调用此方法释放，即归还连接
    public static void  release(JedisPool jedisPool, Jedis jedis){
        if(null != jedis){
            jedisPool.returnResourceObject(jedis);
        }
    }

}
