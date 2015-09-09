package com.bbg.myontimenotice.util.velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityHelper {
	
	private static VelocityEngine velocityEngine;
	
	public static VelocityEngine getVelocityEngine(){
		if(velocityEngine==null){
			init();
		}
		return velocityEngine;
	}

	private static void init() {
		velocityEngine = new VelocityEngine();
	    Properties velocityProperties = new Properties();
	    velocityProperties.put("resource.loader", "class");
	    velocityProperties.put("class.resource.loader.description", "Velocity Classpath Resource Loader");
	    velocityProperties.put("class.resource.loader.class",
	        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    velocityEngine.init(velocityProperties);
		
	}
	
	public static String merge(VelocityContext context,String templateName){	
		VelocityEngine ve = getVelocityEngine();
		Template template = ve.getTemplate(templateName);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}

}
