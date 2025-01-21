package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
  List<Lesson> findAllByBeginAfter(LocalDateTime begin);

  List<Lesson> findAllByGroup(Group group);

  List<Lesson> findAllByLecturer(Lecturer lecturer);

  @Transactional
  void deleteAllByGroupName(String groupName);

  @Transactional
  void deleteAllByLecturerId(Long id);
}
