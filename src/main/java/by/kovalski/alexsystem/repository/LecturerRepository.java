package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
  List<Lecturer> findLecturersByCourses(List<Course> courses);

  boolean existsByEmail(String email);

  boolean existsByTelephoneNumber(String telephoneNumber);

  boolean existsByTelephoneNumberAndIdNot(String telephoneNumber, Long id);

  boolean existsByEmailAndIdNot(String email, Long id);
}
