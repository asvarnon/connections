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
public class DynamicValue {

    private int id;
    private String category;
    private String value;
    private String type;


}
