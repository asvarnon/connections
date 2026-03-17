package com.home.connections.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtisanDto {

    private Integer artisanId;
    private String artisanName;
    private String artisanType;


}
