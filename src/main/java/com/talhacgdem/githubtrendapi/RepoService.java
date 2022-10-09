package com.talhacgdem.githubtrendapi;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class RepoService {
    private final String GitHubURL = "https://github.com/trending";


    public List<Repo> update(){
        List<Repo> listOfRepositories = new ArrayList<>();
        try{
            Connection.Response jsoupResponse = Jsoup.connect(GitHubURL).execute();
            if (jsoupResponse.statusCode() == 200){
                Document githubPage = jsoupResponse.parse();
                Elements articleTagsForRepositories = githubPage.select("div.box article.Box-row");

                Repo repo;

                for (Element articleTag : articleTagsForRepositories){
                    String captionOfRepository = articleTag.select("h1.h3").text();
                    repo = setFromCaptionRepo(captionOfRepository);
                    repo.setDescription(articleTag.select("p.col-9.color-fg-muted.pr-4").text());
                    repo.setLanguage(articleTag.select("span[itemprop=\"programmingLanguage\"]").text());
                    repo.setTotalstars(getIntValueFromString(articleTag.selectXpath("div[2]/a[1]").text()));
                    repo.setForks(getIntValueFromString(articleTag.selectXpath("div[2]/a[2]").text()));
                    repo.setTodays_stars(getIntValueFromString(articleTag.selectXpath("div[2]/span[3]").text()));
                    listOfRepositories.add(repo);
                }

                listOfRepositories.sort((r1, r2) -> r2.getTodays_stars().compareTo(r1.getTodays_stars()));

                return listOfRepositories;

            }else{
                return null;
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
            return null;
        }
    }

    private Repo setFromCaptionRepo(String from) {
        from = from.replace(" ", "");
        String[] splittedTitle = from.split("/");
        return new Repo(
                generateHashIdFromCaptionString(from),
                splittedTitle[1],
                splittedTitle[0],
                from,
                "",
                "",
                0,
                0,
                0
        );
    }

    private String generateHashIdFromCaptionString(String from) {
        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(from.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private List<Repo> sortByRank(List<Repo> responseList) {
//        List<Repo> employeeById = new ArrayList<>(responseList.values());
        return null;
    }

    private Integer getIntValueFromString(String text) {
        try {
            return Integer.valueOf(text.replaceAll("[^0-9]", ""));
        }catch (Exception e){
            System.out.println(text);
            return 0;
        }
    }


    private Map<String, String> setFromCaption(String from){
        from = from.replace(" ", "");
        Map<String, String> to = new HashMap<>();
        to.put("name", from.split("/")[1]);
        to.put("owner", from.split("/")[0]);
        to.put("full_name", from);
        return to;
    }
}
