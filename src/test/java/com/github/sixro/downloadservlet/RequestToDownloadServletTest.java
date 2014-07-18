package com.github.sixro.downloadservlet;

import static org.junit.Assert.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

public class RequestToDownloadServletTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	private HttpServletRequest request = context.mock(HttpServletRequest.class);
	private HttpServletResponse response = context.mock(HttpServletResponse.class);
	private RequestToDownloadServlet servlet = new RequestToDownloadServlet();

	@Test public void write_the_request_body_in_output_setting_contenttype_and_disposition_using_request_input() throws ServletException, IOException {
		final String csv = "a,b\n1,2";
		final StringWriter output = new StringWriter();
		
		context.checking(new Expectations() {{
			oneOf(request).getParameter("mimeType");
				will(returnValue("text/csv"));
			oneOf(response).setContentType("text/csv");

			oneOf(request).getParameter("filename");
				will(returnValue("example1.csv"));
			oneOf(response).setHeader("Content-disposition", "attachment; filename=example1.csv");

			oneOf(request).getParameter("content");
				will(returnValue(csv));
			
			oneOf(response).getWriter();
				will(returnValue(new PrintWriter(output)));
		}});
		
		servlet.doPost(request, response);

		assertEquals(csv, output.toString());
	}

}
