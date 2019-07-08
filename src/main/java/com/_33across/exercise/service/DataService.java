package com._33across.exercise.service;

import java.util.List;

import com._33across.exercise.dto.Datum;

public interface DataService {
	
	public void saveCcds1(final String ccds1) throws Exception;
	public void saveCcds2(final String ccds2) throws Exception;
	public List<Datum> read(final String filter) throws Exception;
}
