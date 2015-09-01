package com.bbg.myontimenotice;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MyontimenoticeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("OCS, notice on time");
		resp.getWriter().println("Hello, world");
	}
}
