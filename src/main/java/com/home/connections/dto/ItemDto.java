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
public class ItemDto {
    private Integer itemId;
    private String itemCategory;
    private String itemDescription;
}
