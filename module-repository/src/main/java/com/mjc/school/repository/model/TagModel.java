package com.mjc.school.repository.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class TagModel implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH} )
    private Set<NewsModel> news = new HashSet<>();

    public TagModel(){}

    public TagModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NewsModel> getNews() {
        return news;
    }

    public void setNews(Set<NewsModel> news) {
        this.news = news;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
