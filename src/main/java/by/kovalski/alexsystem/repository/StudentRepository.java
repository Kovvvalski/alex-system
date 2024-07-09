package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  List<Student> findAllByGroups(List<Group> groups);
}
