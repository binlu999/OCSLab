package com.bbg.myontimenotice.mail.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class MailHandlerServlet extends HttpServlet {

	@Override
    public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) 
            throws IOException { 
        Properties props = new Properties(); 
        Session session = Session.getDefaultInstance(props, null); 
        try {
        	
        	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    		
    		
    		//Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
            /*
    		List<BlobKey> blobKeys = blobs.get("myFile");
            */
			//MimeMessage message = new MimeMessage(session, req.getInputStream());
			//Enumeration headline = message.getAllHeaderLines();
			//headline.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
}
