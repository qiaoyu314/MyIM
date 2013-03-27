package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.h2.value.Value;

import com.avaje.ebean.Expr;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import models.*;
import play.data.*;
import views.html.*;

public class Application extends Controller {

//	static Form<Task> taskForm = Form.form(Task.class);

	public static Result index() {
		return redirect(routes.Application.allMedication());
	}

//	public static Result tasks() {
//		return ok(index.render(Task.all(), taskForm));
//	}

//	public static Result newTask() {
//		Form<Task> filledForm = taskForm.bindFromRequest();
//		if (filledForm.hasErrors()) {
//			return badRequest(views.html.index.render(Task.all(), filledForm));
//		} else {
//			Task.create(filledForm.get());
//			return redirect(routes.Application.tasks());
//		}
//	}

//	public static Result deleteTask(Long id) {
//		Task.delete(id);
//		return redirect(routes.Application.tasks());
//	}

	public static Result allMedication() {

		
		List<Medication> l = Medication.all();
		return ok(allMedication.render(l));
	}
	
	public static Result deleteAllMedication(){
		Medication.deleteAll();
		return redirect(routes.Application.allMedication());
	}

	public static Result allCurrent() {
		List<Medication> l  = Medication.getAllCurrent();		
		return ok(allCurrent.render(l));
	}
	
	public static Result allPrescribed(){
		List<Medication> l = Medication.getAllPrescribed();
		return ok(allPrescribed.render(l));
	}
	
	public static Result sortByCondition(){
		return TODO;
	}
	
	public static Result sortByDate(){
		List<Medication> allMedication = Medication.all();
		Collections.sort(allMedication, new DateComparator());
		ArrayList<Medication> before2010 = new ArrayList<Medication>();
		ArrayList<Medication> in2010 = new ArrayList<Medication>();
		ArrayList<Medication> in2011 = new ArrayList<Medication>();
		ArrayList<Medication> in2012 = new ArrayList<Medication>();
		ArrayList<Medication> in2013 = new ArrayList<Medication>();
		for(Medication m : allMedication){
			int year = m.startDate.getYear() + 1900;
			if(year<2010){
				before2010.add(m);
			}else if(year == 2010){
				in2010.add(m);
			}else if(year == 2011){
				in2011.add(m);
			}else if(year == 2012){
				in2012.add(m);
			}else if(year == 2013){
				in2013.add(m);
			}
		}
		return ok(soryByDate.render(before2010, in2010, in2011, in2012, in2013));
	}
	
	
	public static Result refillMedication(){
		return TODO;
	}
	
	public static Result enterOTCDrugs(){
		return TODO;
	}
	
	public static Result enterPharmacyInfo(){
		return TODO;
	}
	
	public static Result uploadTab() {
		String message = flash("uploadInfo");

		return ok(upload.render(message, MedicalFile.find.all()));
	}
	
	
	
	/**
	 * POST method to upload file
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Result upload() throws FileNotFoundException {

		// get data from form
		MultipartFormData body = request().body().asMultipartFormData();
		Map<String, String[]> values = body.asFormUrlEncoded();
		String alias = values.get("name")[0];
		String source = values.get("source")[0];
		Date uploadDate = new Date();
		FilePart fp = body.getFile("data");

		// check if the name is alreday been used
		if (MedicalFile.find.where().eq("alias", alias).findList().size() > 0) {
			flash("uploadInfo", "This name already exists!");
			return redirect(routes.Application.uploadTab());
		} else {

			if (fp != null) {
				// String fileName = fp.getFilename();
				// String contentType = fp.getContentType();
				File file = fp.getFile();
				file.renameTo(new File("data/" + alias));
				//extract the medication information
				DataExtractor d = new DataExtractor("data/" + alias);
				List<Medication> medicationList =  d.getMedication();
				MedicalFile mf = new MedicalFile(alias, source, uploadDate, medicationList);
				mf.save();				
				flash("uploadInfo", "File is uploaded successfully!");	
//				return redirect(routes.Application.uploadTab());
				return redirect("/uploadTab");
			} else {
				flash("uploadInfo", "Missing file!");
				return redirect(routes.Application.uploadTab());
			}
		}
		
		
	}
	
	public static Result deleteFile(long id){
		//delete db and file
		MedicalFile.delete(id);
		return redirect(routes.Application.uploadTab());
	}
	


}
