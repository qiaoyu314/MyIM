package models;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import java.util.*;

import play.data.validation.Constraints.*;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;


@Entity
public class Medication extends Model{
	
	@Id
	public Long id;
	public String name;
	public boolean prescribed;
	public String source;
	public String category;
	public String prescriptionNum;
	public String strength;
	public String dose;
	public String unit;
	public String frequency;
	public int remaining;
	public Date startDate;
	public Date lastTakenDate;
	public boolean currentTaking = false;
	public String physician;
	public String condition;
	public String reasonForTaking;
	
	@ManyToOne
	public Pharmacy pharmacy;
	
	
	
//	@ManyToOne
//	@JoinColumn
//	public MedicalFile file;
	
	/**
	 * Find if there is a pharmacy matching the name and phone
	 * If there is, set it as the pharmacy of this medication
	 * If not, create a new one and set it.
	 * @param name
	 * @param phone
	 */
	public void setPharmacy(String name, String phone){
		Pharmacy pharmacy = Pharmacy.find.where().eq("name", name).eq("phone", phone).findUnique();
		if(pharmacy==null){
			pharmacy = new Pharmacy(name, phone);
			pharmacy.save();
		}
		this.pharmacy = pharmacy;		
	}

	/**
	 * Find if there is a pharmacy matching the name
	 * If there is, set it as the pharmacy of this medication
	 * If not, create a new one and set it. 
	 * @param name
	 */
	public void setPharmacy(String name){
		Pharmacy pharmacy = Pharmacy.find.where().eq("name", name).findUnique();
		if(pharmacy==null){
			pharmacy = new Pharmacy(name);
		}
		this.pharmacy = pharmacy;		
	}

	
	public static Finder<Long, Medication> find = new Finder(Long.class, Medication.class);
	
	public static List<Medication> all() {
		return find.all();
	}
	
	/**
	 * delete a medication by a given id
	 * @param id
	 */
	public static void delete(long id){
		find.ref(id).delete();
	}
	
	/**
	 * Delete all medication
	 */
	public static void deleteAll(){
		List<Medication> all = find.all();
		for(Medication a : all){
			a.delete();
		}
	}
	
	/**
	 * Get all current medicaion
	 * @return
	 */
	public static List<Medication> getAllCurrent(){
		return Medication.find.where().or(Expr.eq("currentTaking", "true"), Expr.eq("lastTakenDate", null)).findList();
	}
	
	public static List<Medication> getAllPrescribed(){
		return Medication.find.where().eq("prescribed", true).findList();
	}
	
}
