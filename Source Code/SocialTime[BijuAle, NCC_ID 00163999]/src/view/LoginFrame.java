package view;

import controller.SocialTime;
import controller.UserController;
import dao.DAO;
import dao.DAOEvent;
import dao.SimpleDAOFactory;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame implements ActionListener {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    JButton btnLogin, btnRegister;
    RegistrationFrame regFrame;
    UserController userController = SocialTime.getUserController();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginFrame() {
        setSize(250, 225);
        setTitle("Social Time - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(102, 0, 51));
        getContentPane().setLayout(null);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(this);

        JLabel lblNewLabel = new JLabel("Event Planner Tool by Biju Ale");
        lblNewLabel.setForeground(new Color(102, 255, 204));
        lblNewLabel.setFont(new Font("Georgia", Font.ITALIC, 11));
        lblNewLabel.setBounds(21, 34, 163, 25);
        getContentPane().add(lblNewLabel);

        JLabel label = new JLabel("Social Time");
        label.setForeground(new Color(102, 255, 204));
        label.setFont(new Font("Georgia", Font.PLAIN, 20));
        label.setBounds(21, 11, 119, 25);
        getContentPane().add(label);

        btnLogin.setFont(new Font("Georgia", Font.PLAIN, 11));

        btnLogin.setBounds(21, 139, 89, 23);
        getContentPane().add(btnLogin);

        btnRegister.setFont(new Font("Georgia", Font.PLAIN, 11));
        btnRegister.setBounds(120, 139, 93, 23);
        getContentPane().add(btnRegister);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(new Color(102, 255, 204));
        lblEmail.setFont(new Font("Georgia", Font.PLAIN, 11));
        lblEmail.setBounds(21, 80, 44, 25);
        getContentPane().add(lblEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(102, 255, 204));
        lblPassword.setFont(new Font("Georgia", Font.PLAIN, 11));
        lblPassword.setBounds(21, 106, 65, 25);
        getContentPane().add(lblPassword);

        txtEmail = new JTextField();
        txtEmail.setBounds(84, 82, 129, 20);
        getContentPane().add(txtEmail);
        txtEmail.setColumns(10);

        txtPassword = new JPasswordField();
        txtPassword.setColumns(10);
        txtPassword.setBounds(84, 108, 129, 20);
        getContentPane().add(txtPassword);

        setLocationRelativeTo(null);

        regFrame = new RegistrationFrame();
        regFrame.setVisible(false);
        //Anonymous class to override close operation. When form is closed it is hidden. 
        regFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                regFrame.dispose();
            }
        });
        //Anonymous class to override show and hide operation. All fields are reset. 
        regFrame.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                regFrame.resetFields();
            }

            public void componentShown(ComponentEvent e) {
                regFrame.resetFields();
            }
        });
        regFrame.loginFrame = this;
        txtEmail.requestFocus();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSource = e.getSource();
        if (btnSource.equals(btnRegister)) {
            regFrame.setVisible(true);
            regFrame.btnReset.doClick(); //Reset all fields
        } else if (btnSource.equals(btnLogin)) {

            //Null validation
            if (txtEmail.getText().equals(null) || txtEmail.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Email cannot be left blank!");
                txtEmail.setText("");
                txtEmail.requestFocus();
                return;
            }
            if (new String(txtPassword.getPassword()).equals(null) || new String(txtPassword.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Password cannot be left blank!");
                txtPassword.setText("");
                txtPassword.requestFocus();
                return;
            }

            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());
            if (userController.authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(null, "Welcome!");
                Dashboard dashboard = Dashboard.getInstanceOf();
                DAO daoEvent = SimpleDAOFactory.getDAO("DAOEvent");
                DAOEvent.dashboard = dashboard;//Dashboard is registered for syncing created event list
                daoEvent.getAll(UserController.getLoggedInUser().getId());
                this.dispose();
                dashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
                txtEmail.setText("");
                txtPassword.setText("");
                txtEmail.requestFocus();
                return;
            }
        }
    }
}
