package com.example.demo.info;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.info.model.Project;

@Service
public class InfoService {
	
	public Project getProjectInfo() {
		
		Project project = new Project();
		project.projectName = "preword";
		project.author = "hello-bryan";
		project.createdDate = new Date();
		
		return project;
	}
}
