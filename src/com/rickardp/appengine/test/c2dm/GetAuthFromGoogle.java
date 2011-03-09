package com.rickardp.appengine.test.c2dm;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetAuthFromGoogle extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		StringBuilder url = new StringBuilder();
		
		url.append("https://www.google.com/accounts/ClientLogin");
		url.append("?Email=" + URLEncoder.encode("yourgoogleaccount", "UTF-8"));
		url.append("&Passwd=" + URLEncoder.encode("yourpassword", "UTF-8"));
		url.append("&accountType=GOOGLE");
		url.append("&source=Test-C2DM");
		url.append("&service=ac2dm");
		
		String result = download(url.toString());
		
		resp.getWriter().println(result);
	}
	
	public static String download(String url) throws java.io.IOException {
		java.io.InputStream s = null;
		java.io.InputStreamReader r = null;
		StringBuilder content = new StringBuilder();
		try {
			s = (java.io.InputStream) new URL(url).getContent();

			r = new java.io.InputStreamReader(s, "UTF-8");

			char[] buffer = new char[4 * 1024];
			int n = 0;
			while (n >= 0) {
				n = r.read(buffer, 0, buffer.length);
				if (n > 0) {
					content.append(buffer, 0, n);
				}
			}
		} finally {
			if (r != null)
				r.close();
			if (s != null)
				s.close();
		}
		return content.toString();
	}
}
