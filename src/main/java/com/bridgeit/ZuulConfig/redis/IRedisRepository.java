
package com.bridgeit.ZuulConfig.redis;

import io.jsonwebtoken.Claims;

/**
 * @author LAKSHMI PRIYA
 * @since DATE:10-07-2018
 *        <p>
 *        <b>A POJO class with the user details.</b>
 *        </p>
 */

public interface IRedisRepository {

	/**
	 * @param jwtToken
	 */
	void setToken(String jwtToken);

	

	/**
	 * @param claim
	 */
	void deleteToken(Claims claim);

	/**
	 * @param userIds
	 * @return
	 */
	String getToken(String userIds);
}
