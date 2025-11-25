package com.example.day2.services;
import com.example.day2.model.Student;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StudentService {
    private List<Student> students = new ArrayList<>();
    private int nextId = 1; // ID generator

    // CREATE
    public String addStudent(Student student) {
        student.setId(nextId++); // Automatically assign ID
        students.add(student);
        return "Student added successfully with ID: " + student.getId();
    }

    // READ (all)
    public List<Student> getAllStudents() {
        return students;
    }

    // READ (by ID)
    public Object getStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;  // return the Student object
            }
        }
        return "No student found with ID: " + id;
    }


    // UPDATE
    public String updateStudent(int id, Student updatedStudent) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(updatedStudent.getName());
                s.setAge(updatedStudent.getAge());
                s.setGrade(updatedStudent.getGrade());
                return "Student updated successfully!";
            }
        }
        return "Student not found!";
    }

    // DELETE
    public String deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        return removed ? "Student deleted successfully!" : "Student not found!";
    }
    public String addMultipleStudents(List<Student> studentList) {
        for (Student s : studentList) {
            s.setId(nextId++); // assign auto ID
            students.add(s);
        }
        return studentList.size() + " students added successfully!";
    }
}
