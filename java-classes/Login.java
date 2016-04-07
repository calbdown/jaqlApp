import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener
{
    private JButton loginBtn, cancelBtn;
    private JLabel usernamelbl, passwordlbl, validationlbl;
    private JTextField usernametxt,passwordtxt;
    private final String USER_NAME = "yourusername";
    private final String PASSWORD = "yourpassword";
    //private JPasswordField passwordtxt;
    private Application app;
    public static void main(String [] args)
    {
        new Login();
    }
    
    public Login ()
    {
       super("Login Screen");
       
       setLayout(null);
       setSize(400,300);
       setLocation(450,200);
       
       usernamelbl = new JLabel("Username");
       usernamelbl.setSize(70,20);
       usernamelbl.setLocation(80,50);
       add(usernamelbl);
       
       usernametxt = new JTextField();
       usernametxt.setSize(130,20);
       usernametxt.setLocation(190,50);
       add(usernametxt);
       usernametxt.addActionListener(this);
       
       passwordlbl = new JLabel("Password");
       passwordlbl.setSize(70,20);
       passwordlbl.setLocation(80,100);
       add(passwordlbl);
       
       //passwordtxt = new JPasswordField();
       passwordtxt = new JTextField();
       passwordtxt.setSize(130,20);
       passwordtxt.setLocation(190,100);
       add(passwordtxt);
       
       loginBtn = new JButton("Login");
       loginBtn.setSize(80,30);
       loginBtn.setLocation(80,150);
       add(loginBtn);
       loginBtn.addActionListener(this);
       
       cancelBtn = new JButton("Cancel");
       cancelBtn.setSize(80,30);
       cancelBtn.setLocation(240,150);
       add(cancelBtn);
       cancelBtn.addActionListener(this);
       
       validationlbl = new JLabel();
       validationlbl.setSize(300,20);
       validationlbl.setLocation(80,125);
       validationlbl.setForeground(Color.RED);
       add(validationlbl);
       
       
       setVisible(true);
    }
   
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == loginBtn)
        {
           String user = usernametxt.getText();
           String password = passwordtxt.getText();
           if (user.equals(USER_NAME) && password.equals(PASSWORD) )
           {
               setVisible(false);
               JOptionPane.showMessageDialog(null,"Welcome to the application!","WELCOME",
               JOptionPane.INFORMATION_MESSAGE);
               
               app = new Application();
           }
           else if (user !=USER_NAME && password.equals(PASSWORD))
           {
               validationlbl.setText("Wrong username, Please try again.");
           }
           else if (user.equals(USER_NAME) && password !=PASSWORD)
           {
               validationlbl.setText("Wrong password,  Please try again.");
           }
           else
           {
                validationlbl.setText("Wrong credentials, Please try again.");
           }
        }
        else if(e.getSource()==cancelBtn)
        {
            System.exit(0);
        }
    }    
}
