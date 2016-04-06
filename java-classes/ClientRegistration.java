import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;

public class ClientRegistration extends JPanel implements ActionListener
{
    private JPanel appContainer;
    private CardLayout appCardLayout;
    
    private DefaultTableModel clientTableModel;
    static JTable clientTable;
    private Queries queries;
    private JScrollPane clientScroll;
    private JTextField clientSearchTxt;
    private JLabel searchLbl;
    private JButton addBtn, deleteBtn, editBtn, mainMenuBtn;
    private AddClient addClient;
    private Application app;
    private AddEvent addEvent;
    public ClientRegistration(JPanel container, CardLayout cl, Application appTitle)
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
        
        searchLbl = new JLabel("Search for a client: ");
        searchLbl.setSize(130,50);
        searchLbl.setLocation(10,10);
        this.add(searchLbl);
        
        clientSearchTxt = new JTextField("Client  First Name");
        clientSearchTxt.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e) {
                clientSearchTxt.setText(""); 
            }
            
            public void focusLost(FocusEvent e) {
                clientSearchTxt.setText("Client First Name");
            }
        });
        clientSearchTxt.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                queries.searchForClient(clientSearchTxt.getText()+"%",clientTable);
                app.centerTable(clientTable);
            }
        }
        );
        clientSearchTxt.setSize(130,25);
        clientSearchTxt.setLocation(140,25);
        this.add(clientSearchTxt);
        
        
        clientTableModel = new DefaultTableModel();
        clientTable = new JTable(clientTableModel);
        clientTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                rowClicked();
            }
        });
        clientTable.setBounds(10,60,770,140);
        clientTable.getTableHeader().setReorderingAllowed(false);
        queries.updateTable(clientTable,queries.allClients);
        app.centerTable(clientTable);
        clientScroll = new JScrollPane(clientTable);
        clientScroll.setBounds(clientTable.getBounds());
        add(clientScroll);
        
        ImageIcon add = new ImageIcon("add.gif");
        addBtn = new JButton(add);
        addBtn.setSize(87,34);
        addBtn.setLocation(10,200);
        addBtn.setToolTipText("Add new Client");
        this.add(addBtn);
        addBtn.addActionListener(this);
        
        ImageIcon edit = new ImageIcon("edit.gif");
        editBtn = new JButton(edit);
        editBtn.setSize(87,34);
        editBtn.setLocation(100,200);
        editBtn.setToolTipText("Edit Client");
        this.add(editBtn);
        editBtn.addActionListener(this);
        
        ImageIcon delete = new ImageIcon("delete.gif");
        deleteBtn = new JButton(delete);
        deleteBtn.setSize(87,34);
        deleteBtn.setLocation(190,200);
        deleteBtn.setToolTipText("Delete Client");
        this.add(deleteBtn);
        deleteBtn.addActionListener(this);
        
        ImageIcon mainMenu = new ImageIcon("mainMenu.png");
        mainMenuBtn = new JButton(mainMenu);
        mainMenuBtn.setToolTipText("Main Menu");
        mainMenuBtn.setSize(87,34);
        mainMenuBtn.setLocation(692,443);
        add(mainMenuBtn);
        mainMenuBtn.addActionListener(this);
        
        addEvent = new AddEvent();
        addEvent.setLocation(0,260);
        add(addEvent);
        
        setVisible(true);
    }
    
    public void deleteClient()
    {
        if(clientTable.getSelectedRowCount()!=1)
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
                    int ID = (Integer)clientTable.getValueAt(clientTable.getSelectedRow(),0);
                   
                    queries.deleteOneClient(ID);
                    queries.updateTable(clientTable,queries.allClients);
                    app.centerTable(clientTable);
                }
            }
    }
    
    public void updateClient()
    {
        if(clientTable.getSelectedRowCount()!=1)
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
                int clientID=(Integer)clientTable.getValueAt(clientTable.getSelectedRow(),0);
                queries.updateClient(clientID,""+clientTable.getValueAt(clientTable.getSelectedRow(),1),
                ""+clientTable.getValueAt(clientTable.getSelectedRow(),2),""+clientTable.getValueAt(clientTable.getSelectedRow(),3),""+clientTable.getValueAt(clientTable.getSelectedRow(),4),
                ""+clientTable.getValueAt(clientTable.getSelectedRow(),5),""+clientTable.getValueAt(clientTable.getSelectedRow(),6),""+clientTable.getValueAt(clientTable.getSelectedRow(),7));
                queries.updateTable(clientTable,queries.allClients);
                app.centerTable(clientTable);
                
                JOptionPane.showMessageDialog(null,"The row has been updated!","User Information",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }        
    }
    
    public void rowClicked()
    {
        int selectedRow=clientTable.getSelectedRow();
        
        if(clientTable.getSelectedRowCount()!=1)
        {
            JOptionPane.showMessageDialog(null,
                "Select a single row!","User Information",
                JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            int clientID=(Integer)clientTable.getValueAt(clientTable.getSelectedRow(),0);
            addEvent.cidTxt.setText(""+clientID);
        }
    }
    
    public void actionPerformed(ActionEvent e)
    { 
        if(e.getSource()==deleteBtn)
        {
            deleteClient();
        }
        else if(e.getSource()==addBtn)
        {
            addClient = new AddClient();
        }
        else if(e.getSource()==editBtn)
        {
            updateClient();
        }
        else if(e.getSource()==mainMenuBtn)
        {
            appCardLayout.show(appContainer,"mainMenuPanel");
            app.setTitle("Menu");
        }
    }
}
