package edu.iastate.repository;

import edu.iastate.data.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by jjhanso on 11/16/14.
 */
public class CourseRepositoryImpl implements CustomCourseRepository {
    private final MongoOperations operations;

    @Autowired
    public CourseRepositoryImpl(MongoOperations operations) {

        Assert.notNull(operations, "MongoOperations must not be null!");
        this.operations = operations;
    }

    @Override
    public List<Course> findByComplexQuery(Map<String, String> criteria) {
        return Collections.emptyList();
    }
}
