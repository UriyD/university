package com.degtyaruk.university.model.dto;

import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Group;
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
public class FacultyDto {

    private Integer id;
    private String name;
    private  List<Integer> courseIds;
    private  List<Integer> groupIds;
}

