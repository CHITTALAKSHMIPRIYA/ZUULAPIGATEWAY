
package com.bridgeit.ZuulConfig.redis;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.ZuulConfig.token.TokenGenerator;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import io.jsonwebtoken.Claims;

/**
 * @author LAKSHMI PRIYA
 * @since DATE:10-07-2018
 *        <p>
 *        <b>A POJO class with the user details.</b>
 *        </p>
 */
@Repository
public class RedisRepository implements IRedisRepository {
	@Autowired
	TokenGenerator token;

	private RedisTemplate<String, Object> redisTemplate;
	private static HashOperations<String, String, String> hashOperations;
	private static String KEY = "lakshmi";

	@Autowired
	public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(RedisRepository.class);

	/**
	 * To initialize hash operations and this method MUST be invoked before the
	 * class is put into service.
	 */
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * @param clientId
	 * @param jwtToken
	 */
	@Override
	public void setToken(String jwttoken) {
		//String claim = token.parseJwt(jwttoken).getId();
		String userId = token.parseJwt(jwttoken);
		hashOperations.put(KEY, userId, jwttoken);
		logger.info("Token set in redis");
	}

	/**
	 * @param clientId
	 * @return token
	 */
	@Override
	public String getToken(String userId) {
		logger.info("Getting token from redis");
		return hashOperations.get(KEY, userId);
	}

	/**
	 * @param clientId
	 */
	@Override
	public void deleteToken(Claims claim) {
		logger.info("Deleting token from redis");
		hashOperations.delete(KEY, claim);
	}
}
