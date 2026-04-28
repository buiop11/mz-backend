package com.matjzing.controller;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
public class CommonController {

	@GetMapping(value = "/hc", name = "Health Check용도")
	public String hc(){
		return "UP";
	}

	@GetMapping(value = "/", name = "존재하지 않는 index 페이지 용도")
	public String index(){
		return "";
	}

}

