package com.anda.rest.service;

import com.anda.rest.model.Filter;

import java.util.List;
import java.util.Map;

public interface FilterService {
    boolean createFilter(Filter filter);
    public String updateFilter(Map<String, Object> updates);
    public String deleteFilter(String username);
    public Filter getFilter(String username);
    public List<Filter> getFiltersByUsername(String username);
}
