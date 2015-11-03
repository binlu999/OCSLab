package com.bbg.myontimenotice.mail.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbg.myontimenotice.data.dao.EmailMessageDAO;
import com.bbg.myontimenotice.data.model.EmailMessage;
import com.bbg.myontimenotice.util.velocity.VelocityHelper;

@Controller
@RequestMapping("/qpush")
public class EmailMessageQueueController {

	private static final Logger log = Logger
			.getLogger(EmailMessageQueueController.class.getName());

	//http://localhost:8888/mvc/qpush/queue-email-inbound/123
	@RequestMapping(value = "/queue-email-inbound/{key}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String getQueueEMailInbound(@PathVariable String key) {
		log.info("Received GET request key=" + key);

		return "SUCCESS";

	}

	@RequestMapping(value = "/queue-email-inbound/{key}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String postQueueEMailInbound(@PathVariable String key) throws Exception {
		log.info("Received POST request key=" + key);
		EmailMessage emailMessage = EmailMessageDAO.findByKey(key);
		if (emailMessage != null) {
			MimeMessage mimeMessage = EmailMessageDAO.extractMimeMessage(emailMessage);
			MimeMessageParser mimeMessageParser=new MimeMessageParser(mimeMessage);
			mimeMessageParser.parse();
			String from = mimeMessageParser.getFrom();
			String subject=mimeMessageParser.getSubject();
			emailMessage.setFromAddress(from);
			EmailMessageDAO.save(emailMessage);
			log.info("Found emailMessage by key " + key);
			
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

	        String msgBody1 = "We received your email subjected "+subject;
	        String msgBody = "Hello <br> You messmage is confirmed<hr>"+subject+"<hr> <img src=\"http://www.apache.org/images/feather.gif\"> ....";
	        

	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("ontimenotice@gmail.com", "Ontime Notice"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(from));
	            msg.setSubject("Your message is confirmed");
	            msg.setText(msgBody);
	            //msg.set
	            Transport.send(msg);
	           sendHTMLMail(from, subject);
	    
	            log.info("Acknowledgement is sent");
	        } catch (AddressException e) {
	            e.printStackTrace();
	        } catch (MessagingException e) {
	        	 e.printStackTrace();
	        }
			
		}
		return "SUCCESS";

	}

	private void sendHTMLMail(String toAddress, String subject) throws MessagingException, UnsupportedEncodingException {
		 String msgBody = "We received your email subjected "+subject;
		 
		VelocityContext context=new VelocityContext();
		context.put("subject", subject);
		msgBody=VelocityHelper.merge(context, "/vm/in_bounf_eamil_ack.vm");
		 
		log.info("Message body by velocity:"+msgBody);
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("ontimenotice@gmail.com", "Ontime Notice"));
        msg.addRecipient(Message.RecipientType.TO,
                         new InternetAddress(toAddress));
        msg.setSubject("Your message is confirmed");
        msg.setText(msgBody);
        
        String htmlBody = "Hello <br> You messmage is confirmed<hr>"+subject+"<hr> <img src=\"http://www.apache.org/images/feather.gif\"> ....";
        htmlBody=msgBody;
        byte[] attachmentData;  // ...

        Multipart mp = new MimeMultipart();

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");
        mp.addBodyPart(htmlPart);
/*
        MimeBodyPart attachment = new MimeBodyPart();
       // InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
        attachment.setFileName("manual.pdf");
        //attachment.setContent(attachmentDataStream, "application/pdf");
        mp.addBodyPart(attachment);
*/
        msg.setContent(mp);
        Transport.send(msg);
        log.info("Acknowledgement in HTML is sent");
		
	}

	private void sendHTMLeMail(String toAddress, String subject) throws MalformedURLException, EmailException {

		 // load your HTML email template
		  String htmlEmailTemplate = "Hello <br> You messmage is confirmed<hr>"+subject+"<hr> <img src=\"http://www.apache.org/images/feather.gif\"> ....";

		  // define you base URL to resolve relative resource locations
		  URL url = new URL("http://www.apache.org");

		  // create the email message
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		//  email.setHostName("mail.myserver.com");
		  email.addTo(toAddress);
		  email.setFrom("ontimenotice@gmail.com", "Ontime Notice");
		  email.setSubject("Test email with inline image");
		  
		  // set the html message
		  email.setHtmlMsg(htmlEmailTemplate);

		  // set the alternative message
		  email.setTextMsg("Your email client does not support HTML messages");

		  // send the email
		  email.send();
		
	}

}
