package com.example.demo.info.model;

import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

//�߿� Getter, Setter �־�� �Ѵ�.
@Getter
@Setter
//JacksonProperty
//@JsonInclude(JsonInclude.Include.NON_NULL) ���� null �� �ƴ� ���鸸 ��ȯ�ȴ�.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
	public String projectName;
//	@JsonProperty(value = "project master") ���� Ű�� value�� �ٲ��ش�.
//	@JsonProperty(value = "project master")
	public String author;
//	@JsonIgnore ���� �� ������Ƽ�� ��ȯ���� ���Ե��� �ʴ´�.
//	@JsonIgnore
//	���� �ڵ� ���� "createdDate":"2022-06-08T04:41:36.912+00:00" -> "2022-06-08"
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date createdDate;
}
