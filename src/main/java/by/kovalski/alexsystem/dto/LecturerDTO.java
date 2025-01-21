package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO extends AbstractPersonDTO {

    private List<Long> courseIds;

    public LecturerDTO(Lecturer lecturer) {
        super(lecturer.getId(), lecturer.getFirstName(), lecturer.getSecondName(), lecturer.getThirdName(), lecturer.getTelephoneNumber(),
                lecturer.getEmail(), lecturer.getStatus());
        this.courseIds = lecturer.getCourses().stream().map(Course::getId).toList();
    }
}
