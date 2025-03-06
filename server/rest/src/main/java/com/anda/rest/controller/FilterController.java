package com.anda.rest.controller;

import com.anda.rest.model.Filter;
import com.anda.rest.service.FilterService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
// import java.security.SecureRandom;
import java.util.List;
// import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * Report API processing
 * @author Josh Rubow (jrubow)
 */

@RestController
@RequestMapping("/api/filter")
public class FilterController {

    FilterService filterService;



    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createFilterAccess(Authentication authentication, @RequestBody Filter filter) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            boolean isCreated = filterService.createFilter(filter);
            return isCreated ? ResponseEntity.ok("FILTER CREATED") : ResponseEntity.status(400).body("FILTER ALREADY EXISTS");
        }
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
            return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
        }
        return ResponseEntity.status(403).body("Access Denied");
    }

//    @GetMapping("/username")
//    public ResponseEntity<?> getFilterByUsername(Authentication authentication, @RequestBody Map<String, Object> body) {
//        try {
//            // Extract deviceId from the request body
//            if (authentication != null && authentication.getAuthorities().stream()
//                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_GUEST"))) {
//                return ResponseEntity.badRequest().body("ERROR: You are browsing as a guest, please log in!");
//            }
//            String username = (String) body.get("username");
//            if (username == null) {
//                return ResponseEntity.badRequest()
//                        .body("ERROR : username SHOULD NOT BE NULL");
//            }
//
//            // Retrieve filters using the username
//            List<Filter> filters = filterService.getFiltersByUsername(username);
//
//            // Spring Boot will automatically convert the List<Report> to JSON
//            return ResponseEntity.ok(filters);
//        } catch (Exception e) {
//            // Return a JSON formatted error message in case of exception
//            return ResponseEntity.status(500)
//                    .body("ERROR : COULD NOT RETRIEVE USER'S FILTER");
//        }
//    }

}
