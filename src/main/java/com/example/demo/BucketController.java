package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FileData;



@RestController
@RequestMapping("/storage/")
public class BucketController {

	private AmazonClient amazonClient;

    @Autowired
    BucketController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile[] files) {
    	boolean flag=true;
    	for(MultipartFile file:files) {
    		String url=this.amazonClient.uploadFile(file);
    		if(!url.isEmpty()) {
    			flag=true;
    		}
    		else {
    			flag=false;
    		}
    	}
    	if(flag==true) {
    		return "success";
    	}
    	else {
    		return "failure";
    	}  	
    }
    
    @PostMapping("/uploadData")
    public String uploadData(@ModelAttribute FileData file ) {
    	String url=this.amazonClient.uploadFile(file.getFile());
    	return file.getId();
    }
    
    
}
