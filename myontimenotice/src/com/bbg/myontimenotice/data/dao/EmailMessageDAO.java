package com.bbg.myontimenotice.data.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletInputStream;

import org.apache.commons.io.IOUtils;

import com.bbg.myontimenotice.data.PMF;
import com.bbg.myontimenotice.data.model.EmailMessage;
import com.bbg.myontimenotice.mail.filter.HandleDiscussionEmail;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EmailMessageDAO {

	private static final Logger log = Logger.getLogger(EmailMessageDAO.class
			.getName());

	public EmailMessageDAO() {

	}

	public static EmailMessage save(EmailMessage emailMessage) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(emailMessage);
			Key key = emailMessage.getKey();
			String id = KeyFactory.keyToString(key);
			log.info("New " + EmailMessage.class.getName()
					+ "created with key " + id);
		} finally {
			pm.close();
		}

		return emailMessage;
	}

	public static EmailMessage create(InputStream inputStream)
			throws IOException, MessagingException {
		EmailMessage emailMessage = new EmailMessage();
		byte[] messageBytes = IOUtils.toByteArray(inputStream);
		Blob blob = new Blob(messageBytes);
		emailMessage.setMessageBlob(blob);

		return emailMessage;

	}

	public static MimeMessage extractMimeMessage(EmailMessage emailMessage)
			throws MessagingException {
		if (emailMessage == null || emailMessage.getMessageBlob() == null) {
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				emailMessage.getMessageBlob().getBytes());
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session, byteArrayInputStream);
		return message;
	}

	public static EmailMessage findByKey(String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EmailMessage emailMessage = null;
		try {

			emailMessage = pm.getObjectById(EmailMessage.class, key);

		} finally {
			pm.close();
		}

		return emailMessage;

	}

}
