package edu.iastate.controller;


import edu.iastate.data.Course;
import edu.iastate.data.Department;
import edu.iastate.repository.CourseRepository;
import edu.iastate.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourseByDeptCode(
            //@RequestParam(value="semCode", required=true) String semCode,
            //@RequestParam(value="semYr", required=true) String semYr,
            @RequestParam(value="deptCode", required=true) String deptCode
    )
    {
        //return courseRepository.findByDeptCode(semCode, semYr, deptCode);
        return courseRepository.findByDeptCode(deptCode);
    }

    @RequestMapping(value="/basic", method= RequestMethod.GET)
    public @ResponseBody
    List<Course> getCourseByBasicQuery(
            @RequestParam(value="term", required=true) String term,
            @RequestParam(value="deptCode", required=true) String deptCode
    )
    {
        return courseRepository.findByBaseQuery(term, deptCode);
        //return courseRepository.findByDeptCode(deptCode);
    }

    @RequestMapping(value="/dept", method= RequestMethod.GET)
    public @ResponseBody
    List<Department> getDepartments()
    {
        return departmentRepository.findAll(new Sort(Sort.Direction.ASC, "departmentTitle"));
    }
}
