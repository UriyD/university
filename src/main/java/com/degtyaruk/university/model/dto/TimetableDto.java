package com.degtyaruk.university.model.dto;

import com.degtyaruk.university.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimetableDto {

    private Integer id;
    private LocalDate date;
    private  List<Integer> lectureIds;
}

