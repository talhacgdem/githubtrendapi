package com.talhacgdem.githubtrendapi;

import lombok.*;

import java.util.List;

@Data
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
}
