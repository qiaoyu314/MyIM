package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import models.Medication;

public class DataExtractor {

	private BufferedReader reader;

	/**
	 * Constructor
	 * 
	 * @param fileName
	 */
	public DataExtractor(String fileName) {
		try {

			this.reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataExtractor(File file){
		try {
			this.reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Medication> getMedication() {
		List<Medication> medicationList = new ArrayList<Medication>();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
		String line = new String();
		String[] content = null;
		try {
			while ((line = reader.readLine()) != null) {
				// detect the null line (can't use String.match("\\s+"))
				if (line.length() < 2)
					continue;
				// detect "VA MEDICATION HISTORY" part
				else if(line.matches("^-+\\s*VA MEDICATION HISTORY.*")){
					Medication m = null;
					
					while ((line = reader.readLine()) != null) {
						// when reaching next part, break
						if (line.matches("^-+\\s*.*")){
							reader.reset();
							break;
						}
						// when reaching null line, continue
						else if(line.length() < 2) {
							continue;
						} else{
							// parse the medication info
							content = line.split(":\\s");
							switch(content[0]){
							case "Medication":
								m = new Medication();
								m.name = content[1].toLowerCase();
								m.prescribed = true;
								break;
							case "Instructions":
								m.frequency = content[1].toLowerCase();
								break;
							case "Status":
								m.currentTaking = true;
								break;
							case "Refills Remaining":
								m.remaining = Integer.parseInt(content[1]);
								break;
							case "Last Filled On":
								
								break;
							case "Initially Ordered On":
								m.startDate = format.parse(content[1]);
								break;
							case "Quantity":
								
								break;
							case "Days Supply":
								
								break;
							case "Pharmacy":
								m.setPharmacy(content[1]);
								break;
							case "Prescription Number":
								m.prescriptionNum = content[1];
								medicationList.add(m);
//								m.save();
								reader.mark(999);
								break;							
							}
						}
					}
				}
				// detect "medications and supplements" part
				else if(line.matches("^-+\\s*MEDICATIONS.*")) {
					// System.out.println("Got it!");
					// System.out.println(line);
					// begin to read medication info
					
					Medication m = null;
					
					while((line = reader.readLine()) != null) {
						// when reaching next part, break
						if (line.matches("^-+\\s*.*"))
							break;
						// when reaching null line, continue
						else if (line.length() < 2) {
							continue;
						} else {
							// parse the medication info
							content = line.split(":\\s");
							switch (content[0]) {
//							case "Source":
//								i++;
//								System.out.println("This is "+i);
//								m = new Medication();
//								m.source = content[1];
//								break;
							case "Category":
								m = new Medication();
								m.category = content[1];
								m.prescribed = false;
								break;
							case "Drug Name":
								m.name = content[1];
								break;
							case "Prescription Number":
								m.prescriptionNum = content[1];
								break;
							case "Strength":
								m.strength = content[1];
								break;
							case "Dose":
								m.dose = content[1];
								break;
							case "Frequency":
								m.frequency = content[1];
								break;
							case "Start Date":
								String[] date = content[1].split("\\s{2,}");
								m.startDate = format.parse(date[0]);	
								if(content[2].length()>3)
									m.lastTakenDate = format.parse(content[2]);
								break;
							case "Pharmacy Name":
								String[] pharmacyInfo = content[1].split("\\s{2,}");
								if(content.length==3)
									m.setPharmacy(pharmacyInfo[0], content[2]);
								break;
							case "Reason for taking":
								m.reasonForTaking = content[1];
//								m.save();
								medicationList.add(m);
								break;
							default:
								break;
							}
						}

					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return medicationList;
	}
}
