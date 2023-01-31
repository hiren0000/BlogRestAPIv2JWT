package com.rebel.BlogAPIv2.enitities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="comment_table")
@Getter
@Setter
@NoArgsConstructor
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coId;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;



}
