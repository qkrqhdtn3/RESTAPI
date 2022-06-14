package com.example.demo.info;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.info.model.City;
import com.example.demo.info.model.Project;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InfoController {
	
	@GetMapping()
	public String Hello() {
		return "Hello2";
	}
	
	@GetMapping("/info1")
	public String projectInfo1() {
//		return "Project name is preword.";
//		원시적 json 문자열 만들기
		return "{\"project name\": \"preword\", " + "\"created date\": \"2021-07-03\"}";
	}
	
//	Jackson
	@GetMapping("/info2")
	public Object projectInfo2() {
//		11:25:20.354 [http-nio-8080-exec-2] DEBUG com.example.demo.info.InfoController - /info start
//		11:23:49.233 [http-nio-8080-exec-1] INFO  com.example.demo.info.InfoController - return com.example.demo.info.model.Project@fb7c8fb
		log.debug("/info2 start");
		
		Project project = new Project();
		project.projectName = "preword";
		project.author = "hello-bryan";
		project.createdDate = new Date();
		
		log.info("return {}", project.toString());
		return project;
	}
//	결과
//	{"projectName":"preword","author":"hello-bryan","createdDate":"2022-06-08T04:41:36.912+00:00"}
	
//	Gson
	@GetMapping("/info3")
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

//	DI InfoControllder, InfoService
//	@Autowired
	private InfoService infoService;
	
	@Autowired
	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	@GetMapping("/info4")
	public Object projectInfo4() {
		log.debug("/info4 start");
		Project project = infoService.getProjectInfo();
		return project;
	}
	
	@GetMapping("/cityList")
	public Object cityList() {
		log.debug("/cityList start");
		List<City> cityList = infoService.getCityList();
		return cityList;
	}
}
