package com.example.demo.info;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.info.model.City;
import com.example.demo.info.model.FileData;
import com.example.demo.info.model.Project;
import com.example.demo.info.storage.StorageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@Controller
@RequestMapping("info")
public class InfoController {
	
	//	DI InfoControllder, InfoService
	private InfoService infoService;
	private StorageService storageService;

	@Autowired
	public InfoController(InfoService infoService, StorageService storageService) {
		this.infoService = infoService;
		this.storageService = storageService;
	}
	
	@GetMapping()
	public String Hello() {
		return "Hello2";
	}

	@GetMapping("1")
	public String projectInfo1() {
//		return "Project name is preword.";
//		원시적 json 문자열 만들기
		return "{\"project name\": \"preword\", " + "\"created date\": \"2021-07-03\"}";
	}

//	Jackson
	@GetMapping("2")
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
	@GetMapping("3")
	public String cumstomJson() {
		JsonObject jo = new JsonObject();
		jo.addProperty("projectName", "preword");
		jo.addProperty("author", "hello-bryan");
		jo.addProperty("createdDate", new Date().toString());

		JsonArray ja = new JsonArray();
		for (int i = 0; i < 5; i++) {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("prop" + i, i);
			ja.add(jObj);
		}
		jo.add("follower", ja);
		return jo.toString();
	}

	@GetMapping("4")
	public Object projectInfo4() {
		log.debug("/info4 start");
		Project project = infoService.getProjectInfo();
		return project;
	}

	@GetMapping("cityList")
	public Object cityList() {
		log.debug("/cityList start");
		List<City> cityList = infoService.getCityList();
		return cityList;
	}

	// localhost:8080/info/cityListByCode/KOR/3000000
	@GetMapping("cityListByCode/{countryCode}/{population}")
	public Object cityByCountryCode1(@PathVariable("countryCode") String countryCode, @PathVariable("population") int population) {
		log.debug("countryCode = {}, population = {}", countryCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(countryCode, population);
		return cityList;
	}

	// http://localhost:8080/info/cityListByCode?countryCode=KOR&population=1000000
	@GetMapping("cityListByCode")
	public Object cityByCountryCode2(@RequestParam("countryCode") String countryCode, @RequestParam(value = "population", required = false, defaultValue = "0") int population) {
		log.debug("countryCode = {}, population = {}", countryCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(countryCode, population);
		return cityList;
	}
	
	// http://localhost:8080/info/cityAdd1?name=TEST&countryCode=TST&district=Seoul&population=100
	@GetMapping(value="cityAdd1")
	public Object cityAdd1(City city) {
		log.debug("city = {}", city.toString());
		return "ok";
	}
	
	// http://localhost:8080/info/cityAdd2/TEST/TST/Seoul/100
	@GetMapping("cityAdd2/{name}/{countryCode}/{district}/{population}")
	public Object cityAdd2(@PathVariable(value="name") String name, 
			@PathVariable(value="countryCode") String ctCode, 
			@PathVariable(value="district") String district, 
			@PathVariable(value="population") int population) {
		log.debug("name = {}, ctCode = {}, district = {}, population = {}", name, ctCode, district, population);
		return "ok";
	}
	
	// http://localhost:8080/info/cityAdd3?name=TEST&countryCode=TST&district=Seoul&population=100
	@GetMapping("cityAdd3")
	public Object cityAdd3(@RequestParam(value="name", required=true) String name, 
			@RequestParam(value="countryCode", required=true) String ctCode, 
			@RequestParam(value="district", required=true) String district, 
			@RequestParam(value="population", required=false, defaultValue="0") int population) {
		log.debug("name = {}, ctCode = {}, district = {}, population = {}", name, ctCode, district, population);
		
		return "ok";
	}
	
	// http://localhost:8080/info/cityAdd4
//	{
//		  "name": "TEST",
//		  "countryCode": "TST",
//		  "district": "Seoul",
//		  "population": 100
//		}
	@PostMapping(value="cityAdd4")
	public ResponseEntity<City> cityAdd4(@RequestBody City city){
		log.debug("city = {}", city.toString());
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	// http://localhost:8080/info/cityAdd5?name=TEST&countryCode=TST&district=Seoul&population=100
	@PostMapping(value="cityAdd5")
	public ResponseEntity<String> cityAdd5(@RequestBody City city){
		try {
			log.debug("city = {}", city.toString());
			
			log.debug(city.getId().toString());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	// http://localhost:8080/info/cityAdd6?name=TEST&countryCode=TST&district=Seoul&population=100
	@PostMapping(value="cityAdd6")
	public ResponseEntity<String> cityAdd6(String name, String countryCode, String district, Integer population){
		log.debug("name = {}, ctCode = {}, district = {}, population = {}", name, countryCode, district, population);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
//	http://localhost:8080/info/cityAdd7
	@PostMapping(value="cityAdd7")
	public ResponseEntity<City> cityAdd(@RequestBody City city){
		try {
			log.debug("city = {}", city.toString());
			return new ResponseEntity<>(infoService.insert(city), HttpStatus.OK);
		} catch(Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	http://localhost:8080/info/cityEdit
	@PostMapping(value="cityEdit")
	public ResponseEntity<String> cityEdit(@RequestBody City city){
		try {
			log.debug("city = {}", city.toString());
			Integer updatedCnt = infoService.updateById(city);
			return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
		} catch (Exception e) {
			log.debug(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	http://localhost:8080/info/cityDelete?id=4085
	@ResponseBody
	@PostMapping(value="cityDelete")
	public ResponseEntity<String> cityDelete(@RequestParam(value="id") Integer id){
		try {
			log.debug("city id = {}", id);
			Integer deletedCnt = infoService.deleteById(id);
			return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
		} catch(Exception e) {
			log.debug(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	http://localhost:8080/info/uploadFile
	@PostMapping(value="uploadFile")
	public ResponseEntity<String> uploadFile(MultipartFile file) throws IllegalStateException, IOException{
		if(!file.isEmpty()) {
			log.debug("file org name = {}", file.getOriginalFilename());
			log.debug("file content type = {}", file.getContentType());
			file.transferTo(new File(file.getOriginalFilename()));
		}
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
//	http://localhost:8080/info/upload
	@PostMapping(value="upload")
	public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException{
		storageService.store(file);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
//	http://localhost:8080/info/download?filename=RESTAPI개발환경.txt
	@GetMapping(value="download")
	public ResponseEntity<Resource> serveFile(@RequestParam(value="filename") String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
//	http://localhost:8080/info/deleteAll
	@PostMapping(value="deleteAll")
	public ResponseEntity<String> deleteAll(){
		storageService.deleteAll();
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
//	http://localhost:8080/info/fileList
	@GetMapping("fileList")
	public ResponseEntity<List<FileData>> getListFiles(){
		List<FileData> fileInfos = storageService.loadAll().map(path ->{
			FileData data = new FileData();
			String filename = path.getFileName().toString();
			data.setFilename(filename);
			data.setUrl(MvcUriComponentsBuilder.fromMethodName(InfoController.class, "serveFile", filename).build().toString());
			try {
				data.setSize(Files.size(path));
			} catch(IOException e) {
				log.error(e.getMessage());
			}
			return data;
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}
}
