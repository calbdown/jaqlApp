import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class StudentRegistration extends JPanel implements ActionListener
{
    private JPanel appContainer;
    private CardLayout appCardLayout;
    
    private DefaultTableModel studentTableModel, courseTableModel;
    static JTable studentTable, courseTable;
    private Queries queries;
    private JScrollPane studentScroll, courseScroll;
    private JTextField courseSearchTxt,studentSearchTxt;
    private JLabel searchLbl,searchCourseLbl;
    private JButton addBtn, deleteBtn, editBtn, addStudToCourseBtn, addCourseBtn, editCourseBtn, deleteCourseBtn, mainMenuBtn;
    private AddStudent addStudent;
    private Application app;
    private AddCourse addCourse;

    public StudentRegistration(JPanel container, CardLayout cl, Application appTitle)
    {
        super();
        appContainer = container;
        appCardLayout = cl;
        app=appTitle;
        
        queries = new Queries();
        queries.connectToDatabase();
        
        createGUI();
    }
    
    public void createGUI()
    {
        this.setLayout(null);
        this.setSize(800,520);
        this.setLocation(0,0);
        
        searchLbl = new JLabel("Search for a student: ");
        searchLbl.setSize(130,50);
        searchLbl.setLocation(10,10);
        this.add(searchLbl);
        
        studentSearchTxt = new JTextField("Student First Name");
        studentSearchTxt.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e) {
                studentSearchTxt.setText(""); 
            }
            
            public void focusLost(FocusEvent e) {
                studentSearchTxt.setText("Student First Name");
            }
        });
        studentSearchTxt.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                queries.searchForStudent(studentSearchTxt.getText()+"%",studentTable);
                app.centerTable(studentTable);
            }
        }
        );
        studentSearchTxt.setSize(130,25);
        studentSearchTxt.setLocation(140,25);
        this.add(studentSearchTxt);
        
        
        studentTableModel = new DefaultTableModel();
        studentTable = new JTable(studentTableModel);
        
        studentTable.setBounds(10,60,770,140);
        studentTable.getTableHeader().setReorderingAllowed(false);
        queries.updateTable(studentTable,queries.allStudents);
        app.centerTable(studentTable);
        studentScroll = new JScrollPane(studentTable);
        studentScroll.setBounds(studentTable.getBounds());
        add(studentScroll);
        
        ImageIcon add = new ImageIcon("add.gif");
        addBtn = new JButton(add);
        addBtn.setSize(87,34);
        addBtn.setLocation(10,200);
        addBtn.setToolTipText("Add new student");
        this.add(addBtn);
        addBtn.addActionListener(this);
        
        ImageIcon edit = new ImageIcon("edit.gif");
        editBtn = new JButton(edit);
        editBtn.setSize(87,34);
        editBtn.setLocation(100,200);
        editBtn.setToolTipText("Edit student");
        this.add(editBtn);
        editBtn.addActionListener(this);
        
        ImageIcon delete = new ImageIcon("delete.gif");
        deleteBtn = new JButton(delete);
        deleteBtn.setSize(87,34);
        deleteBtn.setLocation(190,200);
        deleteBtn.setToolTipText("Delete student");
        this.add(deleteBtn);
        deleteBtn.addActionListener(this);
        
        ImageIcon studToCourse = new ImageIcon("studentToCourse.png");
        addStudToCourseBtn = new JButton(studToCourse);
        addStudToCourseBtn.setToolTipText("Add Student to Course");
        addStudToCourseBtn.setSize(87,34);
        addStudToCourseBtn.setLocation(280,200);
        add(addStudToCourseBtn);
        addStudToCourseBtn.addActionListener(this);
        
        ImageIcon addCourse = new ImageIcon("addCourse.gif");
        addCourseBtn = new JButton(addCourse);
        addCourseBtn.setSize(87,34);
        addCourseBtn.setLocation(10,425);
        addCourseBtn.setToolTipText("Add new Course");
        this.add(addCourseBtn);
        addCourseBtn.addActionListener(this);
        
        ImageIcon editCourse = new ImageIcon("editCourse.gif");
        editCourseBtn = new JButton(editCourse);
        editCourseBtn.setSize(87,34);
        editCourseBtn.setLocation(100,425);
        editCourseBtn.setToolTipText("Edit Course");
        this.add(editCourseBtn);
        editCourseBtn.addActionListener(this);
        
        ImageIcon deleteCourse = new ImageIcon("delete.gif");
        deleteCourseBtn = new JButton(deleteCourse);
        deleteCourseBtn.setSize(87,34);
        deleteCourseBtn.setLocation(190,425);
        deleteCourseBtn.setToolTipText("Delete Course");
        this.add(deleteCourseBtn);
        deleteCourseBtn.addActionListener(this);
        
        ImageIcon mainMenu = new ImageIcon("mainMenu.png");
        mainMenuBtn = new JButton(mainMenu);
        mainMenuBtn.setToolTipText("Main Menu");
        mainMenuBtn.setSize(87,34);
        mainMenuBtn.setLocation(692,425);
        add(mainMenuBtn);
        mainMenuBtn.addActionListener(this);
        
        searchCourseLbl = new JLabel("Search for a course: ");
        searchCourseLbl.setSize(130,50);
        searchCourseLbl.setLocation(10,236);
        this.add(searchCourseLbl);
        
        courseSearchTxt = new JTextField("Course Name");
        courseSearchTxt.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e) {
                courseSearchTxt.setText(""); 
            }
            
            public void focusLost(FocusEvent e) {
                courseSearchTxt.setText("Course Name");
            }
        });
        courseSearchTxt.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                queries.searchForCourse(courseSearchTxt.getText()+"%",courseTable);
                app.centerTable(courseTable);
            }
        }
        );
        courseSearchTxt.setSize(130,25);
        courseSearchTxt.setLocation(140,250);
        this.add(courseSearchTxt);
              
        courseTableModel = new DefaultTableModel();
        courseTable = new JTable(courseTableModel);
        
        courseTable.setBounds(10,285,770,140);
        courseTable.getTableHeader().setReorderingAllowed(false);
        queries.updateTable(courseTable,queries.allCourses);
        app.centerTable(courseTable);
        courseScroll = new JScrollPane(courseTable);
        courseScroll.setBounds(courseTable.getBounds());
        add(courseScroll);
        
        setVisible(true);
    }     
    
    public void deleteStudent()
    {
        if(studentTable.getSelectedRowCount()!=1)
            {
                JOptionPane.showMessageDialog(this,
                    "Select a single row!","User Information",
                    JOptionPane.WARNING_MESSAGE);
                return;            
            }
            else
            {
                int reply = JOptionPane.showConfirmDialog(this, "Do you really want to delete this record?","Delete Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(reply == JOptionPane.NO_OPTION)
                {
                    return;
                }
                else
                {
                    int item = (Integer)studentTable.getValueAt(studentTable.getSelectedRow(),0);
                   
                    queries.deleteOneStudent(item);
                    queries.updateTable(studentTable,queries.allStudents);
                    app.centerTable(studentTable);
                }
            }
    }
    
    public void updateStudent()
    {
        if(studentTable.getSelectedRowCount()!=1)
        { 
            JOptionPane.showMessageDialog(this,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        { 
            int reply1 = JOptionPane.showConfirmDialog(this, "Do you really want to update this row?","Confirmation", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(reply1== JOptionPane.NO_OPTION)
            {
                return;
            }
            else
            {
                int studID=(Integer)studentTable.getValueAt(studentTable.getSelectedRow(),0);
                queries.updateStudent(studID,""+studentTable.getValueAt(studentTable.getSelectedRow(),1),
                ""+studentTable.getValueAt(studentTable.getSelectedRow(),2),""+studentTable.getValueAt(studentTable.getSelectedRow(),3),""+studentTable.getValueAt(studentTable.getSelectedRow(),4),
                ""+studentTable.getValueAt(studentTable.getSelectedRow(),5),""+studentTable.getValueAt(studentTable.getSelectedRow(),6),""+studentTable.getValueAt(studentTable.getSelectedRow(),7),
                ""+studentTable.getValueAt(studentTable.getSelectedRow(),8));
                queries.updateTable(studentTable,queries.allStudents);
                app.centerTable(studentTable);
                
                JOptionPane.showMessageDialog(null,"The row has been updated!","User Information",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }        
    }
    
    public void deleteCourse()
    {
        if(courseTable.getSelectedRowCount()!=1)
            {
                JOptionPane.showMessageDialog(this,
                    "Select a single row!","User Information",
                    JOptionPane.WARNING_MESSAGE);
                return;            
            }
            else
            {
                int reply = JOptionPane.showConfirmDialog(this, "Do you really want to delete this record?","Delete Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(reply == JOptionPane.NO_OPTION)
                {
                    return;
                }
                else
                {
                    int item = (Integer)courseTable.getValueAt(courseTable.getSelectedRow(),0);
                   
                    queries.deleteOneCourse(item);
                    queries.updateTable(courseTable,queries.allCourses);
                    app.centerTable(courseTable);
                }
            }
    }
    
    public void updateCourse()
    {
        if(courseTable.getSelectedRowCount()!=1)
        { 
            JOptionPane.showMessageDialog(this,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        { 
            int reply1 = JOptionPane.showConfirmDialog(this, "Do you really want to update this row?","Confirmation", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(reply1== JOptionPane.NO_OPTION)
            {
                return;
            }
            else
            {
                int courseID=(Integer)courseTable.getValueAt(courseTable.getSelectedRow(),0);
                int staffID=(Integer)courseTable.getValueAt(courseTable.getSelectedRow(),1);
                queries.updateCourse(courseID,staffID,""+courseTable.getValueAt(courseTable.getSelectedRow(),2),
                ""+courseTable.getValueAt(courseTable.getSelectedRow(),3),""+courseTable.getValueAt(courseTable.getSelectedRow(),4));
              
                queries.updateTable(courseTable,queries.allCourses);
                app.centerTable(courseTable);
                
                JOptionPane.showMessageDialog(null,"The row has been updated!","User Information",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }        
    }
    
    public void addStudentToCourse()
    {
        if(studentTable.getSelectedRowCount()!=1 || courseTable.getSelectedRowCount()!=1)
        { 
            JOptionPane.showMessageDialog(this,
                "Select a single row in both tables!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            int reply = JOptionPane.showConfirmDialog(this, "Do you want to add the selected Student to the selected Course?","Confirmation", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(reply == JOptionPane.NO_OPTION)
            {
                return;
            }
            else
            {
                int reply1 = JOptionPane.showConfirmDialog(this, "Has the Student paid for the Course?","Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(reply1== JOptionPane.NO_OPTION)
                {
                    return;
                }
                else
                {
                    int studentID = (Integer)studentTable.getValueAt(studentTable.getSelectedRow(),0);
                    int courseID = (Integer)courseTable.getValueAt(courseTable.getSelectedRow(),0);
                    Calendar javaCalendar = null;
                    String currentDate=null;
                    javaCalendar = Calendar.getInstance();
                    currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
                    
                    queries.insertOneStudentCourse(studentID,courseID,currentDate);
                    
                    JOptionPane.showMessageDialog(this,
                    "Student with ID "+studentID+" is successfully added to course with ID "+courseID+"!","User Information",
                    JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    public void actionPerformed(ActionEvent e)
    { 
        if(e.getSource()==deleteBtn)
        {
            deleteStudent();
        }
        else if(e.getSource()==editBtn)
        {
            updateStudent();
        }
        else if(e.getSource()==addBtn)
        {
            addStudent = new AddStudent();
        }
        else if(e.getSource()==addStudToCourseBtn)
        {
            addStudentToCourse();
        }
        
        else if(e.getSource()==mainMenuBtn)
        {
            appCardLayout.show(appContainer,"mainMenuPanel");
            app.setTitle("Menu");
        }
        else if(e.getSource()==deleteCourseBtn)
        {
            deleteCourse();
        }
        else if(e.getSource()==editCourseBtn)
        {
            updateCourse();
        }
        else if(e.getSource()==addCourseBtn)
        {
            addCourse = new AddCourse();            
        }
    }
}
