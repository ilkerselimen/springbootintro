package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("{id}") // example http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // !!! Delete Student with id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is deleted successfully");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK); // return ResponseEntity.ok(map);
    }

    // !!! Update Student
    @PutMapping("{id}") // http://localhost:8080/students/1 --> endPoint + id + JSON + HTTP-Method
    public ResponseEntity<Map<String,String>> updateStudent(
            @PathVariable Long id, @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is updated successfully");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // !!! pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page,    // kacinci sayfa gelsin
            @RequestParam("size") int size,    // sayfa basi kac urun
            @RequestParam("sort") String prop, // hangi field a gore siralanacak - optional
            @RequestParam("direction") Sort.Direction direction // siralama turu - optional
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);
    }

    // !!! Get By lastName
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> list = studentService.findStudent(lastName);
        return ResponseEntity.ok(list);
    }

}
