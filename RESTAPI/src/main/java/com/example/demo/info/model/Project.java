package com.example.demo.info.model;

import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

//중요 Getter, Setter 있어야 한다.
@Getter
@Setter
//JacksonProperty
//@JsonInclude(JsonInclude.Include.NON_NULL) 사용시 null 이 아닌 값들만 반환된다.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
	public String projectName;
//	@JsonProperty(value = "project master") 사용시 키를 value로 바꿔준다.
//	@JsonProperty(value = "project master")
	public String author;
//	@JsonIgnore 사용시 이 프로퍼티는 반환값에 포함되지 않는다.
//	@JsonIgnore
//	밑의 코드 사용시 "createdDate":"2022-06-08T04:41:36.912+00:00" -> "2022-06-08"
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date createdDate;
}
