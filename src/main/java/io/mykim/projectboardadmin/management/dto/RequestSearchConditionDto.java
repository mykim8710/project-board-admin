package io.mykim.projectboardadmin.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RequestSearchConditionDto {
    private String searchKeyword;
    private Integer page;
    private Integer size;
    private String sort;
}
