package com.anda.rest.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
// import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.anda.rest.model.Filter;
import com.anda.rest.repository.FilterRepository;
import com.anda.rest.service.FilterService;

@Service
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;

    // Constructor for RelayDeviceServiceImpl
    public FilterServiceImpl(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @Override
    public boolean createFilter(Filter filter) {
        try {
            filterRepository.save(filter);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String updateFilter(Map<String, Object> updates) {
        String username = (String) updates.get("username");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username must be non-empty");
        }

        // Find the Devic by id
        Filter filter = (Filter) filterRepository.findById(username).orElse(null);
        if (filter == null) {
            return "USERNAME: " + username + " IS NOT FOUND";
        }

        Set<String> allowedFields = Set.of("ice", "flood", "render_long", "render_lat", "render_rad");

        for (String key : updates.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' cannot be modified.");
            }
        }

        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "ice" -> filter.setIce((Boolean) value);
                    case "flood" -> filter.setFlood((Boolean) value);
                    case "render_long" -> filter.setRender_long((Double) value);
                    case "render_lat" -> filter.setRender_lat((Double) value);
                    case "render_rad" -> filter.setRender_rad((Double) value);
                    default -> throw new IllegalArgumentException("Invalid field: " + key);
                }
            }
        });

        return "FILTER UPDATED";
    }

    @Override
    public String deleteFilter(String username) {
        filterRepository.deleteById(username);
        return "FILTER DELETED FROM DATABASE";
    }

    @Override
    public Filter getFilter(String username) {
        return filterRepository.findById(username).orElse(null);
    }

    @Override
    public List<Filter> getFiltersByUsername(String username) {
        return filterRepository.findFiltersByUsername(username);
    }
}
