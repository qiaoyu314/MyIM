package models;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.Model;


@Entity
public class MedicalFile extends Model{
	
	@Id
	public long id;
	public String alias;
//	public String fileName;
	public String source;
	public Date uploadDate;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="file_id", referencedColumnName="id")
	public List<Medication> medicationList = new ArrayList<Medication>();
	
	public MedicalFile(String alias, String source, Date uploadDate, List<Medication> medicationList){
		this.alias = alias;
//		this.fileName = fileName;
		this.source = source;
		this.uploadDate = uploadDate;
		this.medicationList = medicationList;
	}
	
	public static Finder<Long, MedicalFile> find = new Finder(Long.class, MedicalFile.class);
	
	public static void delete(long id){		
		MedicalFile mf = find.byId(id);
		String fileName = mf.alias;
		mf.delete();
		File file = new File("data/"+fileName);
		if(file.exists())
			file.delete();
	}

}
