package com.bm.lobby.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

@Configuration
public class LobbyConfiguration {

    @Value("${lobby.redis.database}")
    private int database;

    @Value("${lobby.redis.host}")
    private String host;

    @Value("${lobby.redis.maxIdle}")
    private int maxIdle;

    @Value("${lobby.redis.maxTotal}")
    private int maxTotal;

    @Value("${lobby.redis.maxWaitMillis}")
    private long maxWaitMillis;

    @Value("${lobby.redis.password}")
    private String password;

    @Value("${lobby.redis.port}")
    private int port;

    @Value("${lobby.redis.timeout}")
    private int timeout;

    @Value("${lobby.redis.usePool}")
    private boolean usePool;

    @Value("${lobby.wx.url}")
    private String wxUrl;

    @Value("${lobby.wx.appid}")
    private String wxAppid;

    @Value("${lobby.wx.appscret}")
    private String wxAppscret;

    @Value("${lobby.qq.url}")
    private String qqUrl;

    @Value("${lobby.qq.appid}")
    private String qqAppid;

    @Value("${lobby.qq.appscret}")
    private String qqAppscret;

    @Value("${lobby.token.valid.time:2592000}")
    private long token_valid_time;

    @Value("${lobby.player.startid:1000000}")
    private long playerStartId;

    @Value("${lobby.checkin.waitime:180}")
    private long checkInWaitime;

    @Bean
    @Primary
    public RestTemplate createRestTemplate() throws Exception {
        return new RestTemplate();
    }

    @Bean
    public StringRedisTemplate getRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config());
        connectionFactory.setDatabase(database);
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsePool(usePool);
        connectionFactory.setTimeout(timeout);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public JedisPoolConfig config() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isUsePool() {
        return usePool;
    }

    public void setUsePool(boolean usePool) {
        this.usePool = usePool;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxAppscret() {
        return wxAppscret;
    }

    public void setWxAppscret(String wxAppscret) {
        this.wxAppscret = wxAppscret;
    }

    public String getQqUrl() {
        return qqUrl;
    }

    public void setQqUrl(String qqUrl) {
        this.qqUrl = qqUrl;
    }

    public String getQqAppid() {
        return qqAppid;
    }

    public void setQqAppid(String qqAppid) {
        this.qqAppid = qqAppid;
    }

    public String getQqAppscret() {
        return qqAppscret;
    }

    public void setQqAppscret(String qqAppscret) {
        this.qqAppscret = qqAppscret;
    }

    public long getToken_valid_time() {
        return token_valid_time;
    }

    public void setToken_valid_time(long token_valid_time) {
        this.token_valid_time = token_valid_time;
    }

    public long getPlayerStartId() {
        return playerStartId;
    }

    public void setPlayerStartId(long playerStartId) {
        this.playerStartId = playerStartId;
    }

    public long getCheckInWaitime() {
        return checkInWaitime;
    }

    public void setCheckInWaitime(long checkInWaitime) {
        this.checkInWaitime = checkInWaitime;
    }
}
