import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class AddClient extends JDialog implements ActionListener
{
    private GridBagLayout gridBag;
    private GridBagConstraints c;
    private JLabel idLbl, fnameLbl, lnameLbl,dobLbl, addressLbl,cityLbl, postcodeLbl,telNoLbl;
    private JTextField idTxt, fnameTxt, lnameTxt, addressTxt,cityTxt, postcodeTxt,telNoTxt;
    private JButton saveBtn, clearBtn;
    private Queries queries;
    private ArrayList<Client>list;
    private Client oneClient;
    private int listSize;
    private ClientRegistration clientReg;
    private Application app;
    ObservingTextField dobTxt;
    DatePicker dp;
    public AddClient()
    {
        super();
        this.setModal(true);
        setTitle("Add New Client");
        setSize(400,400);
        setResizable(false);
        this.setLocationRelativeTo(null);
        queries = new Queries();
        gridBag = new GridBagLayout();
        setLayout(gridBag);
        c= new GridBagConstraints();
        createGUI();
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
        idLbl = new JLabel("Client ID:");
        idTxt = new JTextField("new ID");
        idTxt.setEnabled(false);
        fnameLbl = new JLabel("First Name:");
        fnameTxt = new JTextField();
        lnameLbl = new JLabel("Last Name:");
        lnameTxt = new JTextField();
        dobLbl = new JLabel("DOB:");
        dobTxt = new ObservingTextField();
        dobTxt.setText("Select Date");
        dobTxt.addFocusListener(new FocusAdapter()
        {          
            public void focusLost(FocusEvent e) {
                if(dobTxt.getText().equals(""))
                {
                    dobTxt.setText("Select Date");
                }   
            }
        });
        dobTxt.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                dobTxt.setText("");
                String lang = null;
                final Locale locale = getLocale(lang);
                dp = new DatePicker(dobTxt, locale);
                Date selectedDate = dp.parseDate(dobTxt.getText());
                dp.setSelectedDate(selectedDate);
                dp.start(dobTxt);               
            }
        }
        );
        addressLbl = new JLabel("Address:");
        addressTxt = new JTextField();
        cityLbl = new JLabel("City:");
        cityTxt = new JTextField();
        postcodeLbl = new JLabel("Postcode:");
        postcodeTxt = new JTextField();
        telNoLbl = new JLabel("Telephone Number:");
        telNoTxt = new JTextField();
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        
        addComponent(idLbl,0,0,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(idTxt,1,0,9,1,90,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(fnameLbl,0,1,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(fnameTxt,1,1,9,1,10,100,
            GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(lnameLbl,0,2,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(lnameTxt,1,2,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(dobLbl,0,3,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(dobTxt,1,3,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(addressLbl,0,4,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(addressTxt,1,4,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(cityLbl,0,5,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cityTxt,1,5,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(postcodeLbl,0,6,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(postcodeTxt,1,6,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(telNoLbl,0,7,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(telNoTxt,1,7,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(saveBtn,1,8,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(clearBtn,2,8,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.EAST);
    }
    
    private Locale getLocale(String loc)
    {
        if(loc!= null && loc.length()>0)
            return new Locale(loc);
        else
            return Locale.US;
    }
    
    public void showClientAt(int index)
    {
        if(index<listSize)
        {
            oneClient = list.get(index);
           
            idTxt.setText(""+oneClient.getClientID());
            fnameTxt.setText(oneClient.getFirstName());
            lnameTxt.setText(oneClient.getLastName());
            dobTxt.setText(oneClient.getDOB());
            addressTxt.setText(oneClient.getAddress());
            cityTxt.setText(oneClient.getCity());
            postcodeTxt.setText(oneClient.getPostcode());
            telNoTxt.setText(oneClient.getTelNo());
        }
    }
    
    public void updateAllClients()
    {
        queries.connectToDatabase();
        list = queries.getAllClientsByID();
        queries.updateTable(clientReg.clientTable,queries.allClients);
        queries.disconnectFromDatabase();
        app.centerTable(clientReg.clientTable);
        listSize = list.size();
    }
    
    public void clearClient()
    {
        idTxt.setText("new ID");
        fnameTxt.setText(null);
        lnameTxt.setText(null);
        dobTxt.setText("Select Date");
        addressTxt.setText(null);
        cityTxt.setText(null);
        postcodeTxt.setText(null);
        telNoTxt.setText(null);
        
        saveBtn.setEnabled(true);
    }
    
    public void saveClient()
    {
        if(fnameTxt.equals("") || lnameTxt.getText().equals("") || dobTxt.getText().equals("Select Date") || addressTxt.getText().equals("")
                            || cityTxt.getText().equals("") || postcodeTxt.getText().equals("") || telNoTxt.getText().equals(""))
        {
           JOptionPane.showMessageDialog(null,
           "Please enter data in all text fields.","User Information", JOptionPane.WARNING_MESSAGE);
           return;
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Saved");
            queries.connectToDatabase();
            queries.insertOneClient(fnameTxt.getText(),lnameTxt.getText(),dobTxt.getText(),addressTxt.getText(),
                                     cityTxt.getText(),postcodeTxt.getText(),telNoTxt.getText());
            queries.disconnectFromDatabase();
            updateAllClients();
            showClientAt(listSize-1);

            saveBtn.setEnabled(false);   
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==saveBtn)
        {
            saveClient();
        }
        else if(e.getSource()==clearBtn)
        {
            clearClient();
        }
    }
}
