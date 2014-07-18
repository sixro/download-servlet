package com.github.sixro.downloadservlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.*;

@SuppressWarnings("serial")
public class RequestToDownloadServlet extends HttpServlet {

	private static final Log LOG = LogFactory.getLog(RequestToDownloadServlet.class);
	
	private static final String PARAMETER_MIME_TYPE = "mimeType";
	private static final String PARAMETER_FILENAME = "filename";
	private static final String HEADER_CONTENT_DISPOSITION = "Content-disposition";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mimeType = req.getParameter(PARAMETER_MIME_TYPE);
		LOG.debug("mimeType: " + mimeType);
		resp.setContentType(mimeType);
		
		String filename = req.getParameter(PARAMETER_FILENAME);
		LOG.debug("filename: " + filename);
		resp.setHeader(HEADER_CONTENT_DISPOSITION, "attachment; filename=" + filename);

		String content = req.getParameter("content");
		LOG.debug("content: " + content);

		StringReader reader = new StringReader(content);
		PrintWriter writer = resp.getWriter();
		IOUtils.copy(reader, writer);
		writer.flush();
	}
	
}
