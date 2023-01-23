package com.rebel.BlogAPIv2.enitities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blog_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category
{
    private Integer coId;
    private String coName;
    private String coDes;


}
