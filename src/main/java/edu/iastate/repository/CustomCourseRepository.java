package edu.iastate.repository;

import edu.iastate.data.Course;

import java.util.List;
import java.util.Map;

/**
 * Created by jjhanso on 11/16/14.
 */
public interface CustomCourseRepository {

    List<Course> findByComplexQuery(Map<String, String> criteria);
}
