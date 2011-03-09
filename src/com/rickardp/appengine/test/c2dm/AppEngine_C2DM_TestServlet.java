package com.rickardp.appengine.test.c2dm;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AppEngine_C2DM_TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
