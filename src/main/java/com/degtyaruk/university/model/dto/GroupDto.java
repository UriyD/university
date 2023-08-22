package com.degtyaruk.university.model.dto;

import com.degtyaruk.university.model.Student;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    private Integer id;
    private Integer facultyId;
    private String name;
    private List<Integer> studentIds;
}

