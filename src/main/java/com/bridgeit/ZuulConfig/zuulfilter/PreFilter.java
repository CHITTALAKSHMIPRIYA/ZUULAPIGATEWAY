
package com.bridgeit.ZuulConfig.zuulfilter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ZuulConfig.Messages;
import com.bridgeit.ZuulConfig.RestPreCondition;
import com.bridgeit.ZuulConfig.Exception.TodoException;
import com.bridgeit.ZuulConfig.redis.RedisRepository;
import com.bridgeit.ZuulConfig.token.TokenGenerator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author LAKSHMI PRIYA
 * @since DATE:10-07-2018
 *        <p>
 *        <b>A POJO class with the user details.</b>
 *        </p>
 */

public class PreFilter extends ZuulFilter{
	@Autowired
	RestPreCondition restprecondition;
	@Autowired
	TokenGenerator tokenGen;
	@Autowired
	RedisRepository redisRepo;
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if(request.getRequestURI().startsWith("/zuulnote/note/")) {
			String token = request.getHeader("token");
	    if(token==null)
	    {
	    	try {
				throw new TodoException(Messages.get("103"));
			} catch (TodoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    String userId = tokenGen.parseJwt(token);
	    System.out.println("userId : "+userId);
	    if(userId==null)
	    {
	    	try {
				throw new TodoException(Messages.get("103"));
			} catch (TodoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	   String redisToken= redisRepo.getToken(userId);
	    if(redisToken!=null)
	    {
	    	ctx.addZuulRequestHeader("userId", userId);
	    	request.setAttribute("userId", userId);
	    	System.out.println(userId);
	    	return userId;
	    	}
	}
		return null;
}
}

