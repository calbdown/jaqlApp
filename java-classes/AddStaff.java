import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class AddStaff extends JDialog implements ActionListener
{
    private GridBagLayout gridBag;
    private GridBagConstraints c;
    private JLabel idLbl, sCatLbl, fnameLbl, lnameLbl,dobLbl, addressLbl,cityLbl, postcodeLbl,telNoLbl, ninLbl;
    private JTextField idTxt, fnameTxt, lnameTxt, addressTxt,cityTxt, postcodeTxt,telNoTxt, ninTxt;
    private JButton saveBtn, clearBtn;
    private JComboBox sCatCmb;
    private Queries queries;
    private ArrayList<Staff>list;
    private Staff oneStaff;
    private int listSize;
    private ManageStaff staffReg;
    private Application app;
    private ObservingTextField dobTxt;
    private DatePicker dp;
    public AddStaff()
    {
        super();
        this.setModal(true);
        setTitle("Add New Staff");
        setSize(400,400);
        setResizable(false);
        this.setLocationRelativeTo(null);
        queries = new Queries();
        gridBag = new GridBagLayout();
        setLayout(gridBag);
        c= new GridBagConstraints();
        createGUI();
        updateCombo();
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
        idLbl = new JLabel("Staff ID:");
        idTxt = new JTextField("new ID");
        idTxt.setEnabled(false);
        sCatLbl = new JLabel("Staff Category ID:");
        sCatCmb = new JComboBox();
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
        ninLbl = new JLabel("NIN:");
        ninTxt = new JTextField();
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        
        addComponent(idLbl,0,0,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(idTxt,1,0,9,1,90,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(sCatLbl,0,1,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(sCatCmb,1,1,9,1,90,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(fnameLbl,0,2,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(fnameTxt,1,2,9,1,10,100,
            GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(lnameLbl,0,3,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(lnameTxt,1,3,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(dobLbl,0,4,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(dobTxt,1,4,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(addressLbl,0,5,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(addressTxt,1,5,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(cityLbl,0,6,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cityTxt,1,6,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(postcodeLbl,0,7,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(postcodeTxt,1,7,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(telNoLbl,0,8,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(telNoTxt,1,8,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(ninLbl,0,9,1,1,10,100,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(ninTxt,1,9,9,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(saveBtn,1,10,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(clearBtn,2,10,1,1,10,100,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.EAST);
    }
    
    private Locale getLocale(String loc)
    {
        if(loc!= null && loc.length()>0)
            return new Locale(loc);
        else
            return Locale.US;
    }
    
    public void showStaffAt(int index)
    {
        if(index<listSize)
        {
            oneStaff = list.get(index);
           
            idTxt.setText(""+oneStaff.getStaffID());
            fnameTxt.setText(oneStaff.getFirstName());
            lnameTxt.setText(oneStaff.getLastName());
            dobTxt.setText(oneStaff.getDOB());
            addressTxt.setText(oneStaff.getAddress());
            cityTxt.setText(oneStaff.getCity());
            postcodeTxt.setText(oneStaff.getPostcode());
            telNoTxt.setText(oneStaff.getTelNo());
        }
    }
    
    public void updateAllStaffs()
    {
        queries.connectToDatabase();
        list = queries.getAllStaffByID();
        queries.updateTable(staffReg.staffTable,queries.allStaffs);
        queries.disconnectFromDatabase();
        app.centerTable(staffReg.staffTable);
        listSize = list.size();
    }
    
    public void updateCombo()
    {
        queries.connectToDatabase();
        queries.getStaffCatToComboBox(sCatCmb);
        queries.disconnectFromDatabase();
    }
    
    public void clearStaff()
    {
        idTxt.setText("new ID");
        fnameTxt.setText(null);
        lnameTxt.setText(null);
        dobTxt.setText("Select Date");
        addressTxt.setText(null);
        cityTxt.setText(null);
        postcodeTxt.setText(null);
        telNoTxt.setText(null);
        ninTxt.setText(null);
        saveBtn.setEnabled(true);
    }
    
    public void saveStaff()
    {
        if(fnameTxt.equals("") || lnameTxt.getText().equals("") || dobTxt.getText().equals("Select Date") || addressTxt.getText().equals("")
           || cityTxt.getText().equals("") || postcodeTxt.getText().equals("") || telNoTxt.getText().equals("") || ninTxt.getText().equals(""))
        {
           JOptionPane.showMessageDialog(null,
           "Please enter data in all text fields.","User Information", JOptionPane.WARNING_MESSAGE);
           return;
        }
        else
        {
            String getID = (String)sCatCmb.getSelectedItem();
            getID = getID.split(",")[0];
            int staffCatID = Integer.parseInt(getID); 
            
            queries.connectToDatabase();
            queries.insertOneStaff(staffCatID,fnameTxt.getText(),lnameTxt.getText(),dobTxt.getText(),addressTxt.getText(),
                                     cityTxt.getText(),postcodeTxt.getText(),telNoTxt.getText(),ninTxt.getText());
            queries.disconnectFromDatabase();
            updateAllStaffs();
            showStaffAt(listSize-1);
            JOptionPane.showMessageDialog(null,"Saved");
            saveBtn.setEnabled(false);   
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==saveBtn)
        {
            saveStaff();
        }
        else if(e.getSource()==clearBtn)
        {
            clearStaff();
        }
    }
}
