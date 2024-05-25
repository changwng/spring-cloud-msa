package com.example.springbootclient.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Header {

    String requestUrl;
    String message;
    String resultCode;
}
