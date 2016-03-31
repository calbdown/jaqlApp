public class Event
{
    private int eventID, clientID, eventCatID;
    private String eventDesc, date, address, city, postcode;
    public Event(int eventID, int clientID, int eventCatID, String eventDesc, 
                 String date, String address, String city, String postcode)
    {
        this.eventID = eventID;
        this.clientID = clientID;
        this.eventCatID = eventCatID;
        this.eventDesc = eventDesc;
        this.date = date;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
    }
    
    public int getEventID()
    {
        return this.eventID ;
    }
    
    public int getClientID()
    {
        return this.clientID ;
    }
    
    public int getEventCatID()
    {
        return this.eventCatID ;
    }
    
    public String getEventDesc()
    {
        return this.eventDesc ;
    }
    
    public String getDate()
    {
        return this.date ;
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
}