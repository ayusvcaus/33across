package com._33across.exercise.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com._33across.exercise.config.Config;
import com._33across.exercise.dto.Datum;
import com._33across.exercise.service.DataService;

@Controller
@RequestMapping(Config.version)
public class DataController {
	
	private static final Logger s_logger = LogManager.getLogger(DataController.class);

	@Autowired
	public DataService dataService;
	
	@RequestMapping(value=Config.pathProcessCcds1, method=RequestMethod.POST)
	public ResponseEntity<String> processCcds1(@RequestBody final String ccds1,  @RequestHeader(value=Config.headerApiKey, required=true) final String apiKey) throws Exception {
		if (apiKey==null || !apiKey.equals(Config.key)) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		try {
			s_logger.info("ccds1="+ccds1);
		    dataService.saveCcds1(ccds1);
		    s_logger.info("ccds1 saving succeded");
		    return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			s_logger.info("ccds1 saving failed " + e.getStackTrace());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=Config.pathProcessCcds2, method=RequestMethod.POST)
	public ResponseEntity<String> processCcds2(@RequestBody final String ccds2,  @RequestHeader(value=Config.headerApiKey, required=true) final String apiKey) throws Exception {
		if (apiKey==null || !apiKey.equals(Config.key)) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		try {
			s_logger.info("ccds2="+ccds2);
			dataService.saveCcds2(ccds2);
			s_logger.info("ccds2 saving succeded");
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			s_logger.info("ccds2 saving failed " + e.getStackTrace());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=Config.pathFetch, method=RequestMethod.POST)
	public ResponseEntity<List<Datum>> fetch(@RequestBody final String filter,  @RequestHeader(value=Config.headerApiKey, required=true) final String apiKey) throws Exception {
		if (apiKey==null || !apiKey.equals(Config.key)) {
			return new ResponseEntity<List<Datum>>(HttpStatus.FORBIDDEN);
		}
		s_logger.info("filter="+filter);
		try {
		    List<Datum> list=dataService.read(filter);
		    s_logger.info("read data succeded, size="+list.size());
		    return new ResponseEntity<List<Datum>>(list, HttpStatus.OK);
		} catch (Exception e) {
			s_logger.info("read data failed " + e.getStackTrace());
			return new ResponseEntity<List<Datum>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
