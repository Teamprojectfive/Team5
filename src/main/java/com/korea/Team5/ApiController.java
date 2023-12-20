package com.korea.Team5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/getData")
    public String getDataFromApi(){
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));


        // API 서비스에 현재 날짜를 전달하여 데이터를 가져옴
        return apiService.fetchDataFromApi(today);

    }

}
