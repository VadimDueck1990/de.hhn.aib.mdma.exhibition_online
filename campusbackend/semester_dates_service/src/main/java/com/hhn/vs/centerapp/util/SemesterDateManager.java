package com.hhn.vs.centerapp.util;

import java.util.List;

import com.hhn.vs.centerapp.model.SemesterDate;

/**
 * Semester Date Manager Class
 * @author Felix Edel
 *
 */
public class SemesterDateManager {
	private static SemesterDateManager single_instance = null; 
	
	private List<SemesterDate> semesterDateList = null;
	
	/**
	 * Private constructor of the singleton
	 */
	private SemesterDateManager() {
		DataLoader dataLoader = new DataLoader();
		semesterDateList = dataLoader.loadSemesterDate();
	}
	
	/**
     * Gets the single instance of this class
     * @return SemesterDateManager instance
	 */
	public static SemesterDateManager getInstance() {
		if(single_instance == null) {
			single_instance = new SemesterDateManager();
		}
		return single_instance;
	}
	
	/**
	 * Gets a list of semester date objects
	 * @return List of semester date
	 */
	public List<SemesterDate> getSemesterDateList() {
		return semesterDateList;
	}
}
