package com.example.day2.controller;

import com.example.day2.model.Student;

import com.example.day2.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // CREATE
    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // READ (all)
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // READ (by ID)
    @GetMapping("/{id}")
    public Object getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public String updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }


    @PostMapping("/add-multiple")
    public String addMultipleStudents(@RequestBody List<Student> studentList) {
        return studentService.addMultipleStudents(studentList);
    }

}

