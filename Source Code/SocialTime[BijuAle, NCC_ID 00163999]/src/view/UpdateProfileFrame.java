package view;

import controller.SocialTime;
import controller.UserController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class UpdateProfileFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JTextField txtConfirmPassword;

    private final JButton btnSubmit;
    private UserController userController;

    /**
     * Create the frame.
     */
    public UpdateProfileFrame() {
        setTitle("Update Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 352, 171);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(102, 0, 51));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login Email");
        lblNewLabel.setForeground(new Color(102, 255, 204));
        lblNewLabel.setBackground(new Color(102, 255, 204));
        lblNewLabel.setBounds(67, 23, 89, 14);
        contentPane.add(lblNewLabel);

        JLabel lblPassword = new JLabel("New Password");
        lblPassword.setForeground(new Color(102, 255, 204));
        lblPassword.setBackground(new Color(102, 255, 204));
        lblPassword.setBounds(49, 48, 107, 14);
        contentPane.add(lblPassword);

        JLabel lblConfirmNewPassword = new JLabel("Confirm new Password");
        lblConfirmNewPassword.setForeground(new Color(102, 255, 204));
        lblConfirmNewPassword.setBackground(new Color(102, 255, 204));
        lblConfirmNewPassword.setBounds(10, 72, 146, 14);
        contentPane.add(lblConfirmNewPassword);

        txtEmail = new JTextField();
        txtEmail.setBounds(182, 18, 125, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        txtPassword = new JTextField();
        txtPassword.setColumns(10);
        txtPassword.setBounds(182, 43, 125, 20);
        contentPane.add(txtPassword);

        txtConfirmPassword = new JTextField();
        txtConfirmPassword.setColumns(10);
        txtConfirmPassword.setBounds(182, 67, 125, 20);
        contentPane.add(txtConfirmPassword);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(218, 98, 89, 23);
        btnSubmit.addActionListener(this);
        contentPane.add(btnSubmit);

        userController = SocialTime.getUserController();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSource = e.getSource();
        if (btnSource.equals(btnSubmit)) {
            //Null validation
            if (txtEmail.getText().equals(null) || txtEmail.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter email.");
                txtEmail.setText("");
                txtEmail.requestFocus();
                return;
            }
            if (txtPassword.getText().equals(null) || txtPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Password cannot be set blank!");
                txtPassword.setText("");
                txtPassword.requestFocus();
                return;
            }
            if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                JOptionPane.showMessageDialog(null, "Passwords did not match!");
                txtPassword.setText("");
                txtConfirmPassword.setText("");
                txtPassword.requestFocus();
                return;
            }
            String newEmail = txtEmail.getText();
            String newPassword = new String(txtPassword.getText());

            UserController.getLoggedInUser().setEmail(newEmail);
            UserController.getLoggedInUser().setPassword(newPassword);
            userController.updateProfile(UserController.getLoggedInUser());
            JOptionPane.showMessageDialog(null, "Please use newly set email & password from next login onwards.", "Profile updated!", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }
}
