package com.nilesh.demo4.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nilesh.demo4.model.Student;
import com.nilesh.demo4.repository.SaveStudentRepository;
import com.nilesh.demo4.service.IStudentService;

import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.BatchAllocator.ForTotal;

@Controller
public class myController {

	@Autowired
	private IStudentService studentService;

	@RequestMapping("/student/{pageNo}/{pageSize}")
	@ResponseBody
	public List<Student> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize) {
		return studentService.findPaginated(pageNo, pageSize);
	}

	@RequestMapping("home")
	public String homeFun(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("totalStudent", repo.count());
		return "homePage";
	}

	@Autowired
	SaveStudentRepository repo;

	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";

	@RequestMapping("addStudent")
	@ResponseBody
	public String saveStudent(Student stu,@RequestParam("img") MultipartFile file) {
		
		StringBuilder fileNames = new StringBuilder();
		String filename=stu.getSid() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
		Path fileNameAndPath =Paths.get(uploadDirectory,filename);
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		stu.setSphoto(filename);
		repo.save(stu);
		return "Save Data Successfully ! ";
	}

	@RequestMapping("delStudent")
	public String delStudent(int sid) {
		repo.deleteById(sid);
		return "homePage";
	}

}
