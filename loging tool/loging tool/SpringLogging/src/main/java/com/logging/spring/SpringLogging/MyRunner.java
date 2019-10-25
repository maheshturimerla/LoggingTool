package com.logging.spring.SpringLogging;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Value("classpath:thermopylae.log")
    private Resource res;
    @Autowired
    public Logger countWords;
    
    private static final String FILE_HEADER = "testGroupName,testName,groupTags,Status";
    private static final String COMMA_DELIMITER = ",";
    @Override
    public void run(String... args) throws Exception {
    	 
    List<RawTestResul> comments =  countWords.getLogTests(res);
    String csvFile = "C:\\My backup\\springlog\\New folder (3)\\SpringLogging\\SpringLogging\\src\\main\\resources\\report.csv";
    FileWriter writer = new FileWriter(csvFile);
    writer.append(FILE_HEADER.toString());
    writer.append("\n");
    StringBuilder sb = new StringBuilder();
    for(RawTestResul raw: comments)
    {
    	
    	if(raw.isFailedTestCase.equals("Failed"))
    	{
    		sb.append("\"");
    		sb.append(raw.testGroupName.toString());
    		sb.append("\"");
    		sb.append(COMMA_DELIMITER);
    		sb.append("\"");
    		sb.append(raw.testName.toString());
    		sb.append("\"");
    		sb.append(COMMA_DELIMITER);
    		sb.append("\"");
    		sb.append(raw.groupTags.toString());
    		sb.append("\"");
    		sb.append(COMMA_DELIMITER);
    		sb.append("\"");
    		sb.append(raw.isFailedTestCase.toString());
    		sb.append("\"");
    		sb.append(COMMA_DELIMITER);
    		sb.append("\n");
    		
    	}
    	
    	
    }
    System.out.println("i am  failed tests cases===="+sb.toString());
    writer.append(sb.toString());
   
    }
    
    
}