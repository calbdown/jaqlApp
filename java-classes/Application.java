import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class Application extends JFrame implements ActionListener
{
    private CardLayout cardLayout;
    private JPanel container, mainMenuPanel;
    private JButton studentRegBtn, clientRegBtn, guardBookBtn, staffBtn;
    private StudentRegistration studentRegistration;
    private ClientRegistration clientRegistration;
    private GuardBooking guardBooking;
    private ManageStaff manageStaff;
    public Application()
    {
       super("Menu");
       
       setSize(800,520);
       setResizable(false);
       this.setLocationRelativeTo(null);
       cardLayout = new CardLayout();
       container = new JPanel();
       container.setLayout(cardLayout);
       createMainMenuPanel();
       
       studentRegistration = new StudentRegistration(container,cardLayout,this);
       clientRegistration = new ClientRegistration(container,cardLayout,this);
       guardBooking = new GuardBooking(container,cardLayout,this);
       manageStaff = new ManageStaff(container,cardLayout,this);
       
       container.add(mainMenuPanel,"mainMenuPanel");
       
       this.add(container);
       setVisible(true);
    }
   
    public  void createMainMenuPanel()
    {
       mainMenuPanel = new JPanel();
       mainMenuPanel.setLayout(null);
       mainMenuPanel.setSize(700,520);
       mainMenuPanel.setLocation(0,0);
      
       //ImageIcon studentReg = new ImageIcon(".png");
       studentRegBtn = new JButton("Manage Student/Course");
       studentRegBtn.setSize(300,170);
       studentRegBtn.setLocation(20,20);
       mainMenuPanel.add(studentRegBtn);
       studentRegBtn.addActionListener(this);
       
       clientRegBtn = new JButton("Manage Client/Event");
       clientRegBtn.setSize(300,170);
       clientRegBtn.setLocation(472,20);
       mainMenuPanel.add(clientRegBtn);
       clientRegBtn.addActionListener(this);
       
       guardBookBtn = new JButton("Guard Booking");
       guardBookBtn.setSize(300,170);
       guardBookBtn.setLocation(20,220);
       mainMenuPanel.add(guardBookBtn);
       guardBookBtn.addActionListener(this);
       
       staffBtn= new JButton("Manage Staff");
       staffBtn.setSize(300,170);
       staffBtn.setLocation(472,220);
       mainMenuPanel.add(staffBtn);
       staffBtn.addActionListener(this);
    }
   
    public static void centerTable(JTable table)
    {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        int i=0;
        while(i<table.getColumnCount())
        {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            i++;
        }
    }
    
    public void actionPerformed(ActionEvent e)
    { 
        if(e.getSource()==studentRegBtn)
        {
            container.add(studentRegistration,"studentReg");
            cardLayout.show(container,"studentReg");
            setTitle("Manage Students and Courses");
        }
        if(e.getSource()==clientRegBtn)
        {
            container.add(clientRegistration,"clientReg");
            cardLayout.show(container,"clientReg");
            setTitle("Manage Clients and Events");
        }
        if(e.getSource()==guardBookBtn)
        {
            container.add(guardBooking,"guardBooking");
            cardLayout.show(container,"guardBooking");
            setTitle("Guard Booking");
        }
        if(e.getSource()==staffBtn)
        {
            container.add(manageStaff,"manageStaff");
            cardLayout.show(container,"manageStaff");
            setTitle("Manage Staff");
        }
    }
    
    public static void main(String [] args)
    {
        Application app = new Application();
        
    }
}