package com.degtyaruk.university.model.dto;

import com.degtyaruk.university.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Integer> coursesId;
    private String email;
    private String parole;
}

