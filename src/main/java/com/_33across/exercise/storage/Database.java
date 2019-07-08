package com._33across.exercise.storage;

import java.util.List;
import java.util.Set;
import java.util.Map;

import com._33across.exercise.dto.Datum;

public interface Database {
	public List<Datum> query(Set<String> filter) throws Exception;
	public void insert(String id, String country, String ua, String f1, String f2, String f3) throws Exception;
	public Map<String, Datum> getDb() throws Exception;
}
