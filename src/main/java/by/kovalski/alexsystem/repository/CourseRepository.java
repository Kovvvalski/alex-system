package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
  List<Course> findAllByStatus(Status status);
}
