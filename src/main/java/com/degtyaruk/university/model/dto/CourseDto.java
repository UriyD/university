package com.degtyaruk.university.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private  Integer id;
    private  Integer facultyId;
    private  String name;
}

