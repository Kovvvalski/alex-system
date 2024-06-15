package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.exception.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
  Course findByName(String name);
}
