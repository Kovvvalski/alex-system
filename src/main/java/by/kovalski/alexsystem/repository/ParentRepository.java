package by.kovalski.alexsystem.repository;

import by.kovalski.alexsystem.entity.Parent;
import by.kovalski.alexsystem.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Long> {
  List<Parent> findAllByStatus(Status status);
}
