package com.deviation.blog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
