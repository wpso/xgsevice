package com.homingpigeon.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.homingpigeon.push.PushKeys;

/**
 * Servlet implementation class JPushInit
 */
public class JPushInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Logger logger = Logger.getLogger(this.getClass());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JPushInit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		PushKeys.apiKey = config.getInitParameter("push_apiKey");
		PushKeys.secretKey = config.getInitParameter("push_secretKey");
		logger.info("JPush--apiKey:"+PushKeys.apiKey);
		logger.info("JPush--secretKey:"+PushKeys.secretKey);
		logger.info("极光初始化成功！");
	}

	
}
