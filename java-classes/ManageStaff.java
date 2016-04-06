import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class ManageStaff extends JPanel implements ActionListener
{
    private JPanel appContainer;
    private  CardLayout appCardLayout;
    
    private  DefaultTableModel staffTableModel;
    protected  static JTable staffTable;
    private  Queries queries;
    private  JScrollPane staffScroll;
    private  JTextField staffSearchTxt;
    private  JLabel searchLbl;
    private  JButton addBtn, deleteBtn, editBtn, mainMenuBtn;
    private  AddStaff addStaff;
    private  Application app;

    public ManageStaff(JPanel container, CardLayout cl, Application appTitle)
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
        
        searchLbl = new JLabel("Search for a staff: ");
        searchLbl.setSize(130,50);
        searchLbl.setLocation(10,10);
        this.add(searchLbl);
        
        staffSearchTxt = new JTextField("Staff First Name");
        staffSearchTxt.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e) {
                staffSearchTxt.setText(""); 
            }
            
            public void focusLost(FocusEvent e) {
                staffSearchTxt.setText("Staff First Name");
            }
        });
        staffSearchTxt.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                queries.searchForStaff(staffSearchTxt.getText()+"%",staffTable);
                app.centerTable(staffTable);
            }
        }
        );
        staffSearchTxt.setSize(130,25);
        staffSearchTxt.setLocation(140,25);
        this.add(staffSearchTxt);
               
        staffTableModel = new DefaultTableModel();
        staffTable = new JTable(staffTableModel);
        staffTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                rowClicked();
            }
        });
        staffTable.setBounds(10,60,770,383);
        staffTable.getTableHeader().setReorderingAllowed(false);
        queries.updateTable(staffTable,queries.allStaffs);
        app.centerTable(staffTable);
        staffScroll = new JScrollPane(staffTable);
        staffScroll.setBounds(staffTable.getBounds());
        add(staffScroll);
        
        ImageIcon add = new ImageIcon("add.gif");
        addBtn = new JButton(add);
        addBtn.setSize(87,34);
        addBtn.setLocation(10,443);
        addBtn.setToolTipText("Add new staff");
        this.add(addBtn);
        addBtn.addActionListener(this);
        
        ImageIcon edit = new ImageIcon("edit.gif");
        editBtn = new JButton(edit);
        editBtn.setSize(87,34);
        editBtn.setLocation(100,443);
        editBtn.setToolTipText("Edit staff");
        this.add(editBtn);
        editBtn.addActionListener(this);
        
        ImageIcon delete = new ImageIcon("delete.gif");
        deleteBtn = new JButton(delete);
        deleteBtn.setSize(87,34);
        deleteBtn.setLocation(190,443);
        deleteBtn.setToolTipText("Delete staff");
        this.add(deleteBtn);
        deleteBtn.addActionListener(this);
        
        ImageIcon mainMenu = new ImageIcon("mainMenu.png");
        mainMenuBtn = new JButton(mainMenu);
        mainMenuBtn.setToolTipText("Main Menu");
        mainMenuBtn.setSize(87,34);
        mainMenuBtn.setLocation(692,443);
        add(mainMenuBtn);
        mainMenuBtn.addActionListener(this);
        
        setVisible(true);
    }
    
    public void deletestaff()
    {
        if(staffTable.getSelectedRowCount()!=1)
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
                    int ID = (Integer)staffTable.getValueAt(staffTable.getSelectedRow(),0);
                   
                    queries.deleteOneStaff(ID);
                    queries.updateTable(staffTable,queries.allStaffs);
                    app.centerTable(staffTable);
                }
            }
    }
    
    public void updateStaff()
    {
        if(staffTable.getSelectedRowCount()!=1)
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
                int staffID=(Integer)staffTable.getValueAt(staffTable.getSelectedRow(),0);
                int staffCatID=(Integer)staffTable.getValueAt(staffTable.getSelectedRow(),1);
                queries.updateStaff(staffID,staffCatID,""+staffTable.getValueAt(staffTable.getSelectedRow(),2),""+staffTable.getValueAt(staffTable.getSelectedRow(),3),
                ""+staffTable.getValueAt(staffTable.getSelectedRow(),4),""+staffTable.getValueAt(staffTable.getSelectedRow(),5),
                ""+staffTable.getValueAt(staffTable.getSelectedRow(),6),""+staffTable.getValueAt(staffTable.getSelectedRow(),7),
                ""+staffTable.getValueAt(staffTable.getSelectedRow(),6),""+staffTable.getValueAt(staffTable.getSelectedRow(),7));
                queries.updateTable(staffTable,queries.allStaffs);
                app.centerTable(staffTable);
                
                JOptionPane.showMessageDialog(null,"The row has been updated!","User Information",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }        
    }
    
    public void rowClicked()
    {
        int selectedRow=staffTable.getSelectedRow();
        
        if(staffTable.getSelectedRowCount()!=1)
        {
            JOptionPane.showMessageDialog(null,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            int staffID=(Integer)staffTable.getValueAt(staffTable.getSelectedRow(),0);
            
        }
    }
    
    public void actionPerformed(ActionEvent e)
    { 
        if(e.getSource()==deleteBtn)
        {
            deletestaff();
        }
        else if(e.getSource()==addBtn)
        {
            addStaff = new AddStaff();
        }
        else if(e.getSource()==editBtn)
        {
            updateStaff();
        }
        else if(e.getSource()==mainMenuBtn)
        {
            appCardLayout.show(appContainer,"mainMenuPanel");
            app.setTitle("Menu");
        }
    }
}
