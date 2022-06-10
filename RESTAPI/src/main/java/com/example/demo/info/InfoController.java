package com.example.demo.info;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.info.model.Project;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InfoController {
	
//	@GetMapping("/info")
//	public String projectInfo() {
////		return "Project name is preword.";
//		// 원시적 json 문자열 만들기
//		return "{\"project name\": \"preword\", " + "\"created date\": \"2021-07-03\"}";
//	}
//	Jackson
	@GetMapping("/info")
	public Object projectInfo() {
		log.debug("/info start");
		
		Project project = new Project();
		project.projectName = "preword";
		project.author = "hello-bryan";
		project.createdDate = new Date();
		
		return project;
	}
//	결과
//	{"projectName":"preword","author":"hello-bryan","createdDate":"2022-06-08T04:41:36.912+00:00"}
	
//	Gson
	@GetMapping("/info2")
	public String cumstomJson() {
		JsonObject jo = new JsonObject();
		jo.addProperty("projectName", "preword");
		jo.addProperty("author", "hello-bryan");
		jo.addProperty("createdDate", new Date().toString());
		
		JsonArray ja = new JsonArray();
		for(int i = 0 ; i < 5 ; i++) {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("prop"+i, i);
			ja.add(jObj);
		}
		jo.add("follower", ja);
		return jo.toString();
	}
	
	@GetMapping()
	public String Hello() {
		return "Hello2";
	}
}
