package models;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;

import java.util.*;

import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class Medication {
	
	@Id
	public Long id;
	public String name;
	public float strength;
	public String unit;
	public String frequency;
	public Date beginDate;
	public Date lastTakenDate;
	public boolean currentTaking;
	public String physician;
	public String condition;
	public Pharmacy pharmacy;
	
	public static Finder<Long, Medication> find = new Finder(Long.class, Medication.class);
	
	public static List<Medication> all() {
		return find.all();
	}
	
	
	
}
