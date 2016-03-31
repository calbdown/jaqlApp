public class Client
{
   private int clientID;
   private String fname, lname, DOB, address, city, postcode, telNo;
   public Client(int clientID, String fname, String lname, String DOB, 
                 String address, String city, String postcode, String telNo)
    {
        this.clientID = clientID;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.telNo = telNo;
    }
   
   public int getClientID()
   {
       return this.clientID;
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
}
