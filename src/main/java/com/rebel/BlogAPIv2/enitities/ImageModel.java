package com.rebel.BlogAPIv2.enitities;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="image_table")
@NoArgsConstructor

public class ImageModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String imageName;
    private String imageType;

    @Column(length = 5000000)
    private byte [] imgByte;

    public ImageModel(String imageName, String imageType, byte[] imgByte) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imgByte = imgByte;
    }
}
