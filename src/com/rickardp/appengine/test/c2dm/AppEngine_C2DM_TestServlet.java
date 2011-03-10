package com.rickardp.appengine.test.c2dm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AppEngine_C2DM_TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		StringBuilder data = new StringBuilder();

		data.append("registration_id=" + ServerConfig.DeviceRegistrationID);

		// Collapse key is for grouping messages and only the last sent message
		// with the same key going to be sent to the phone when the phone is
		// ready to get the message if its not from the beginning
		data.append("&collapse_key=test");

		// Here is the message we sending, key1 can be changed to what you whant
		// or if you whant to send more then one you can do (i think, not tested
		// yet), Testing is the message here.
		data.append("&data.key1=Testing");

		// If you whant the message to wait to the phone is not idle then set
		// this parameter
		// data.append("&delay_while_idle=1");

		byte[] postData = data.toString().getBytes("UTF-8");

		StringBuilder sb = new StringBuilder();

		sb.append(ServerConfig.AuthToken + " - ");

		try {
			// Send data
			URL url = new URL("https://android.clients.google.com/c2dm/send");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("Content-Length",
					Integer.toString(postData.length));
			conn.setRequestProperty("Authorization", "GoogleLogin auth="
					+ ServerConfig.AuthToken);

			OutputStream out = conn.getOutputStream();
			out.write(postData);
			out.close();

			Integer responseCode = conn.getResponseCode();
			if (responseCode.equals(503)) {
				// the server is temporarily unavailable
				sb.append("responseCode = " + responseCode);
			} else {
				if (responseCode.equals(401)) {
					// AUTH_TOKEN used to validate the sender is invalid
					sb.append("responseCode = " + responseCode);
				} else {
					if (responseCode.equals(200)) {

						// Check for updated token header
						String updatedAuthToken = conn
								.getHeaderField("Update-Client-Auth");
						if (updatedAuthToken != null) {
							ServerConfig.AuthToken = updatedAuthToken;
							sb.append("updatedAuthToken = \""
									+ updatedAuthToken + "\"");
						}

						String responseLine = new BufferedReader(
								new InputStreamReader(conn.getInputStream()))
								.readLine();

						if (!sb.toString().equals("")) {
							sb.append(" - ");
						}

						if (responseLine == null || responseLine.equals("")) {
							sb.append("Got responsecode "
									+ responseCode
									+ " but a empty response from Google AC2DM server");
						} else {
							sb.append(responseLine);
						}
					} else {
						sb.append("responseCode = " + responseCode);
					}
				}
			}
		} catch (Exception e) {
			if (!sb.toString().equals("")) {
				sb.append(" - ");
			}

			sb.append("Exception: " + e.toString());
		}

		resp.getWriter().println(sb.toString());
	}

}
