package com.talhacgdem.githubtrendapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Repo{
    private Integer rank;
    private String repositoryName;
    private String username;
    private String description;
    private String url;
    private String language;
    private String languageColor;
    private Integer totalStars;
    private Integer forks;
    private Integer starsSince;
    private List<Developer> builtBy;

    public void setLanguageColor(String languageColor){
        if (languageColor != null && !languageColor.equals(""))
            languageColor = languageColor.substring(languageColor.indexOf('#'));


        this.languageColor = languageColor;
    }

//    @Override
//    public int compareTo(Repo o) {
//        if (getTodays_stars() == null || o.getTodays_stars() == null) {
//            return 0;
//        }
//        return getTodays_stars().compareTo(o.getTodays_stars());
//    }
}
