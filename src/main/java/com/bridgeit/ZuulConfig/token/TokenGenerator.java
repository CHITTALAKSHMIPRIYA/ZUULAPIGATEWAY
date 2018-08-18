
package com.bridgeit.ZuulConfig.token;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * @author LAKSHMI PRIYA
 * @since DATE:10-07-2018
 *        <p>
 *        <b>A POJO class with the user details.</b>
 *        </p>
 */
@Configuration
public class TokenGenerator {
	/**
	 * @param id
	 * @return
	 */
	public static String createToken(String id) {
		final String KEY = "lakshmi";
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Date startTime = new Date();
		Date expireTime = new Date(startTime.getTime() + (24 * 60 * 60 * 1000));

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(startTime).setExpiration(expireTime)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();
	}

	/**
	 * @param jwt
	 * @return
	 */
	public String parseJwt(String jwt) {
		final String KEY = "lakshmi";
		return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody().getId();
	}
}
