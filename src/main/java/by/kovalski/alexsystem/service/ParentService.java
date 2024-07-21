package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.ParentDTO;
import by.kovalski.alexsystem.entity.Parent;

import java.util.List;

public interface ParentService extends BaseService<Parent, Long, ParentDTO> {
  List<Parent> findAllActive();
}
