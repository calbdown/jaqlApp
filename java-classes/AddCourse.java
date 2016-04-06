import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class AddCourse extends JDialog implements ActionListener
{
    private GridBagLayout gridBag;
    private GridBagConstraints c;
    private JLabel cidLbl,sidLbl,cnameLbl,clengthLbl,feeLbl;
    private JTextField cidTxt,cnameTxt,clengthTxt,feeTxt;
    private JButton saveBtn, clearBtn;
    private Queries queries;
    private ArrayList<Course>list;
    private Course oneCourse;
    private int listSize;
    private StudentRegistration studReg;
    private JComboBox selectStaffCmb;
    private Application app;
    public AddCourse()
    {
        super();
        this.setModal(true);
        setTitle("Add New Course");
        setSize(400,400);
        setResizable(false);
        this.setLocationRelativeTo(null);
        queries = new Queries();
        gridBag = new GridBagLayout();
        setLayout(gridBag);
        c= new GridBagConstraints();
        
        createGUI();
        
        updateCombo();
        //queries.updateTable(studReg.studentTable);
        setVisible(true);
    }
   
    public void addComponent(Component comp, int gx, int gy,int gw, int gh, int wx, int wy, int fill, int anchor)
    {
        c.gridx=gx;
        c.gridy=gy;
        c.gridwidth = gw;
        c.gridheight = gh;
        c.weightx= wx;
        c.weighty = wy;
        c.fill = fill;
        c.anchor = anchor;
        c.insets = new Insets(5,5,5,5);
        gridBag.setConstraints(comp,c);
        
        this.add(comp);
    }
    
    private void createGUI()
    {
        cidLbl = new JLabel("Course ID:");
        cidTxt = new JTextField("new ID");
        cidTxt.setEnabled(false);
        sidLbl = new JLabel("Staff ID:");
        selectStaffCmb = new JComboBox();
        selectStaffCmb.addActionListener(this);
        cnameLbl = new JLabel("Course Name:");
        cnameTxt = new JTextField();
        clengthLbl = new JLabel("Course Length:");
        clengthTxt = new JTextField();
        feeLbl = new JLabel("Fee(£):");
        feeTxt = new JTextField();
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        
        addComponent(cidLbl,0,0,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cidTxt,1,0,9,1,90,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(sidLbl,0,1,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(selectStaffCmb,1,1,9,1,10,100,
            GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(cnameLbl,0,2,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cnameTxt,1,2,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(clengthLbl,0,3,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(clengthTxt,1,3,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(feeLbl,0,4,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(feeTxt,1,4,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(saveBtn,1,5,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(clearBtn,2,5,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.EAST);
    }
    
    public void showCourseAt(int index)
    {
        if(index<listSize)
        {
            oneCourse = list.get(index);
            cidTxt.setText(""+oneCourse.getCourseID());
            cnameTxt.setText(oneCourse.getCourseName());
            clengthTxt.setText(oneCourse.getCourseLength());
            feeTxt.setText(""+oneCourse.getFee());
        }
    }
    
    public void updateAllCourses()
    {
        queries.connectToDatabase(); 
        list = queries.getAllCoursesByID();
        queries.updateTable(studReg.courseTable,queries.allCourses);
        queries.disconnectFromDatabase();
        app.centerTable(studReg.courseTable);
        listSize = list.size();      
    }
    
    public void updateCombo()
    {
        queries.connectToDatabase();
        queries.getStaffToComboBox(selectStaffCmb);
        queries.disconnectFromDatabase();
    }
    
    public void clearCourse()
    {
        cidTxt.setText("new ID");
        cnameTxt.setText(null);
        clengthTxt.setText(null);
        feeTxt.setText(null);
       
        saveBtn.setEnabled(true);
    }
    
    public void saveCourse()
    {
        if(cidTxt.equals("")  || cnameTxt.getText().equals("") || clengthTxt.getText().equals("")
         || feeTxt.getText().equals("") )
        {
           JOptionPane.showMessageDialog(null,
           "Please enter data in all text fields.","User Information", JOptionPane.WARNING_MESSAGE);
           return;
        }
        else
        {
            
            String getID = (String)selectStaffCmb.getSelectedItem();
            getID = getID.split(",")[0];
            int staffID = Integer.parseInt(getID);           
            double fee = Double.parseDouble(feeTxt.getText());
            queries.connectToDatabase();
            queries.insertOneCourse(staffID,cnameTxt.getText(),clengthTxt.getText(),fee);
            queries.disconnectFromDatabase();
            updateAllCourses();
            showCourseAt(listSize-1);
             
            saveBtn.setEnabled(false);   
            JOptionPane.showMessageDialog(null,"Saved");
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==saveBtn)
        {
            saveCourse();
        }
        else if(e.getSource()==clearBtn)
        {
            clearCourse();
        }
    }
}
