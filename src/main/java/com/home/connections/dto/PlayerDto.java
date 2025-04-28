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
public class PlayerDto {

    private Integer playerId;
    private String playerName;
    private String archetypeName;

}
