package edu.iastate.repository;

import edu.iastate.data.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface CourseRepository extends MongoRepository<Course, String>, CustomCourseRepository {

    //List<Course> findByDeptCode(String semesterCode, String semesterYear, String deptCode);

    List<Course> findByDeptCode(String deptCode);

    @Query("{ term: ?0, deptCode: ?1 }")
    List<Course> findByBaseQuery(String term, String department);

}
