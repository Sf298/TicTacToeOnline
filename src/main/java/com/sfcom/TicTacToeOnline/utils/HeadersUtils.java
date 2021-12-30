package com.sfcom.TicTacToeOnline.utils;

import org.springframework.http.HttpHeaders;

import java.util.List;

import static java.util.Objects.isNull;

public class HeadersUtils {

    public static Integer getUserId(HttpHeaders headers) {
        List<String> cookiesList = headers.get("Cookie");
        if (isNull(cookiesList)) return null;

        String userIdCookie = cookiesList.stream()
                .filter(c -> c.startsWith("userId="))
                .findFirst().orElse(null);
        if (isNull(userIdCookie)) return null;

        String[] split = userIdCookie.split("=");
        return Integer.parseInt(split[1]);
    }

}
