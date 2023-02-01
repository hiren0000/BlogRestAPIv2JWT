package com.rebel.BlogAPIv2.enitities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String pass;
    private String about;

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<Post> posts = new ArrayList<>();

   // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   //private List<Comment> comments = new ArrayList<>();


}
