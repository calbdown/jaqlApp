import java.sql.*;
import java.util.*;
import javax.swing.*;
import net.proteanit.sql.*;

public class Queries
{
    final private String url = "jdbc:mysql://localhost:3306/nameOfDatabase";
    final private String driver = "com.mysql.jdbc.Driver";
    final private String username = "root";
    
    private Connection conn = null;
    private Statement st = null;
    private String query = null;
    //ResultSet rs = null;
    String allStudents = "select student_ID as StudentID, student_first_name as FirstName, student_last_name as LastName,student_gender as Gender, student_DOB as DOB, "+
                               "student_address as Address, student_city as City, student_postcode as Postcode, student_tel_no as TelNo from Student";
    String allCourses ="Select course_ID as CourseID, staff_ID as StaffID, course_name as CourseName, course_Length as Length, fee as Fee from Course";
    
    String allClients ="Select client_ID as ClientID, client_first_name as FirstName, client_last_name as LastName, client_DOB as DOB, "+
                        "client_address as Address, client_city as City, client_postcode as Postcode, client_tel_no as TelNo from Client";
     
    String allEvents ="Select event_ID as EventID, client_ID as ClientID, event_category_ID as EventCategoryID, event_description as Description, "+
                      "event_date as Date, event_address as Address, event_city as City, event_postcode as Postcode from Event";
                      
    //String allGuards=//"Select staff_ID as GuardID, staff_first_name as FirstName, staff_last_name as LastName, staff_address as Address, "+
                       // "staff_city as City, staff_postcode as Postcode,staff_tel_no as TelNo from Staff where staff_category_ID=3";
                        
    String allStaffs = "Select staff_ID as StaffID, staff_category_ID as StaffCategoryID, staff_first_name as FirstName, staff_last_name as LastName, staff_DOB as DOB, "+
                       "staff_address as Address, staff_city as City, staff_postcode as Postcode, staff_tel_no as TelNo, NIN from Staff";
                           
    
    //manage student
    private PreparedStatement allStudentsByID = null;
    private PreparedStatement insertStudent = null;
    private PreparedStatement deleteStudent = null;
    private PreparedStatement updateStudent = null;
    //manage staff
    private PreparedStatement allStaffByID = null;
    private PreparedStatement insertStaff = null;
    private PreparedStatement deleteStaff = null;
    private PreparedStatement updateStaff = null;
    //manage course
    private PreparedStatement allCoursesByID = null;
    private PreparedStatement insertCourse = null;
    private PreparedStatement deleteCourse = null;
    private PreparedStatement updateCourse = null;
    //manage studentCourse
    private PreparedStatement insertStudentCourse = null;
    //manage client
    private PreparedStatement allClientsByID = null;
    private PreparedStatement insertClient = null;
    private PreparedStatement deleteClient = null;
    private PreparedStatement updateClient = null;
    
    //manage event
    private PreparedStatement allEventsByID = null;
    private PreparedStatement insertEvent = null;
    private PreparedStatement deleteEvent = null;
    
    private PreparedStatement insertGuardBooking = null;
    
