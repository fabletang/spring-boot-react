package edu.iastate.repository;

import edu.iastate.data.Semester;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SemesterRepository extends MongoRepository<Semester, String> {
}
