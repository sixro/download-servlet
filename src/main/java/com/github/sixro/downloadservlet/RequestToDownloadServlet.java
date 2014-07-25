package com.github.sixro.downloadservlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.*;

/**
 * Represents a servlet able to return a content as a download on the browser.
 * 
 * <p>
 * If you have a frontend that has to return a CSV file, you can simply call the URL of this servlet asking to
 * &quot;produce&quot; a download with specified {@code mime type} (e.g. {@code text/csv}) and {@code filename}.<br />
 * The content has to be provided with the parameter {@code content}.
 * </p>
 * 
 * <p>
 * PAY ATTENTION: there are limits on the size of the content. Every webserver has its owns parameter to configure the limits.
 * For Tomcat see {@code maxPostSize} configuration parameter <a href="http://tomcat.apache.org/tomcat-7.0-doc/config/ajp.html" >here</a>.
 * </p>
 * 
 * @author <a href="mailto:me@sixro.net" >Sixro</a>
 */
@SuppressWarnings("serial")
public class RequestToDownloadServlet extends HttpServlet {

	private static final Log LOG = LogFactory.getLog(RequestToDownloadServlet.class);
	
	private static final String PARAMETER_MIME_TYPE = System.getProperty(RequestToDownloadServlet.class.getPackage().getName() + ".parameter.mimeType", "mimeType");
	private static final String PARAMETER_FILENAME = System.getProperty(RequestToDownloadServlet.class.getPackage().getName() + ".parameter.filename", "filename");
	private static final String PARAMETER_CONTENT = System.getProperty(RequestToDownloadServlet.class.getPackage().getName() + ".parameter.content", "content");
	
	private static final String HEADER_CONTENT_DISPOSITION = "Content-disposition";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mimeType = req.getParameter(PARAMETER_MIME_TYPE);
		String filename = req.getParameter(PARAMETER_FILENAME);
		String content = req.getParameter(PARAMETER_CONTENT);
		
		LOG.debug("[doPost] mimeType: " + mimeType + ", filename: " + filename + ", content size: " + content.length());

		resp.setContentType(mimeType);
		resp.setHeader(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + filename);

		PrintWriter writer = resp.getWriter();
		IOUtils.copy(new StringReader(content), writer);
		writer.flush();
	}
	
}
