//package com.sreehari.api.sreehari_api.core.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.Arrays;
//
//@Slf4j
//public class Helper {
//
//    private Helper() {
//        throw new AssertionError("Helper class should not be instantiated");
//    }
//
//    public static Pageable generatePageable(
//            Integer pageNumber,
//            Integer size,
//            String sortBy,
//            String sortDir,
//            String[] sortable
//    ){
//        Sort.Direction direction = (sortDir.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//        String validatedSortBy = sortBy;
//        if (sortable != null && sortBy != null) {
//            boolean isValidSortBy = Arrays.asList(sortable).contains(sortBy);
//            if (!isValidSortBy) {
//                log.warn("Invalid sortBy parameter '{}'. Available options: {}. Defaulting to 'created_at'",
//                        sortBy, Arrays.toString(sortable));
//                validatedSortBy = "created_at";
//            }
//        } else if (sortBy == null) {
//            validatedSortBy = "created_at";
//        }
//
//        Sort sort = Sort.by(direction, validatedSortBy, "created_at");
//        pageNumber = (pageNumber != null && pageNumber > 0) ? --pageNumber : 0;
//
//        return PageRequest.of(pageNumber, size, sort);
//    }
//}
package me.sreejithnair.linkup.post_service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Slf4j
public class Helper {

    private Helper() {
        throw new AssertionError("Helper class should not be instantiated");
    }

    public static Pageable generatePageable(
            Integer pageNumber,
            Integer size,
            String sortBy,
            String sortDir,
            String[] sortable,
            String defaultSortField
    ){
        Sort.Direction direction = (sortDir != null && sortDir.equals("desc")) ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        String validatedSortBy = defaultSortField; // Use provided default

        if (sortBy != null && sortable != null) {
            boolean isValidSortBy = Arrays.asList(sortable).contains(sortBy);
            if (isValidSortBy) {
                validatedSortBy = sortBy;
            } else {
                log.warn("Invalid sortBy parameter '{}'. Available options: {}. Defaulting to '{}'",
                        sortBy, Arrays.toString(sortable), defaultSortField);
            }
        }

        // Create sort with only the validated field
        Sort sort = Sort.by(direction, validatedSortBy);

        // Handle page number (Spring Data pages are 0-based)
        pageNumber = (pageNumber != null && pageNumber > 0) ? pageNumber - 1 : 0;

        return PageRequest.of(pageNumber, size, sort);
    }

    // Overloaded method for backward compatibility
    public static Pageable generatePageable(
            Integer pageNumber,
            Integer size,
            String sortBy,
            String sortDir,
            String[] sortable
    ){
        return generatePageable(pageNumber, size, sortBy, sortDir, sortable, "id");
    }
}