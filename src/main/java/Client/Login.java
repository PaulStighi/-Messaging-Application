import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login {

	private JFrame frame;
	public JTextField textField;
	public JTextField textField_1;
	private JTextField textField_2;
    
	public String Username;
 	
	public String Password;
	
	public int ok=0;
	
	public int ok2=0;
	
	public int k=0;
	
	public int k2=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(55, 49, 90, 30);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(55, 135, 90, 30);
		frame.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(183, 53, 116, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(183, 139, 116, 22);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(117, 192, 97, 25);
		frame.getContentPane().add(btnLogin);
		
		textField_2 = new JTextField();
		textField_2.setBounds(283, 193, 116, 22);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(311, 174, 56, 16);
		frame.getContentPane().add(lblMessage);
	}
	
public void  SetUsernameAndPassword() {
		
		this.Username=textField.getText();
		this.Password=textField_1.getText();
	}
private void doLogin() {
 
    try {
        if (client.login(login, password)) {
            // bring up the user list window
            UserListPane userListPane = new UserListPane(client);
            JFrame frame = new JFrame("User List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 600);

            frame.getContentPane().add(userListPane, BorderLayout.CENTER);
            frame.setVisible(true);

            setVisible(false);
        } else {
            // show error message
            JOptionPane.showMessageDialog(this, "Invalid login/password.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/*
private void StoreUsername() {
	String[]Users={"usr_1","usr_2","usr_3"};
	
	String[]Passwords= { "pass_1","pass_2","pass_3" }; 
	
	for(int i=0;i<Users.length;i++) {
		if(Users[i]==this.Username) {
		//	return Users[i];
			 ok=1;
		}
		else
		{
		   k=1;
		}
	}
	if(k==1) {
		
	}
		
		for(int j=0;j<Passwords.length;j++) {
			if(Passwords[j]==this.Password) {
			//	return Passwords[j];
				 ok2=1;
				
			}
			else
			{
				k2=1;
			}
			
		}
		if(k2==1) {
		System.out.println("incorrect password");
		}
		
		if(ok == 1 && ok2 == 1) {
			System.out.println("Login OK!");
		}
}*/

	
	
	
	
	
}
