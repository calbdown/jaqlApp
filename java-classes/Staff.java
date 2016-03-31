public class Staff
{
    private int staffID, staffCatID;
    private String fname, lname, DOB, address, city, postcode, telNo, NIN;
    public Staff(int staffID, int staffCatID, String fname, String lname, String DOB, 
    String address, String city, String postcode, String telNo, String NIN)
    {
        this.staffID = staffID;
        this.staffCatID = staffCatID;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.telNo = telNo;
        this.NIN = NIN;
    }
    
    public int getStaffID()
    {
        return this.staffID;
    }
    
    public int getStaffCatID()
    {
        return this.staffCatID;
    }
    
    public String getFirstName()
    {
       return this.fname;
    }
   
    public String getLastName()
    {
       return this.lname;
    }
   
    public String getDOB()
    {
       return this.DOB;
    }
    
    public String getAddress()
    {
        return this.address ;
    }
    
    public String getCity()
    {
        return this.city ;
    }
    
    public String getPostcode()
    {
        return this.postcode ;
    }
   
    public String getTelNo()
    {
       return this.telNo;
    }
   
    public String getNIN()
    {
       return this.NIN;
    }
}


