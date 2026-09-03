package com.sinclair.digital.app.migrate.Dao;

import java.util.List;

import com.sinclair.digital.app.migrate.model.Content;

public interface ContentDao {
	
	public List<Content> getAllContentByTypeWithinDateRange(String type, String startDate, String endDate);
}
