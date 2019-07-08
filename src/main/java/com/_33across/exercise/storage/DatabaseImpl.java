package com._33across.exercise.storage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

import com._33across.exercise.dto.Datum;
import com._33across.exercise.storage.Database;
import com._33across.exercise.util.DataHandler;

public class DatabaseImpl implements Database {
	
	private ConcurrentHashMap<String, Datum> data=new ConcurrentHashMap<>();
	
	@Override
	public List<Datum> query(final Set<String> filter) throws Exception {
		return DataHandler.screen(filter, data);
	}
	
	@Override
	public void insert(final String id, final String country, final String ua, final String f1, final String f2, final String f3)  throws Exception {
		if (data.containsKey(id)) {
			Datum d=data.get(id);
			d.setCountry(country);
			d.setUa(ua);
			d.setF1(f1);
			d.setF2(f2);
			d.setF3(f3);
		} else {
			Datum d=new Datum(id, country, ua, f1, f2, f3);
			data.put(id, d);
		}
	}
	
	@Override
	public Map<String, Datum> getDb() {
		return data;
	}
}
