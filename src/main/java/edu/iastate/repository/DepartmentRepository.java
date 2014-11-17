package edu.iastate.repository;

import edu.iastate.data.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by jjhanso on 11/16/14.
 */
public interface DepartmentRepository extends MongoRepository<Department, String> {

    /*@Query()
    List<Department> findAllDepartments();*/
}
