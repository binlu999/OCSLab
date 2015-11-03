package com.bbg.lab.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/lab_mvc")
public class GeneralMapController {
	private final Logger log = Logger.getLogger(this.getClass().getName());
	private Map<String, Object> mapValue=new HashMap<String,Object>();


	//http://localhost:8888/mvc/lab_mvc/getmap
	@RequestMapping(value = "/getmap", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Map<String,Object> getMapValue() throws Exception {
		log.info("Received POST map");

		return this.mapValue;
	}

	@RequestMapping(value = "/putmap", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> postMapValue(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		log.info("Received POST map");
		this.mapValue=inputMap;
		return inputMap;
	}
}
