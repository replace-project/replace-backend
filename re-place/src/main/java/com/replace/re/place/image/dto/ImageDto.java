package com.replace.re.place.image.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long imageId;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ImageDto(String url){
        this.url = url;
    }
}
