package ir.ounegh.nazdik;
import android.location.*;

public class mLocation extends Location
{
	private int id;
	private String name;
	private String phone;
	private String category;
	public mLocation (Location l){
		
		super(l);
	}
	public mLocation(){
		super("gps");
	}
	public mLocation(String p){
		super(p);
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getCategory()
	{
		return category;
	}

	@Override
	public String toString()
	{
		// TODO: Implement this method
		return name+"  "+category+"  "+id+"   "+phone+  super.toString();
	}
	public String jahat(Location l){
		String[]jahatha=new String[]{"شمال","شمال شرقی","شرق","جنوب شرقی","جنوب","جنوب غربی","غرب","شمال غربی"};
		int bear=(int)(l.bearingTo(this)/45);
		if(bear<0){
			return "";
		}
		return jahatha[bear];
	
	}
	
	public String faseleh(Location loc){
		int dis=(int) this.distanceTo(loc);
		
		return String.valueOf(dis);
	}
	 
}
