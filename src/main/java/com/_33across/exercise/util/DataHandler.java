package com._33across.exercise.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com._33across.exercise.dto.Datum;

public class DataHandler {
	public static void parseCcds1(String ccds1, Map<String, Datum> data) {
		Stream.of(ccds1.split("\n"))
	        .map(l->l.split("\",\""))
	        .forEach(d->{
	    	    String id=null;
	    	    String ua=null;
	    	    if (d.length>0) {
	    	    	if (d[0].endsWith("\"")) {
	    	    	   id=d[0].substring(1, d[0].length()-1);
	    	    	} else {
	    		       id=d[0].substring(1);
	    	    	}
	    		    if (d.length>1) {
	    			    ua=d[1].substring(0, d[1].length()-1);
	    		    }  
		    	    try {
			    		if (data.containsKey(id)) {
			    			Datum dd=data.get(id);
			    			dd.setUa(ua);
			    		} else {
			    			Datum dd=new Datum(id, null, ua, null, null, null);
			    			data.put(id, dd);
			    		}
			    	} catch (Exception e) {
			    		throw new RuntimeException(e.toString());
			    	}
	    	    }
	     });
	}
	
	public static void parseCcds2(String ccds2, Map<String, Datum> data) {
		Stream.of(ccds2.split("\n"))
	    .map(l->l.split("\\s+"))
	    .forEach(d->{
	    	String id=null;
    	    String country=null;
    	    String f1=null;
    	    String f2=null;
    	    String f3=null;
	    	if (d.length>0) {
	    		id=d[0];
	    		if (d.length>1) {
	    			country=d[1];
	    		    if (d.length>2) {
	    		    	f1=d[2];
	    			    if (d.length>3) {
	    			    	f2=d[3];
	    			    	if (d.length>4) {
	    			    		f3=d[4];
	    			    	}
	    			    }
	    		    }
	    		}
	    	    try {
		    		if (data.containsKey(id)) {
		    			Datum dd=data.get(id);
		    			dd.setCountry(country);
		    			dd.setF1(f1);
		    			dd.setF2(f2);
		    			dd.setF3(f3);
		    		} else {
		    			Datum dd=new Datum(id, country, null, f1, f2, f3);
		    			data.put(id, dd);
		    		}
		    	} catch (Exception e) {
		    		throw new RuntimeException(e.toString());
		    	}
	    	}
	    });
	}
	
	public static List<Datum> screen(Set<String> filter, Map<String, Datum> data) {
		return data.values()
				.stream()
				.filter(d->!filter.contains(d.getCountry()))
				.collect(Collectors.toCollection(ArrayList<Datum>::new));	
	}
}
