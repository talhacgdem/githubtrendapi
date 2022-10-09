package com.talhacgdem.githubtrendapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Repo{
    private String id;
    private String name;
    private String owner;
    private String full_name;
    private String description;
    private String language;
    private Integer totalstars;
    private Integer forks;
    private Integer todays_stars;

//    @Override
//    public int compareTo(Repo o) {
//        if (getTodays_stars() == null || o.getTodays_stars() == null) {
//            return 0;
//        }
//        return getTodays_stars().compareTo(o.getTodays_stars());
//    }
}
