package com.fakeapi.controller;

import java.util.HashMap;

import org.json.XML;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@EnableAutoConfiguration
@ComponentScan("com")
public class SampleController {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseMessage get() {
		ResponseMessage responseMessage = new ResponseMessage("rest-example", "Hello World!");
		return responseMessage;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseMessage json() {
		ResponseMessage responseMessage = new ResponseMessage("json-example", "Hello World JSON!");
		return responseMessage;
	}

	@RequestMapping(value = "/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseMessage xml() {
		ResponseMessage responseMessage = new ResponseMessage("xml-example", "Hello World XML!");
		return responseMessage;
	}

	@RequestMapping(value = "/xml-to-json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseMessage xmlToJson(@RequestBody HashMap<String,String> requestMap) {
		ResponseMessage responseMessage = new ResponseMessage("response", XML.toJSONObject(requestMap.get("request")).toString());
		return responseMessage;
	}
}
