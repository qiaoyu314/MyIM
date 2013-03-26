package controllers;

import java.util.Comparator;

import models.Medication;

public class DateComparator implements Comparator<Medication>{

	@Override
	public int compare(Medication a, Medication b) {
		// TODO Auto-generated method stub
		
		if(a.startDate.after(b.startDate))
			return 1;
		else if(a.startDate.before(b.startDate))
			return -1;
		else 
			return 0; 
	}

}
