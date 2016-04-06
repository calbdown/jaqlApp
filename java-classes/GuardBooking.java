import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import  java.util.EventListener;
import javax.swing.table.*;
import java.util.*;
import javax.swing.event.*;
public class GuardBooking extends JPanel implements ActionListener
{
    JPanel appContainer;
    CardLayout appCardLayout;
    
    DefaultTableModel eventTableModel, guardTableModel;
    static JTable eventTable, guardTable;
    Queries queries;
    JScrollPane eventScroll, guardScroll;
    ObservingTextField eventSearchTxt;
    JLabel searchLbl, guardTableNameLbl, dateLbl, eventIDLbl, guardIDLbl, hoursLbl;
    JTextField eventIDTxt, guardIDTxt, hoursTxt;
    JButton addGuardToEventBtn, mainMenuBtn, editBtn;
    DatePicker dp;
    Application app;

    public GuardBooking(JPanel container, CardLayout cl, Application appTitle)
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
        
        searchLbl = new JLabel("Search for an event: ");
        searchLbl.setSize(130,50);
        searchLbl.setLocation(10,10);
        this.add(searchLbl);
        
        eventSearchTxt = new ObservingTextField();
        eventSearchTxt.setText("Search by Date");
        
        eventSearchTxt.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                eventSearchTxt.setText("");
                String lang = null;
                final Locale locale = getLocale(lang);
                dp = new DatePicker(eventSearchTxt, locale);
                Date selectedDate = dp.parseDate(eventSearchTxt.getText());
                dp.setSelectedDate(selectedDate);
                dp.start(eventSearchTxt);
                
            }
        });
        
        eventSearchTxt.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent arg0){}
            
            public void insertUpdate(DocumentEvent arg0) 
            {                
                queries.searchForEvent(eventSearchTxt.getText()+"%",eventTable);
                app.centerTable(eventTable);
            }

            public void removeUpdate(DocumentEvent arg0)
            {
                queries.searchForEvent(eventSearchTxt.getText()+"%",eventTable);
                app.centerTable(eventTable);
            }            
        });
        
        eventSearchTxt.setSize(130,25);
        eventSearchTxt.setLocation(140,25);
        this.add(eventSearchTxt);
        
        
        eventTableModel = new DefaultTableModel();
        eventTable = new JTable(eventTableModel);
        
        eventTable.setBounds(10,60,770,140);
        eventTable.getTableHeader().setReorderingAllowed(false);
        eventTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                rowClicked();
            }
        });
        queries.updateTable(eventTable,queries.allEvents);
        app.centerTable(eventTable);
        eventScroll = new JScrollPane(eventTable);
        eventScroll.setBounds(eventTable.getBounds());
        add(eventScroll);
        
        ImageIcon mainMenu = new ImageIcon("mainMenu.png");
        mainMenuBtn = new JButton(mainMenu);
        mainMenuBtn.setToolTipText("Main Menu");
        mainMenuBtn.setSize(87,34);
        mainMenuBtn.setLocation(692,425);
        add(mainMenuBtn);
        mainMenuBtn.addActionListener(this);
               
        guardTableNameLbl = new JLabel("Guards available on: ");
        guardTableNameLbl.setSize(130,50);
        guardTableNameLbl.setLocation(190,200);
        add(guardTableNameLbl);
        
        dateLbl = new JLabel("");
        dateLbl.setSize(80,50);
        dateLbl.setLocation(310,200);
        add(dateLbl);
        
        guardTableModel = new DefaultTableModel();
        guardTable = new JTable(guardTableModel);        
        guardTable.setBounds(190,235,589,164); 
        guardTable.getTableHeader().setReorderingAllowed(false);
        guardTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                guardTableRowClicked();
            }
        });
        app.centerTable(guardTable);
        guardScroll = new JScrollPane(guardTable);
        guardScroll.setBounds(guardTable.getBounds());
        add(guardScroll);
        
        eventIDLbl = new JLabel("Event ID:");
        eventIDLbl.setSize(60,20);
        eventIDLbl.setLocation(10,235);
        add(eventIDLbl);
        
        eventIDTxt = new JTextField("Event ID");
        eventIDTxt.setEditable(false);
        eventIDTxt.setSize(87,25);
        eventIDTxt.setLocation(80,235);
        add(eventIDTxt);
        
        guardIDLbl = new JLabel("Guard ID:");
        guardIDLbl.setSize(60,20);
        guardIDLbl.setLocation(10,265);
        add(guardIDLbl);
        
        guardIDTxt = new JTextField("Guard ID");
        guardIDTxt.setEditable(false);
        guardIDTxt.setSize(87,25);
        guardIDTxt.setLocation(80,265);
        add(guardIDTxt);
        
        hoursLbl = new JLabel("Hours:");
        hoursLbl.setSize(60,20);
        hoursLbl.setLocation(10,295);
        add(hoursLbl);
        
        hoursTxt = new JTextField();
        hoursTxt.setSize(87,25);
        hoursTxt.setLocation(80,295);
        add(hoursTxt);
        
        ImageIcon add = new ImageIcon("add.gif");
        addGuardToEventBtn = new JButton(add);
        addGuardToEventBtn.setSize(87,34);
        addGuardToEventBtn.setLocation(80,324);
        addGuardToEventBtn.setToolTipText("Add Guard To Event");
        this.add(addGuardToEventBtn);
        addGuardToEventBtn.addActionListener(this);
        
        
        ImageIcon edit = new ImageIcon("edit.gif");
        editBtn = new JButton(edit);
        editBtn.setSize(87,34);
        editBtn.setLocation(80,364);
        editBtn.setToolTipText("Update guard for event");
        this.add(editBtn);
        editBtn.addActionListener(this);
      
        
        setVisible(true);
    }
    
    private Locale getLocale(String loc)
    {
        if(loc!= null && loc.length()>0)
            return new Locale(loc);
        else
            return Locale.US;
    }
    
    private void rowClicked()
    {
        int selectedRow=eventTable.getSelectedRow();
        
        if(eventTable.getSelectedRowCount()!=1)
        {
            JOptionPane.showMessageDialog(null,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            String date=""+eventTable.getValueAt(eventTable.getSelectedRow(),4);
            dateLbl.setText(date);
            queries.showIfGuardIsBooked(eventTable,eventIDTxt,guardIDTxt,addGuardToEventBtn);
            queries.updateGuardTable(guardTable,eventTable,queries.allAvailableGuards,guardIDTxt);
            
            app.centerTable(guardTable);
            int eventID = (Integer)eventTable.getValueAt(eventTable.getSelectedRow(),0);
            eventIDTxt.setText(""+eventID); 
            if(addGuardToEventBtn.isEnabled())
            {
                editBtn.setEnabled(false);
            }
            else
            {
                editBtn.setEnabled(true);
            }
        }
    }
    
    private void guardTableRowClicked()
    {
        int selectedRow=guardTable.getSelectedRow();
        
        if(guardTable.getSelectedRowCount()!=1)
        {
            JOptionPane.showMessageDialog(null,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            if(addGuardToEventBtn.isEnabled()==true)
            {
                int guardID=(Integer)guardTable.getValueAt(guardTable.getSelectedRow(),0);
                guardIDTxt.setText(""+guardID);
            }            
        }
    }
    
    public void addGuardToEvent()
    {
        if(eventTable.getSelectedRowCount()!=1 || guardTable.getSelectedRowCount()!=1)
        { 
            JOptionPane.showMessageDialog(this,
                "Select a single row in both tables!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            if(hoursTxt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,
                "Please enter data in all text fields!","User information",
                JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                int reply = JOptionPane.showConfirmDialog(this, "Do you want to add the selected Guard to the selected Event?","Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(reply == JOptionPane.NO_OPTION)
                {
                    return;
                }
                else
                {                       
                    int eventID = (Integer)eventTable.getValueAt(eventTable.getSelectedRow(),0);
                    int guardID=(Integer)guardTable.getValueAt(guardTable.getSelectedRow(),0);
                    int hours = Integer.parseInt(hoursTxt.getText());
 
                    queries.insertOneGuardBooking(eventID,guardID,hours);
                    
                    JOptionPane.showMessageDialog(this,
                    "Guard with ID "+guardID+" is successfully added to event with ID "+eventID+"!","User Information",
                    JOptionPane.INFORMATION_MESSAGE);
                    
                    eventIDTxt.setText("Event ID");
                    guardIDTxt.setText("Guard ID");
                    hoursTxt.setText(null);
                }
            }           
        }
    }
    
    private void updateGuardForEvent()
    {
        if(eventTable.getSelectedRowCount()!=1 || guardTable.getSelectedRowCount()!=1)
        { 
            JOptionPane.showMessageDialog(this,
                "Select a single row in both tables!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                "You are about to update a row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            queries.showIfGuardIsBooked(eventTable,eventIDTxt,guardIDTxt,addGuardToEventBtn);
            queries.updateGuardForEvent(eventTable,guardTable,eventIDTxt,guardIDTxt,addGuardToEventBtn);
        }
        
    }
    
    public void actionPerformed(ActionEvent e)
    {    
        if(e.getSource()==addGuardToEventBtn)
        {
            addGuardToEvent();
        }
        if(e.getSource()==mainMenuBtn)
        {
            appCardLayout.show(appContainer,"mainMenuPanel");
            app.setTitle("Menu");
        }
        if(e.getSource()==editBtn)
        {
            updateGuardForEvent();
            rowClicked();
        }
    }
}
