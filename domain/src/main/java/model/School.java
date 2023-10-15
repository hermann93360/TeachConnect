package model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class School {
    private Long schoolId;
    private User user;
    private String name;
    private String address;
    private String contact;
    private List<JobOffer> jobOffers;

}
