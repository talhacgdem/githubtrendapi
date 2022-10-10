package com.talhacgdem.githubtrendapi;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RepoService {
    private final String GitHubURL = "https://github.com/trending";


    public List<Repo> update() {
        List<Repo> listOfRepositories = new ArrayList<>();
        try {
            Connection.Response jsoupResponse = Jsoup.connect(GitHubURL).execute();
            if (jsoupResponse.statusCode() == 200) {
                Document githubPage = jsoupResponse.parse();
                Elements articleTagsForRepositories = githubPage.select("div.box article.Box-row");

                Repo repo;

                for (Element articleTag : articleTagsForRepositories) {
                    String captionOfRepository = articleTag.select("h1.h3").text();
                    repo = setFromCaptionRepo(captionOfRepository);
                    repo.setDescription(articleTag.select("p.col-9.color-fg-muted.pr-4").text());
                    repo.setLanguage(articleTag.select("span[itemprop=\"programmingLanguage\"]").text());
                    repo.setLanguageColor(articleTag.select("span.repo-language-color").attr("style"));

                    repo.setTotalStars(getIntValueFromString(articleTag.selectXpath("div[2]/a[1]").text()));
                    repo.setForks(getIntValueFromString(articleTag.selectXpath("div[2]/a[2]").text()));
                    repo.setStarsSince(getIntValueFromString(articleTag.selectXpath("div[2]/span[3]").text()));

                    Elements builtBy = articleTag.selectXpath("div[2]/span[2]/a");
                    repo.setBuiltBy(setDevelopersToRepo(builtBy));

                    listOfRepositories.add(repo);
                }

                listOfRepositories.sort((r1, r2) -> r2.getStarsSince().compareTo(r1.getStarsSince()));
                var ref = new Object() {
                    int i = 1;
                };
                listOfRepositories.forEach((r) -> {r.setRank(ref.i); ref.i++;});

                return listOfRepositories;
            } else {
                return null;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    private List<Developer> setDevelopersToRepo(Elements devElements) {
        List<Developer> developers = new ArrayList<>();

        for (Element devElem : devElements) {
            Developer developer = new Developer(
                    devElem.attr("href").replace("/", ""),
                    "https://github.com" + devElem.attr("href"),
                    devElem.select("img").eq(0).attr("src")
            );
            developers.add(developer);
        }

        return developers;
    }

    private Repo setFromCaptionRepo(String from) {
        from = from.replace(" ", "");
        String[] splittedTitle = from.split("/");

        return new Repo(
                0,
                splittedTitle[1],
                splittedTitle[0],
                "",
                "https://github.com/" + from,
                "",
                "",
                0,
                0,
                0,
                new ArrayList<>()
        );
    }

    private Integer getIntValueFromString(String text) {
        try {
            return Integer.valueOf(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

}