    private PreparedStatement searchStudent = null;
    private PreparedStatement searchCourse = null;
    private PreparedStatement searchClient = null;
    private PreparedStatement searchEvent = null;
    private PreparedStatement searchStaff = null;
    private PreparedStatement staffToCombo = null;
    private PreparedStatement staffCatToCombo = null;
    private PreparedStatement allEventCat = null;
    private PreparedStatement guardIsBooked = null;
    private PreparedStatement updateGuardForEvent = null;
    private PreparedStatement allAvailableGuards = null;
    private String [] staff;
    private String [] staffCat;
    private String [] eventCat;
    public void connectToDatabase()
    {
        try
        {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, username,null);
            st = conn.createStatement();
            
            allStudentsByID = conn.prepareStatement("select * from Student");
            insertStudent = conn.prepareStatement("insert into student (student_first_name, student_last_name, student_gender,student_DOB,"+
                                                  " student_address, student_city, student_postcode, student_tel_no) values(?,?,?,?,?,?,?,?)");
            deleteStudent = conn.prepareStatement("delete from student where student_ID=?");
            updateStudent = conn.prepareStatement("UPDATE Student SET student_first_name=?, student_last_name=?, student_gender=?, "+
                                                  "student_DOB=?, student_address=?, student_city=?, student_postcode=?, "+
                                                  " student_tel_no=? WHERE student_ID=?");
            
            allStaffByID = conn.prepareStatement("select * from Staff");
            insertStaff = conn.prepareStatement("insert into Staff (staff_category_ID, staff_first_name, staff_last_name, staff_DOB, "+ 
                                                "staff_address, staff_city, staff_postcode, staff_tel_no, NIN) values(?,?,?,?,?,?,?,?,?)");
            deleteStaff = conn.prepareStatement("delete from Staff where staff_ID=?");
            updateStaff = conn.prepareStatement("UPDATE Staff SET staff_category_ID=?, staff_first_name=?, staff_last_name=?, "+
                                                  "staff_DOB=?, staff_address=?, staff_city=?, staff_postcode=?, "+
                                                  " staff_tel_no=?, nin=? WHERE staff_ID=?");
            
            allCoursesByID = conn.prepareStatement("Select * from Course");
            insertCourse = conn.prepareStatement("insert into Course(staff_ID, course_name, course_length, fee) values(?,?,?,?)");
            deleteCourse = conn.prepareStatement("delete from Course where course_ID = ?");
            updateCourse = conn.prepareStatement("UPDATE Course SET staff_ID =?, course_name = ?, course_length = ?, fee = ?  WHERE course_ID = ?");
            
            insertStudentCourse = conn.prepareStatement("insert into StudentCourse(student_ID,course_ID,course_joined)values(?,?,?)");
            
            allClientsByID = conn.prepareStatement("Select * from Client");
            insertClient = conn.prepareStatement("insert into Client(client_first_name, client_last_name, client_DOB, "+ 
                                                "client_address, client_city, client_postcode, client_tel_no) values(?,?,?,?,?,?,?)");
            deleteClient = conn.prepareStatement("delete from Client where client_ID = ?");
            updateClient = conn.prepareStatement("UPDATE Client SET client_first_name=?, client_last_name=?, "+
                                                  "client_DOB=?, client_address=?, client_city=?, client_postcode=?, "+
                                                  " client_tel_no=? WHERE client_ID=?");
            
            allEventsByID = conn.prepareStatement("select * from Event");
            insertEvent = conn.prepareStatement("insert into Event(client_ID, event_category_ID, event_description, event_date, event_address, "+
                                                "event_city, event_postcode) values(?,?,?,?,?,?,?)");
            deleteEvent = conn.prepareStatement("delete from Event where event_ID = ?");
            
            searchStudent = conn.prepareStatement("select student_ID as StudentID, student_first_name as FirstName, student_last_name as LastName, student_gender as Gender, student_DOB as DOB, "+
                               "student_address as Address, student_city as City, student_postcode as Postcode, student_tel_no as TelNo from Student where student_first_name Like ?");
            
            searchCourse = conn.prepareStatement("Select course_ID as CourseID, staff_ID as StaffID, course_name as CourseName, course_Length as Length, fee as Fee from Course where course_name Like ?");
            
            searchStaff = conn.prepareStatement("Select staff_ID as StaffID, staff_category_ID as StaffCategoryID, staff_first_name as FirstName, staff_last_name as LastName, staff_DOB as DOB, "+
                                                "staff_address as Address, staff_city as City, staff_postcode as Postcode, staff_tel_no as TelNo, NIN from Staff where staff_first_name Like ?");
            
            staffToCombo = conn.prepareStatement("Select staff_ID,staff_First_Name, staff_last_name from staff where staff_category_ID=2");
            
            staffCatToCombo = conn.prepareStatement("Select staff_category_ID,staff_category_description from StaffCategory");
            
            searchClient = conn.prepareStatement("Select client_ID as ClientID, client_first_name as FirstName, client_last_name as LastName, client_DOB as DOB, "+
                        "client_address as Address, client_city as City, client_postcode as Postcode, client_tel_no as TelNo from Client where client_first_name Like ?");
                  
            searchEvent = conn.prepareStatement("Select event_ID as EventID, client_ID as ClientID, event_category_ID as EventCategoryID, event_description as Description, "+
                      "event_date as Date, event_address as Address, event_city as City, event_postcode as Postcode from Event where event_date Like ?");
           
            allEventCat = conn.prepareStatement("Select * from EventCategory");
            
            insertGuardBooking = conn.prepareStatement("Insert into GuardBooking(event_ID, staff_ID, no_of_hours)values(?,?,?)");
            guardIsBooked= conn.prepareStatement("Select event_ID, staff_ID from GuardBooking where event_ID=?");
            
            updateGuardForEvent = conn.prepareStatement("UPDATE GuardBooking "+
                                                        "SET staff_ID=? "+
                                                        "WHERE event_ID=?");
                                                        
            allAvailableGuards = conn.prepareStatement("Select DISTINCT es.staff_ID as GuardID, es.staff_first_name as FirstName,es.staff_last_name as LastName, es.staff_address as Address, "+
                                                       "es.staff_city as City, es.staff_postcode as Postcode, es.staff_tel_no as TelNo FROM(SELECT gb.event_ID, s.*, e.event_date FROM "+
                                                       "(Event e LEFT JOIN GuardBooking gb ON e.event_ID=gb.event_ID) RIGHT JOIN Staff s ON s.staff_ID=gb.staff_ID "+
                                                       "Where s.staff_category_ID=3 and e.event_date IS NULL or e.event_date!=? )es WHERE es.staff_id!=?");

        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
            System.exit(1);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void disconnectFromDatabase()
    {
        try
        {
            conn.close();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
    }    
    
    public ArrayList<Student> getAllStudentsByID()
    {
        ArrayList<Student> list = new ArrayList<Student>();
        try
        {
            ResultSet rs = allStudentsByID.executeQuery();
            
            while(rs.next())
            {
             list.add(new Student(rs.getInt("student_ID"),rs.getString("student_first_name"),rs.getString("student_last_name"),
             rs.getString("student_gender"),rs.getString("student_DOB"),rs.getString("student_address"),rs.getString("student_city"),
             rs.getString("student_postcode"),rs.getString("student_tel_no")));
             
            }          
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return list;
    }
    
    public int insertOneStudent(String fname, String lname, String gender, String DOB, String address, String city, String postcode, String telNo)
    {
        int inserted=0;        
        try
        {
            insertStudent.setString(1,fname);
            insertStudent.setString(2,lname);
            insertStudent.setString(3,gender);
            insertStudent.setString(4,DOB);
            insertStudent.setString(5,address);
            insertStudent.setString(6,city);
            insertStudent.setString(7,postcode);
            insertStudent.setString(8,telNo);
          
            inserted = insertStudent.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public int deleteOneStudent(int studentID)
    {
        int deleted=0;       
        try
        {
            deleteStudent.setInt(1,studentID);
            deleted= deleteStudent.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return deleted;
    }
    
    public int updateStudent(int studID, String fname, String lname, String gender, String DOB, String address, String city, String postcode, String telNo)
    {
        int updated=0;        
        try
        {
            updateStudent.setString(1,fname);
            updateStudent.setString(2,lname);
            updateStudent.setString(3,gender);
            updateStudent.setString(4,DOB);
            updateStudent.setString(5,address);
            updateStudent.setString(6,city);
            updateStudent.setString(7,postcode);
            updateStudent.setString(8,telNo);
            updateStudent.setInt(9,studID);
            updated = updateStudent.executeUpdate();
        }
        catch(SQLException sqle)
        {
            //JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            //JOptionPane.WARNING_MESSAGE);
            sqle.printStackTrace();
        }
        return updated;
    }
    
    public int insertOneStudentCourse(int sID, int cID, String joined)
    {
        int inserted=0;        
        try
        {
            insertStudentCourse.setInt(1,sID);
            insertStudentCourse.setInt(2,cID);
            insertStudentCourse.setString(3,joined);
           
            inserted = insertStudentCourse.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public ArrayList<Staff> getAllStaffByID()
    {
        ArrayList<Staff> list = new ArrayList<Staff>();      
        try
        {
            ResultSet rs = allStaffByID.executeQuery();           
            while(rs.next())
            {
             list.add(new Staff(rs.getInt("staff_ID"),rs.getInt("staff_category_ID"),rs.getString("staff_first_name"),rs.getString("staff_last_name"),
             rs.getString("staff_DOB"),rs.getString("staff_address"),rs.getString("staff_city"),
             rs.getString("staff_postcode"),rs.getString("staff_tel_no"), rs.getString("NIN")));            
            }
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return list;
    }
    
    public int insertOneStaff(int staffCatID, String fname, String lname, String DOB, String address, String city, String postcode, String telNo, String NIN)
    {
        int inserted=0;        
        try
        {
            insertStaff.setInt(1,staffCatID);
            insertStaff.setString(2,fname);
            insertStaff.setString(3,lname);
            insertStaff.setString(4,DOB);
            insertStaff.setString(5,address);
            insertStaff.setString(6,city);
            insertStaff.setString(7,postcode);
            insertStaff.setString(8,telNo);
            insertStaff.setString(9,NIN);
          
            inserted = insertStaff.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public int deleteOneStaff(int staffID)
    {
        int deleted=0;      
        try
        {
            deleteStaff.setInt(1,staffID);
            deleted= deleteStaff.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return deleted;
    }
    
    public int updateStaff(int staffID, int staffCatID, String fname, String lname, String DOB, String address, String city, String postcode, String telNo, String NIN)
    {
        int updated=0;        
        try
        {
            updateStaff.setInt(1,staffCatID);
            updateStaff.setString(2,fname);
            updateStaff.setString(3,lname);
            updateStaff.setString(4,DOB);
            updateStaff.setString(5,address);
            updateStaff.setString(6,city);
            updateStaff.setString(7,postcode);
            updateStaff.setString(8,telNo);
            updateStaff.setString(9,NIN);
            updateStaff.setInt(10,staffID);
            updated = updateStaff.executeUpdate();
        }
        catch(SQLException sqle)
        {
            //JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            //JOptionPane.WARNING_MESSAGE);
            sqle.printStackTrace();
        }
        return updated;
    }
    
    public ArrayList<Course> getAllCoursesByID()
    {
        ArrayList<Course> list = new ArrayList<Course>();      
        try
        {
            ResultSet rs = allCoursesByID.executeQuery();           
            while(rs.next())
            {
             list.add(new Course(rs.getInt("course_ID"),rs.getInt("staff_ID"),rs.getString("course_name"),rs.getString("course_length"),
             rs.getDouble("fee")));
            }
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return list;
    }
    
    public int insertOneCourse(int staffID, String courseName, String courseLength, double fee)
    {
        int inserted=0;        
        try
        {
            insertCourse.setInt(1,staffID);
            insertCourse.setString(2,courseName);
            insertCourse.setString(3,courseLength);
            insertCourse.setDouble(4,fee);
          
            inserted = insertCourse.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public int deleteOneCourse(int courseID)
    {
        int deleted=0;        
        try
        {
            deleteCourse.setInt(1,courseID);
            deleted= deleteCourse.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return deleted;
    }
    
    public int updateCourse(int courseID, int staffID, String courseName, String length, String fee)
    {
        int updated=0;        
        try
        {
            updateCourse.setInt(1,staffID);
            updateCourse.setString(2,courseName);
            updateCourse.setString(3,length);
            updateCourse.setString(4,fee);
            updateCourse.setInt(5,courseID);
            updated = updateCourse.executeUpdate();
        }
        catch(SQLException sqle)
        {
            //JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            //JOptionPane.WARNING_MESSAGE);
            sqle.printStackTrace();
        }
        return updated;
    }
    
    public ArrayList<Client> getAllClientsByID()
    {
        ArrayList<Client> list = new ArrayList<Client>();       
        try
        {
            ResultSet rs = allClientsByID.executeQuery();            
            while(rs.next())
            {
             list.add(new Client(rs.getInt("client_ID"),rs.getString("client_first_name"),rs.getString("client_last_name"),
             rs.getString("client_DOB"),rs.getString("client_address"),rs.getString("client_city"),
             rs.getString("client_postcode"),rs.getString("client_tel_no")));             
            }           
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return list;
    }
    
    public int insertOneClient(String fname, String lname, String DOB, String address, String city, String postcode, String telNo)
    {
        int inserted=0;        
        try
        {
            insertClient.setString(1,fname);
            insertClient.setString(2,lname);
            insertClient.setString(3,DOB);
            insertClient.setString(4,address);
            insertClient.setString(5,city);
            insertClient.setString(6,postcode);
            insertClient.setString(7,telNo);
          
            inserted = insertClient.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public int deleteOneClient(int clientID)
    {
        int deleted=0;        
        try
        {
            deleteClient.setInt(1,clientID);
            deleted= deleteClient.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return deleted;
    }
    
    public int updateClient(int clientID, String fname, String lname, String DOB, String address, String city, String postcode, String telNo)
    {
        int updated=0;        
        try
        {
            updateClient.setString(1,fname);
            updateClient.setString(2,lname);
            updateClient.setString(3,DOB);
            updateClient.setString(4,address);
            updateClient.setString(5,city);
            updateClient.setString(6,postcode);
            updateClient.setString(7,telNo);
            updateClient.setInt(8,clientID);
            updated = updateClient.executeUpdate();
        }
        catch(SQLException sqle)
        {
            //JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            //JOptionPane.WARNING_MESSAGE);
            sqle.printStackTrace();
        }
        return updated;
    }
    
    public ArrayList<Event> getAllEventsByID()
    {
        ArrayList<Event> list = new ArrayList<Event>();      
        try
        {
            ResultSet rs = allEventsByID.executeQuery();            
            while(rs.next())
            {
             list.add(new Event(rs.getInt("event_ID"),rs.getInt("client_ID"),rs.getInt("event_category_ID"),rs.getString("event_description"),rs.getString("event_date"),
             rs.getString("event_address"),rs.getString("event_city"),rs.getString("event_postcode")));
            }
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return list;
    }
    
    public int insertOneEvent(int clientID, int eventCatID, String eventDesc, String date, String address, String city, String postcode)
    {
        int inserted=0;        
        try
        {
            insertEvent.setInt(1,clientID);
            insertEvent.setInt(2,eventCatID);
            insertEvent.setString(3,eventDesc);
            insertEvent.setString(4,date);
            insertEvent.setString(5,address);
            insertEvent.setString(6,city);
            insertEvent.setString(7,postcode);
          
            inserted = insertEvent.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public int deleteOneEvent(int eventID)
    {
        int deleted=0;        
        try
        {
            deleteEvent.setInt(1,eventID);
            deleted= deleteEvent.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return deleted;
    }
    
    public void updateTable(JTable t, String sql)
    {
        ResultSet rs;
        try
        {
            rs=st.executeQuery(sql);
            t.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void searchForStudent(String fname, JTable table)
    {
        try
        {
            searchStudent.setString(1,fname);     
            ResultSet rs = searchStudent.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void searchForCourse(String cname, JTable table)
    {
        try
        {
            searchCourse.setString(1,cname);     
            ResultSet rs = searchCourse.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void searchForClient(String cname, JTable table)
    {
        try
        {
            searchClient.setString(1,cname);     
            ResultSet rs = searchClient.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void searchForStaff(String sname, JTable table)
    {
        try
        {
            searchStaff.setString(1,sname);     
            ResultSet rs = searchStaff.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void searchForEvent(String date, JTable table)
    {
        try
        {
            searchEvent.setString(1,date);     
            ResultSet rs = searchEvent.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void getStaffToComboBox(JComboBox comb)
    {
        try
        {
            ResultSet rs = staffToCombo.executeQuery();
            while(rs.next())
            {
                staff =new String[] {rs.getString("staff_ID"),rs.getString("staff_first_name"),rs.getString("staff_last_name")};
                comb.addItem(staff[0]+", "+staff[1]+" "+staff[2]);
            }
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void getStaffCatToComboBox(JComboBox comb)
    {
        try
        {
            ResultSet rs = staffCatToCombo.executeQuery();
            while(rs.next())
            {
                staffCat =new String[] {rs.getString("staff_category_ID"),rs.getString("staff_category_description")};
                comb.addItem(staffCat[0]+", "+staffCat[1]);
            }
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void getEventCategoryToComboBox(JComboBox comb)
    {
        try
        {
            ResultSet rs = allEventCat.executeQuery();
            while(rs.next())
            {
                eventCat =new String[] {rs.getString("event_category_ID"),rs.getString("event_category_description")};
                comb.addItem(eventCat[0]+", "+eventCat[1]);
            }
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public int insertOneGuardBooking(int eID, int sID, int hours )
    {
        int inserted=0;        
        try
        {
            insertGuardBooking.setInt(1,eID);
            insertGuardBooking.setInt(2,sID);
            insertGuardBooking.setInt(3,hours);
           
            inserted = insertGuardBooking.executeUpdate();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,sqle.getMessage().toString(),"SQL Error",
            JOptionPane.WARNING_MESSAGE);
        }
        return inserted;
    }
    
    public void showIfGuardIsBooked(JTable eventTable,JTextField eIDTxt,JTextField gIDTxt,JButton add)
    {
        try
        {
            guardIsBooked.setInt(1,(Integer)eventTable.getValueAt(eventTable.getSelectedRow(),0));
                        
            ResultSet rs = guardIsBooked.executeQuery();
            //eIDTxt.setText(""+rs.getInt("event_ID"));
            //
            if(rs.next())
            {
                eIDTxt.setText(""+rs.getInt("event_ID"));  
                gIDTxt.setText(""+rs.getInt("staff_ID"));
                add.setEnabled(false);
            }
            else
            {
                //eIDTxt.setText("");  
                gIDTxt.setText("Guard ID");
                add.setEnabled(true);
            }
                                 
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void updateGuardForEvent(JTable eventTable,JTable guardTable,JTextField eIDTxt,JTextField gIDTxt,JButton add)
    {
        try
        {
            
            updateGuardForEvent.setInt(1,(Integer)guardTable.getValueAt(guardTable.getSelectedRow(),0));
            updateGuardForEvent.setInt(2,(Integer)eventTable.getValueAt(eventTable.getSelectedRow(),0));
            updateGuardForEvent.executeUpdate();
          
        }
         catch(Exception e)
        {  
            e.printStackTrace();
        }
    }
    
    public void updateGuardTable(JTable guardTable,JTable eventTable, PreparedStatement ps, JTextField guardIDTxt)
    {
        ResultSet rs;
        try
        {
            //ps.setString(1,""+eventTable.getValueAt(eventTable.getSelectedRow(),0));
            ps.setString(1,""+eventTable.getValueAt(eventTable.getSelectedRow(),4));
            ps.setString(2,guardIDTxt.getText());
            rs=ps.executeQuery();
            
            guardTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(SQLException e)
        {
            // JOptionPane.showMessageDialog(null,e.getMessage().toString(),"SQL Error",
            //JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }
}
