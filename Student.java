public class Student
{
    private int studentID;
    private String fname, lname, gender, DOB, address, city, postcode, telNo;
    public Student(int studentID, String fname, String lname, String gender, String DOB, 
    String address, String city, String postcode, String telNo)
    {
        this.studentID = studentID;
        this.fname = fname;
        this.lname = lname;
        this. gender = gender;
        this.DOB = DOB;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.telNo= telNo;
    }
    
    public int getStudentID()
    {
        return this.studentID;
    }
    
    public String getFirstName()
    {
       return this.fname;
    }
   
    public String getLastName()
    {
       return this.lname;
    }
   
    public String getGender()
    {
       return this.gender;
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
}
