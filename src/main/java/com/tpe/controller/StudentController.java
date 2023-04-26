package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // !!! Get all students
    // endpoint + http methods -> http://localhost:8080/students + GET
    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); // List<Student> + HTTP.Status code = 200
    } //Birden çok kişi geleceği için List<> yapısını kullandık. Array kullanmamız için kişi sayısının belli olması gerekirdi.
      //ResponseEntity<> sayesinde hem Student'ları hem de Status Code'ları Repository'e göndermiş oluyoruz.

    // !!! Create new student
    @PostMapping // http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);

        Map<String,String> map = new HashMap<>();
        map.put("message","student is created successfully");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // 201
    }

    // !!! Get a Student by ID via RequestParam
    @GetMapping("/query") // example http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student); // return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // !!! Get a Student by ID via PathVariable
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    //!!! Delete Student with id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is deleted successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK); // return ResponseEntity.ok(map);
    }

}
