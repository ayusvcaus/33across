package com._33across.exercise.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._33across.exercise.dto.Datum;
import com._33across.exercise.service.DataService;
import com._33across.exercise.storage.Database;
import com._33across.exercise.util.DataHandler;

@Service
public class DataServiceImpl implements DataService {
	
	@Autowired
	public Database db;
	
	@Override
	public void saveCcds1(final String ccds1) throws Exception {
		 DataHandler.parseCcds1(ccds1, db.getDb());
	}
	
	@Override
	public void saveCcds2(final String ccds2) throws Exception {
		 DataHandler.parseCcds2(ccds2, db.getDb());
	}
		
	@Override
	public List<Datum> read(final String filter) throws Exception {
		Set<String> set=Stream.of(filter.split(",")).collect(Collectors.toSet());	    
		return db.query(set);			
	}
}
