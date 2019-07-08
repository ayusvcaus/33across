package com._33across.excercise.util;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com._33across.exercise.dto.Datum;
import com._33across.exercise.util.DataHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:exercise-applicationContext.xml"})

public class DataHandlerTest {
	private static String ccds1;
	private static String ccds2;
	private static String ua="\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\"\n";
	
	@Before
	public void setup() throws Exception {
		ccds1="\"758\","+ua 
				+"\"576\"\n";
		ccds2="118	US	2836	23464	544\n"
			  +"999	US	119129	72001	693\n"
			  + "752	TR	223";
	}

	@Test
	public void testParseCcds1() throws Exception {
		Map<String, Datum> data=new HashMap<>();
		DataHandler.parseCcds1(ccds1, data);
		assertTrue(data.containsKey("758"));
		assertTrue(data.containsKey("576"));
		assertNull(data.get("576").getUa());
		assertEquals(ua.substring(1, ua.length()-2), data.get("758").getUa());
	}
	
	@Test
	public void testParseCcds2() throws Exception {
		Map<String, Datum> data=new HashMap<>();
		DataHandler.parseCcds2(ccds2, data);
		assertTrue(data.containsKey("118"));
		assertTrue(data.containsKey("999"));
		assertNull(data.get("752").getF2());
	}
	
	@Test
	public void testScreen() throws Exception {
		Map<String, Datum> data=new HashMap<>();
		DataHandler.parseCcds2(ccds2, data);
		Set<String> filter=new HashSet<>();
		filter.add("TR");
		List<Datum> list=DataHandler.screen(filter, data);
		assertEquals(2, list.size());
		assertNull(data.get("752").getF2());
		filter.add("US");
		list=DataHandler.screen(filter, data);
		assertEquals(0, list.size());
	}
}
