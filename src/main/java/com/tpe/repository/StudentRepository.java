package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Bu annotation'ı yazmasak da extends JpaRepository'den dolayı çalışır. Kod okunabilirliğini arttırmak için yine de yazıyoruz.
public interface StudentRepository extends JpaRepository<Student, Long> {


    boolean existsByEmail(String email);
}
