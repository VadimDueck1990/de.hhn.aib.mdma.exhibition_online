package com.hhn.vs.centerapp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.UUID;

import com.hhn.vs.centerapp.model.SemesterDate;

/**
 * Data loader for the dummy data or hard coded data
 * @author Felix Edel
 *
 */
@Component
public class DataLoader {
	private static final Logger logger = LogManager.getLogger(DataLoader.class);
	 
	 /**
	  * Loads semester dates into a list
	  * @return List of semester date
	  */
	 public List<SemesterDate> loadSemesterDate() {
		logger.debug("Load dummydata");
		
		List<SemesterDate> list = new ArrayList<>();
		try {
			list = readCSV();
			return list;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	 }

	/**
	 * Reads all semester date objects out from the csv file
	 */
	private List<SemesterDate> readCSV() throws FileNotFoundException {
		logger.debug("Get all data from csv file");
		
    	List<SemesterDate> listEvent = new ArrayList<SemesterDate>();
    	
    	List<List<String>> records = new ArrayList<List<String>>();
		InputStream is = this.getClass().getResourceAsStream("/SemesterplanSS2019.csv");
    	Scanner scanner = new Scanner( new InputStreamReader(is));
    	try {
    	    while (scanner.hasNextLine()) {
    	        records.add(getRecordFromLine(scanner.nextLine()));
    	    }
    	    
    	    //Remove metadata
    	    records.remove(0);
    	    
    	    for(List<String> list : records) {
    	    	String dateBegin = null, dateEnd = null, title = null, description = null;
    	    	for(int index = 0; index <= list.size(); index++) {
    	    		switch(index) {
    	    		  case 0:
    	    		    break;
    	    		  case 1:
    	    		    dateBegin = list.get(index);
    	    		    break;
    	    		  case 2:
      	    		    if(list.get(index)!="null") {
      	    		    	dateEnd = list.get(index);
      	    		    }
						break;
					  case 3:
						if(list.get(index)!="null"){
							title = list.get(index);
						}
      	    		    break;
					  case 4:
						if(list.get(index)!="null"){
							description = list.get(index);
						}
						break;
    	    		  default:
    	    		    // code block
    	    		}    	    		
				}
				String id = UUID.randomUUID().toString();
    	    	listEvent.add(new SemesterDate(id, dateBegin, dateEnd, title, description, "SemesterDate", createTimestamp(), createTimestamp()));
    	    }
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    	}
    	scanner.close();
		return listEvent;
	}

	/**
	 * Gets record from line
	 * @param line The string of the line
	 * @return A list of strings
	 */
	private static List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}
	
	/**
	 * Creates a timestamp based on ISO8601 of the current date and time
	 * @return Timestamp string
	 */
	private static String createTimestamp() {
		logger.debug("Create a ISO8601 timestamp");
		
		TimeZone tz = TimeZone.getTimeZone("Europe/Berlin");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(new Date());
	}
}
