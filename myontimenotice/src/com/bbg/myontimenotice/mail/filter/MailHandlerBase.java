package com.bbg.myontimenotice.mail.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.bbg.myontimenotice.data.dao.EmailMessageDAO;
import com.bbg.myontimenotice.data.model.EmailMessage;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public abstract class MailHandlerBase implements Filter {

	private Pattern pattern = null;

	protected MailHandlerBase(String pattern) {
		if (pattern == null || pattern.trim().length() == 0) {
			throw new IllegalArgumentException(
					"Expected non-empty regular expression");
		}
		this.pattern = Pattern.compile("/_ah/mail/" + pattern);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * Process the message. A message will only be passed to this method if the
	 * servletPath of the message (typically the recipient for appengine)
	 * satisfies the pattern passed to the constructor. If the implementation
	 * returns false, control is passed to the next filter in the chain. If the
	 * implementation returns true, the filter chain is terminated.
	 *
	 * The Matcher for the pattern can be retrieved via getMatcherFromRequest
	 * (e.g. if groups are used in the pattern).
	 */
	protected abstract boolean processMessage(HttpServletRequest req,
			HttpServletResponse res) throws ServletException;

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) sreq;
		HttpServletResponse res = (HttpServletResponse) sres;

		MimeMessage message = getMessageFromRequest(req);
		Matcher m = applyPattern(req);

		if (m != null && processMessage(req, res)) {
			return;
		}

		chain.doFilter(req, res); // Try the next one

	}

	private Matcher applyPattern(HttpServletRequest req) {
		String reqpath = req.getServletPath();
		reqpath=reqpath.replaceAll("/_ah/mail","");
		if(reqpath.length()==0){
			reqpath="/_ah/mail/default";
		}
		Matcher m = pattern.matcher(reqpath);
		if (!m.matches())
			m = null;

		req.setAttribute("matcher", m);
		return m;
	}

	protected Matcher getMatcherFromRequest(ServletRequest req) {
		return (Matcher) req.getAttribute("matcher");
	}

	protected MimeMessage getMessageFromRequest(HttpServletRequest req)
			throws ServletException {
		
			try {
				EmailMessage emailMessage = EmailMessageDAO.create(req.getInputStream());
				emailMessage.setName(req.getRequestURI());
				EmailMessageDAO.save(emailMessage);
				
				Queue queue = QueueFactory.getQueue("queue-email-inbound");
				queue.add(TaskOptions.Builder.withUrl("/mvc/qpush/queue-email-inbound/"+emailMessage.getKeyString()).method(TaskOptions.Method.POST));
				
			} catch (MessagingException e) {
				throw new ServletException("Error processing inbound message",
						e);
			} catch (IOException e) {
				throw new ServletException("Error processing inbound message",
						e);
			}
	
		return null;
	}

}