package com.OneTech.common.util.redis;

import com.OneTech.common.util.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @description redis操作工具类，此工具类内数据类型都为String
 * @date 2019年1月10日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Component
public class StringRedisTemplateUtil{
    
    private static Logger logger = LoggerFactory.getLogger(StringRedisTemplateUtil.class);
    
    @Autowired
    StringRedisTemplate redisTemplate;   
	
	private static StringRedisTemplate stringRedisTemplate;
	
	@PostConstruct
	public void init() {
		stringRedisTemplate = redisTemplate;
	}
	
	/**
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static void set(String key,String value,Integer seconds){
		try {
			if(seconds > 0) {
				stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
			}else {
				stringRedisTemplate.opsForValue().set(key, value);
			}
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
	
	}
	
	/**
	 * 检查指定key是否存在
	 * @param key
	 * @return true存在  false不存在
	 * 
	 */
	public static Boolean exists(String key) {
	    try {
	    	return stringRedisTemplate.hasKey(key);
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
	    return false;
	
	}
	
	/**
	 * 如果不存在，则插入
	 * @param key
	 * @param value
	 * @return 是否插入成功
	 */
	public static Boolean setIfAbsent(String key,String value, Integer seconds){
	    try {
	    	if(BooleanUtils.isNotEmpty(seconds)) {
				expire(key, seconds);
			}
	    	return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
	    return false;
	}
	
	/**
	 * 获取指定key的值
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		try {			    
			return stringRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {	
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	
	}
	
	/**
	 * 判断redis hashkey是否存在 
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key,String field) {
		try {
			return stringRedisTemplate.opsForHash().hasKey(key, field);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return false;
	}	
	
	/**
	 * hset
	 * @param key
	 * @param hashKey
	 * @param timeOutSeconds 过期时间
	 * @param value
	 */
	public static void hset(String key,String hashKey,Integer timeoutSeconds,Object value) {
		try {
        	if(BooleanUtils.isNotEmpty(timeoutSeconds)) {
        		stringRedisTemplate.expire(hashKey, timeoutSeconds, TimeUnit.SECONDS);
        	}
        	stringRedisTemplate.opsForHash().put(key, hashKey, value);         
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
	}
	
	/**
	 * 根据field返回map数据
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static String hget(String key,Object hashKey) {
		try {
			Object temp = stringRedisTemplate.opsForHash().get(key, hashKey);
			if(BooleanUtils.isNotEmpty(temp)) {
				return temp.toString();
			}
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}	
		return null;
	}	
	
	/**
	 * 根据key获取redis中对应的map数据
	 * @param key
	 * @return
	 */
	public static Map<Object, Object> hgetAll(String key) {
		try {
		    return stringRedisTemplate.opsForHash().entries(key);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	}
	
	/**
	 * 将map设置到redis指定key中
	 * @param key
	 * @param hash
	 */
	public static void hmset(String key,Map<String, String>hash, Integer seconds) {
		try {
			if(BooleanUtils.isNotEmpty(seconds)) {
				expire(key, seconds);
			}
		    stringRedisTemplate.opsForHash().putAll(key, hash);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
	}
	
	/**
	 * 根据fields数组获取redis指定key对应map中的数据
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<Object> hmget(String key,Object... fields) {
		try {
			return stringRedisTemplate.opsForHash().multiGet(key, Arrays.asList(fields));
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	}
	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
	 * @param key 
	 * @param long
	 * 
	 */
	public static Long rpush(String key,String...values) {
		try {			    
			return stringRedisTemplate.opsForList().rightPushAll(key, Arrays.asList(values));
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}		
		return null;
	}
	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
	 * 当 key 不存在时， RPUSHX 命令什么也不做
	 * @param key 
	 * @param long
	 * 
	 */
	public static Long rpushx(String key,String value) {
		try {			    
			return stringRedisTemplate.opsForList().leftPushIfPresent(key, value);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	}
	
	/**
	 * 移除表头中的元素
	 */
	public static String lpop(String key) {
		try {
			return stringRedisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	}
	
	/**
	 * 当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止
	 * 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
	 * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值
	 */
	public static String blpop(String key,Long timeout) {
		try {
			return stringRedisTemplate.opsForList().leftPop(key, timeout, TimeUnit.SECONDS);
		} catch (Exception e) {			
			logger.error("操作redis异常，{}",e);
		}			
		return null;
	}
	
	/**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * @param key
     * @param count 
     * <br/>
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。<br/>
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。<br/>
     * count = 0 : 移除表中所有与 value 相等的值
     * @param value需要移除的值
     */
    public static Long lrem(String key,long count, Object value) {
        try {
            return stringRedisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;
    
    }
	
	public static Long llen(String key) {

		try {
			return stringRedisTemplate.opsForList().size(key);
		}catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;		
	}	
	
	/**
	 * 设置key的过期时间
	 * @param key 
	 * @param seconds 过期的秒
	 * @return 
	 * 
	 */
	public static Boolean expire(String key,Integer seconds) {
		try {
		    return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
		} catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return false;
	}
	/**
     * 删除指定的field
     * @param key 
     * 
     */
    public static Long hdel(String key,Object... hashKeys) {
        try {
        	return stringRedisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;          
    }
	
	/**
	 * 删除指定的key
	 * @param key 
	 * 
	 */
	public static Boolean del(String key) {
		try {
			return stringRedisTemplate.delete(key);
		}catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return false;    			
	}
		
	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 * @param key 需要查询的key
	 * @param start 数 start 和 stop 都以 0 为底，，以 0 表示列表的第一个元素
	 * @param end 以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素
	 * @return Long field的数量 
	 * 
	 */
	public static List<String> lrange(String key,long start,long end) {
		try {
		    return  stringRedisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;  			
	}
	
	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * @param key 需要查询的key
	 * @return Long 当 key 不存在时，返回 -2 
	 * 当 key 存在但没有设置剩余生存时间时，返回 -1 	
	 * 否则，以秒为单位，返回 key 的剩余生存时间
	 */
	public static Long ttl(String key) {
		try {
			return stringRedisTemplate.getExpire(key);
		} catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;  			
	}
	
	/**
	 * 将 key 所储存的值加上增量 increment
	 * @param key 需要查询的key
	 * @param increment 增量值
	 * @return Long加上 increment 之后， key 的值
	 */
	public static Long incrby(String key,long increment) {
		try {
			return stringRedisTemplate.opsForValue().increment(key, increment);
		} catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null; 			
	}	
	
	/**
     * 将 key 所储存的值减去增量 increment
     * @param key 需要查询的key
     * @param increment 增量值
     * @return Long 减去increment 之后， key 的值
     */
    public static Long decrby(String key,long increment) {
        try {
            return stringRedisTemplate.opsForValue().increment(key, -increment);
        } catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
        return null;            
    }  
    
    
    /**
     * 将 key 所储存的值减去增量 increment
     * @param key 需要查询的key
     * @param increment 增量值
     * @return Long 减去increment 之后， key 的值
     */
    public static void setMap(String key,Map<String,String> map,Integer seconds) {
		try {
			if(BooleanUtils.isNotEmpty(seconds)) {
				expire(key, seconds);
			}
			stringRedisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {         
            logger.error("操作redis异常，{}",e);
        }     
	}  
    
    /**
     * 根据key获取list中的所有数据
     * @param key
     * @return
     */
    public static List<String> getAllList(String key) {
    	try {
    		return stringRedisTemplate.opsForList().range(key, 0, -1);
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
    	return null;
    }
    
    
    /**
     * 将list设置到redis对应key中
     * @param key
     * @param list
     */
    public static Long setList(String key, List<String> list, Integer seconds) {
    	try {
    		if(BooleanUtils.isNotEmpty(seconds)) {
    			expire(key, seconds);
    		}
			return stringRedisTemplate.opsForList().rightPushAll(key, list);
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
    	return null;
    }
    
	/**
	 * 重置list
	 * @param key
	 * @param list
	 * @param seconds 过期时间
	 * @return
	 */
	public static Long resetList(String key,List<String> list,Integer seconds){
		try {
			boolean delFlag = del(key);
			logger.info("delFlag: " + delFlag);
			if(BooleanUtils.isNotEmpty(list)) {
				setList(key, list,seconds);
			}
		} catch (Exception e) {
			logger.error("操作redis异常，{}",e);
		}
		return null;
	}
    
    
    
    
    
}
