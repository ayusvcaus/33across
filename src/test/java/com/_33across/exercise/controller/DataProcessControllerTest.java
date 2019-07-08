package com._33across.exercise.controller;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;

import com._33across.exercise.controller.DataController;
import com._33across.exercise.config.Config;
import com._33across.exercise.dto.Datum;
import com._33across.exercise.service.DataService;
import com._33across.exercise.util.DataHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:exercise-applicationContext.xml"})
public class DataProcessControllerTest {
	
	public String eu="AT,BE,BG,CY,CZ,DE,DK,EE,ES,FI,FR,GB,GR,HR,HU,IE,IT,LT,LU,LV,MT,NL,PO,PT,RO,SE,SI,SK";
	
	@InjectMocks
	@Autowired
	public DataController controller;

	@Mock
	public DataService dataService;

	public MockMvc mockMvc;
	
	@Before
	public void initMockMvc() throws Exception { 
	    MockitoAnnotations.initMocks(this);
	    mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testProcessCcds1() throws Exception {
		 //case: invalid key
		String ccds1="xxxx";
		mockMvc.perform(post(Config.version+Config.pathProcessCcds1)
	    		 .header(Config.headerApiKey, Config.key+"0")
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds1)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isForbidden());
		 
	      //case: success
	      doNothing().when(dataService).saveCcds1(ccds1);      
	      mockMvc.perform(post(Config.version+Config.pathProcessCcds1)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds1)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isCreated())
		          .andExpect(content().string(""));
	      
          //case: failed
	      doThrow(new RuntimeException()).when(dataService).saveCcds1(ccds1);
	      mockMvc.perform(post(Config.version+Config.pathProcessCcds1)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds1)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isInternalServerError());
	 }
	 
	 @Test
	 public void testProcessCcds2() throws Exception {
		  //case: invalid key
		 String ccds2="xxxx";
		 mockMvc.perform(post(Config.version+Config.pathProcessCcds2)
	    		  .header(Config.headerApiKey, Config.key+"0")
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds2)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isForbidden());
		 
	      //case: success
	      doNothing().when(dataService).saveCcds2(ccds2);      
	      mockMvc.perform(post(Config.version+Config.pathProcessCcds2)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds2)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isCreated())
		          .andExpect(content().string(""));
	      
          //case: failed
	      doThrow(new RuntimeException()).when(dataService).saveCcds2(ccds2);
	      mockMvc.perform(post(Config.version+Config.pathProcessCcds2)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(ccds2)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isInternalServerError());
	 }
	 
	 @Test
	 public void testFetch() throws Exception {
		 
		 //case: invalid key
		 mockMvc.perform(post(Config.version+Config.pathFetch)
	    		  .header(Config.headerApiKey, Config.key+"0")	    		  
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content("xxxx")    		  
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isForbidden());
		 
	     //case: success
		 String ccds1=new String(Files.readAllBytes(Paths.get("data/ccds1.csv")));
		 String ccds2=new String(Files.readAllBytes(Paths.get("data/ccds2.tsv")));
		 
		 ObjectMapper mapper=new ObjectMapper();	 
		 String jsonEU=mapper.writeValueAsString(eu);
		 
		 Map<String, Datum> data=new HashMap<>();
		 DataHandler.parseCcds1(ccds1, data);
		 DataHandler.parseCcds2(ccds2, data);
		 Set<String> filter=Stream.of(eu.split(",")).collect(Collectors.toSet());
		 List<Datum> list=data.values()
					.stream()
					.filter(d->!filter.contains(d.getCountry()))
					.collect(Collectors.toCollection(ArrayList<Datum>::new));
		  
		 String jsonOutput=mapper.writeValueAsString(list);	 
		 	 
	     doReturn(list).when(dataService).read(any(String.class));
	     mockMvc.perform(post(Config.version+Config.pathFetch)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(jsonEU) 
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isOk())
		          .andExpect(content().json(jsonOutput));
	      
          //case: failed   
		 
	      doThrow(new RuntimeException()).when(dataService).read(any(String.class));
	      mockMvc.perform(post(Config.version+Config.pathFetch)
	    		  .header(Config.headerApiKey, Config.key)
	    		  .contentType(MediaType.APPLICATION_JSON)
                  .content(jsonEU)
	    		  .accept(MediaType.APPLICATION_JSON))
		          .andDo(print())
		          .andExpect(status().isInternalServerError());
	 }
}
