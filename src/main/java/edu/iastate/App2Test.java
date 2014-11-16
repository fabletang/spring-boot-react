package edu.iastate;

import edu.iastate.data.Course;
import edu.iastate.data.Section;
import edu.iastate.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


@EnableAutoConfiguration
public class App2Test implements CommandLineRunner{




        @Autowired
        private CourseRepository repository;

        public static void main(String[] args) {
            SpringApplication.run(App2Test.class, args);
        }

        @Override
        public void run(String... args) throws Exception {

            repository.deleteAll();

            Course course1 = new Course();
            course1.setClassTitle("hello");
            Section section = new Section();
            section.setSectionID("blah");
            course1.addSection(section);

            Course course2 = new Course();
            course2.setClassTitle("hello2");
            Section section2 = new Section();
            section2.setSectionID("yo");
            course2.addSection(section2);

            // save a couple of customers
            repository.save(course1);
            repository.save(course2);

            // fetch all customers
            System.out.println("Customers found with findAll():");
            System.out.println("-------------------------------");
            for (Course course : repository.findAll()) {
                System.out.println(course);
            }

            //for (Course course : repository.)
            //System.out.println();


        }

}
