package com.logging.spring.SpringLogging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class Logger {
	 RawTestResul rawTestResult = null;
    public List<RawTestResul> getLogTests(Resource res) throws IOException, ParseException {
    	 
        List<String> lines = Files.readAllLines(Paths.get(res.getURI()),
                StandardCharsets.UTF_8);
       List<RawTestResul> listTestCaseLog = new ArrayList<>();
       String currentTestCase = "";
      
       
        for (String line : lines) {
            
            String[] words = line.split("\\t");
          
            
                if(words.length >=3){
            	
                	// Initiate Raw Test Case Log from each test case build Log
                	 if(words[2].contains("-->start testGroup=")
                			 &&currentTestCase != words[2] ){
                		 
                		 if(rawTestResult != null){
                			if(rawTestResult.getIsFailedTestCase() != "Failed"){
                				rawTestResult.setIsFailedTestCase("Success");
                			}
                		    listTestCaseLog.add(rawTestResult);
                		 //   System.out.println(rawTestResult.toString());
                		  // System.out.println();
                		 }

                	
                		 rawTestResult = new RawTestResul();
                		// rawTestResult.setName(words[2]);
                     } 
                	 
                	 
                     // Null pointer - on going - current Test case attributes           	 
                	 if(rawTestResult != null){
		              	    // rawTestResult.setDate(words[1]);
                		 
		              	     if(rawTestResult.getComments() != null)
		             	        rawTestResult.setComments(rawTestResult.getComments() + "\n" + words[2] );
		              	     else
		              	    	rawTestResult.setComments(words[2]);
		              	     
		                     // Checking whether Pass or Fail
		              	     if(words[2] != null && words[2].contains("java.lang.AssertionError") ){
		              	    	 
		              	    	 
		                 		rawTestResult.setIsFailedTestCase("Failed");
		            	     }
                	 }
                	 getTestName(words[2]);
                	 // Previous Test Case 
            	     if(words[2].contains("-->start testGroup=")){
            	    	 currentTestCase = words[2];
            	    	 rawTestResult.setTestGroupName(currentTestCase.substring(19,currentTestCase.length()));
            	     } 		 
              }
          
        }

        return listTestCaseLog;
    }
    
    public void getTestName(String comment){
    	String result = null;
    	if(comment != null ){
    		
    		if(comment.contains("test=")){
    		
    			//comment.replace("test=", "");
    			result = comment;
    			rawTestResult.setTestName(result.substring(5,result.length()));
    		}else
    		if(comment.contains("group tags=")){
        		
    			//comment.replace("test=", "");
    			result = comment;
    			rawTestResult.setGroupTags(result.substring(11, result.length()));
    		}else
              if(comment.contains("test tags=")){
        		
    			//comment.replace("test=", "");
    			result = comment;
    			rawTestResult.setTestTags(result.substring(11,result.length()));
    		}
    	}
    	
    	
    }
}
