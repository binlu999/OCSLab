package com.bbg.myontimenotice.mail.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

public class HandleDiscussionEmail extends MailHandlerBase {

	private static final Logger log = Logger
			.getLogger(HandleDiscussionEmail.class.getName());

	public HandleDiscussionEmail() {
		super("default");
	}

	@Override
	protected boolean processMessage(HttpServletRequest req,
			HttpServletResponse res) throws ServletException {

		return false;
	}

}
