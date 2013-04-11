package models;

import java.util.List;

import play.db.ebean.*;
import javax.persistence.*;


@Entity
public class Pharmacy extends Model{
	@Id
	public long id;
	public String name;
	public String phone;
	public String location;
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE})
	public List<Medication> medicationList;
	
	public Pharmacy(String name, String phone){
		this.name = name;
		this.phone = phone;
		this.save();
	}
	
	public Pharmacy(String name){
		this.name = name;
		this.phone = "000-000-0000";
		this.save();
	}
	
	public static Model.Finder<Long,Pharmacy> find = new Model.Finder(Long.class, Pharmacy.class);

	public static List<Pharmacy> getAllPharmacy(){
		return find.all();
	}


}
