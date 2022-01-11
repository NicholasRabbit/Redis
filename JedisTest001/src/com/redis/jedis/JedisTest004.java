package com.redis.jedis;

import redis.clients.jedis.Jedis;

public class JedisTest004 {

    public static void main(String[] args) {

        /**
         * Set类型数据常用方法
         * Set属于无需不可重复集合类型，因此向一个set类型的键添加Set值的话，如果Set中元素相同则覆盖
         * */
        Jedis jedis = new Jedis("8.142.134.196", 6379);
        jedis.auth("$C&ayman1463852^");
        jedis.select(1);       //选择1号库，不影响0号库数据

        //



    }
}
