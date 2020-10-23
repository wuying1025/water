package com.water.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.water.exception.TokenException;

@Component
public class RedisUtil {

	private Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	/**
	 * redisTemplate 缓存对象
	 */
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	// **********************************public**********************************

	/**
	 * 批量删除
	 * 
	 * @param keys
	 * @return
	 */
	public Boolean del(String... keys) {
		try {
			Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
			redisTemplate.delete(kSet);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查询key是否存在
	 * 
	 * @param keys
	 * @return
	 */
	public Boolean hasKey(String key) {
		try {
			redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param time 秒
	 * @return
	 */
	public Boolean expireKey(String key, Long time) {
		try {
			redisTemplate.expire(key, time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 设置key在指定时间过期
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public Boolean expireKeyAt(String key, Date date) {
		try {
			redisTemplate.expireAt(key, date);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查询key过期时间（秒）
	 * 
	 * @param key
	 * @return
	 */
	public Long getKeyExpire(String key) {
		try {
			return redisTemplate.getExpire(key, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// **********************************String**********************************

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Set<String> get() {
		try {
			return redisTemplate.keys("*");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean set(String key, String value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 添加及设置超期时间
	 *
	 * @param key
	 * @param value
	 * @param expireTime 超期时间
	 * @return
	 */
	public Boolean setExpire(String key, String value,long expireTime) {
		try {
			redisTemplate.opsForValue().set(key, value,expireTime,TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 添加
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean setnx(String key, String value) {
		try {
			redisTemplate.opsForValue().setIfAbsent(key,value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @return
	 */
	public Boolean del(String key) {
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// **********************************Hash**********************************

	/**
	 * 获取
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hget(String key, String hashKey) {
		try {
			Object val = redisTemplate.opsForHash().get(key, hashKey);
			return val == null ? null : val;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean hset(String key, String hashKey, Object value) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 添加所有
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public Boolean hsetAll(String key, Map<? extends Object,? extends Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 删除
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Boolean hdel(String key, String hashKey) {
		try {
			redisTemplate.opsForHash().delete(key, hashKey);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取key对应的多有键值
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hget(String key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// **********************************Set**********************************

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> sget(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Boolean sset(String key, Object... values) {
		try {
			redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除值为value的
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Boolean sdel(String key, Object... values) {
		try {
			redisTemplate.opsForSet().remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// **********************************List**********************************

	/**
	 * 获取list缓存内容
	 * 
	 * @param key
	 * @param start 开始
	 * @param end   结束 0 到 -1 代表所有
	 * @return
	 */
	public List<Object> lget(String key, Long start, Long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * 
	 * @param key
	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lgetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lset(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 查询当前登录人员的全部信息
	 * @param prefix 前缀标识
	 * @return Map<Object, Object>
	 */
	public Map<Object, Object> getPerson(String prefix) {
		String username = getUsername();
		return hget(prefix+username);
	}
	
	/**
	 * 计数器
	 * @param key 缓存键
	 * @return
	 */
	public Long increment(String key) {
	  return redisTemplate.opsForValue().increment(key);
	}
	
	/**
	 * 查询当前登录人员的实体类对象
	 * @param <T>
	 * @param clazz 实体类类型
	 * @return T 用户或管理员实体
	 */
	public <T> T getPerson(Class<T> clazz) {
		
		T entity = null;
		
		String username = getUsername();
		
//		if(Account.class == clazz) {
//			Map<Object, Object> map = hget(RedisConstant.PREFIX_ADMIN+username);
//			entity = BeanUtil.mapToBean(map, clazz, CopyOptions.create());
//		} else if(Users.class == clazz) {
//			Map<Object, Object> map = hget(RedisConstant.PREFIX_SMALL+username);
//			entity = BeanUtil.mapToBean(map, clazz, CopyOptions.create());
//		}
		
		return entity;
	}
	
	/**
	 * 查询当前登录人员某个字段的信息
	 * @param <T>
	 * @param prefix 前缀标识
	 * @param hashKey 人员某个字段信息
	 * @param clazz 返回字段类型
	 * @return Object
	 */
	public <T> T getPersonByHashKey(String prefix,String hashKey,Class<T> clazz) {
		String username = getUsername();
		Object value = hget(prefix+username, hashKey);
		
		return clazz.cast(value);
	}
	
	/**
	 * 获取当前登录用户的用户名
	 * @return 登录用户的用户名
	 */
	public String getUsername() {
		
		String username = null;
		
		try {
			String JWTToken = JWTUtil.localToken.get();
			username = JWTUtil.getUsername(JWTToken);
		} catch (Exception e) {
			logger.error("获取token异常", e);
			throw new TokenException("获取token异常");
		}
		
		return username;
	}

}
