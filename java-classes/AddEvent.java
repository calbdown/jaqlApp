import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class AddEvent extends JPanel implements ActionListener
{
    private GridBagLayout gridBag;
    private GridBagConstraints c;
    private JButton saveBtn, clearBtn;
    JTextField eidTxt, cidTxt, ecidTxt, edescTxt, addressTxt, cityTxt, pcodeTxt;
    private JLabel eidLbl, cidLbl, ecidLbl, edescLbl, dateLbl, addressLbl, cityLbl, pcodeLbl;
    private Queries queries;
    private Event oneEvent;
    private int listSize;
    private ClientRegistration clientReg;
    private ArrayList<Event>list;
    private JComboBox selectECCmb;
    private ObservingTextField dateTxt;
    private DatePicker dp;
    public AddEvent()
    {
        super();
        setSize(500,230);
        
        gridBag = new GridBagLayout();
        setLayout(gridBag);
        c = new GridBagConstraints();
        queries = new Queries();
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
    
    public void createGUI()
    {
        eidLbl = new JLabel("Event ID:"); 
        eidTxt = new JTextField("new ID");
        eidTxt.setEditable(false);
        cidLbl = new JLabel("Client ID:");
        cidTxt = new JTextField("Client ID");
        cidTxt.setEditable(false);
        ecidLbl = new JLabel("Event Category ID:");
        selectECCmb = new JComboBox();
        selectECCmb.addActionListener(this);
        edescLbl = new JLabel("Event Description:");
        edescTxt = new JTextField();
        dateLbl = new JLabel("Date:");
        dateTxt = new ObservingTextField();
        dateTxt.setText("Select Date");
        dateTxt.addFocusListener(new FocusAdapter()
        {          
            public void focusLost(FocusEvent e) {
                if(dateTxt.getText().equals(""))
                {
                    dateTxt.setText("Select Date");
                }   
            }
        });
        dateTxt.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                dateTxt.setText("");
                String lang = null;
                final Locale locale = getLocale(lang);
                dp = new DatePicker(dateTxt, locale);
                Date selectedDate = dp.parseDate(dateTxt.getText());
                dp.setSelectedDate(selectedDate);
                dp.start(dateTxt);
            }
        });
                
        addressLbl = new JLabel("Address:");
        addressTxt = new JTextField();
        cityLbl = new JLabel("City:"); 
        cityTxt = new JTextField();
        pcodeLbl = new JLabel("Postcode:");
        pcodeTxt = new JTextField();
        ImageIcon save= new ImageIcon("save.png");
        saveBtn = new JButton (save);
        saveBtn.setToolTipText("Save Event");
        saveBtn.addActionListener(this);
        ImageIcon clear= new ImageIcon("clear.png");
        clearBtn = new JButton (clear);
        clearBtn.setToolTipText("Clear Event");
        clearBtn.addActionListener(this);
        
        addComponent(eidLbl,0,0,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(eidTxt,1,0,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(cidLbl,0,1,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cidTxt,1,1,1,1,1,10,
            GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(ecidLbl,0,2,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(selectECCmb,1,2,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(edescLbl,0,3,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(edescTxt,1,3,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
            
        addComponent(dateLbl,2,0,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(dateTxt,3,0,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(addressLbl,2,1,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(addressTxt,3,1,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(cityLbl,2,2,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(cityTxt,3,2,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(pcodeLbl,2,3,1,1,1,10,
            GridBagConstraints.NONE,GridBagConstraints.EAST);
        addComponent(pcodeTxt,3,3,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(saveBtn,1,4,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST);
        addComponent(clearBtn,3,4,1,1,1,10,
            GridBagConstraints.HORIZONTAL,GridBagConstraints.EAST);
    }
    
    private Locale getLocale(String loc)
    {
        if(loc!= null && loc.length()>0)
            return new Locale(loc);
        else
            return Locale.US;
    }
    
    public void showEventAt(int index)
    {
        if(index<listSize)
        {
            oneEvent = list.get(index);
           
            eidTxt.setText(""+oneEvent.getEventID());
            cidTxt.setText(""+oneEvent.getClientID());
            edescTxt.setText(oneEvent.getEventDesc());
            dateTxt.setText(oneEvent.getDate());
            addressTxt.setText(oneEvent.getAddress());
            cityTxt.setText(oneEvent.getCity());
            pcodeTxt.setText(oneEvent.getPostcode());           
        }
    }
    
    public void updateAllEvents()
    {
        queries.connectToDatabase();
        list = queries.getAllEventsByID();
        queries.disconnectFromDatabase();
        listSize = list.size();
    }
    
    public void updateCombo()
    {
        queries.connectToDatabase();
        queries.getEventCategoryToComboBox(selectECCmb);
        queries.disconnectFromDatabase();
    }
    
    public void clearEvent()
    {
        eidTxt.setText("new ID");
        cidTxt.setText("Client ID");
        edescTxt.setText("");
        dateTxt.setText("Select Date");
        addressTxt.setText("");
        cityTxt.setText("");
        pcodeTxt.setText("");    
        
        saveBtn.setEnabled(true);
    }
    
    public void saveEvent()
    { 
        if(clientReg.clientTable.getSelectedRowCount()!=1)
        {
            JOptionPane.showMessageDialog(null,
            "Select a single row!","User Information",
            JOptionPane.WARNING_MESSAGE);
            return;            
        }
        else
        {
            if(cidTxt.equals("Client ID") || edescTxt.getText().equals("") || dateTxt.getText().equals("Select Date")
                            || addressTxt.getText().equals("") || cityTxt.getText().equals("") || pcodeTxt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,
                "Please enter data in all text fields.","User Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            else
            {
                int reply = JOptionPane.showConfirmDialog(null, "Has deposit been received?","Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(reply == JOptionPane.NO_OPTION)
                {
                    return;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Saved");
                    int cID = Integer.parseInt(cidTxt.getText());
                    String getID = (String)selectECCmb.getSelectedItem();
                    getID = getID.split(",")[0];
                    int ecID = Integer.parseInt(getID);
                    queries.connectToDatabase();
                    queries.insertOneEvent(cID,ecID,edescTxt.getText(),dateTxt.getText(),
                                             addressTxt.getText(),cityTxt.getText(),pcodeTxt.getText());
                    queries.disconnectFromDatabase();
                    updateAllEvents();
                    showEventAt(listSize-1);
        
                    saveBtn.setEnabled(false); 
                }                 
            }
        }       
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==saveBtn)
        {
            saveEvent();
        }
        else if(e.getSource()==clearBtn)
        {
            clearEvent();
        }
    }
}
