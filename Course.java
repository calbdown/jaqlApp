public class Course
{
    private int courseID, staffID;
    private String courseName, courseLength; 
    private double fee;
    
    public Course(int courseID, int staffID, String courseName, String courseLength, double fee)
    {
        this.courseID = courseID;
        this.staffID = staffID;
        this.courseName = courseName;
        this.courseLength = courseLength;
        this.fee = fee;
    }
    
    public int getCourseID()
    {
        return this.courseID;
    }
    
    public int getStaffID()
    {
        return this.staffID;
    }
    
    public String getCourseName()
    {
        return this.courseName;
    }
    
    public String getCourseLength()
    {
        return this.courseLength;
    }
    
    public double getFee()
    {
        return this.fee;
    }
}














