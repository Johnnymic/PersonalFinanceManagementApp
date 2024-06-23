package com.michael.Personal.Finance.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DefaultApiResponse<T> {

        private String status;

        private String message;

        @JsonProperty(value = "data")
        private T data;


}
