package com.relaschool.jee.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.School;
import model.User;

@AllArgsConstructor
@Builder
@Data
public class SchoolOutput {
    private Long schoolId;
    private User user;
    private String name;
    private String address;
    private String contact;

    public static SchoolOutput from (School school){
        return SchoolOutput.builder().schoolId (school.getSchoolId())
                .user(school.getUser())
                .name(school.getName())
                .address(school.getAddress())
                .contact(school.getContact()).build();
    }
}
