/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SocialTime;
import controller.TimeSlotController;
import controller.UserController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.TimeSlot;

/**
 *
 * @author Biju Ale
 */
public class RegistrationFrame extends JFrame implements ActionListener, ItemListener {

    private JCheckBox[][] allChkAvailableTS, allChkUnavailableTS;
    private JCheckBox eachChkAvailableTS, eachChkUnavailableTS;

    private final JTextField txtFirstName;
    private final JTextField txtLastName = new JTextField(20);
    private final JTextField txtLocation = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);

    private final JButton btnRegister = new JButton("Register");
    final JButton btnReset = new JButton("Reset");
    private final JButton btnLogin = new JButton("Back to Login");

    LoginFrame loginFrame;

    private final ArrayList<String> allAvailableTS_Str = new ArrayList();
    private final ArrayList<String> allUnavailableTS_Str = new ArrayList();

    private final UserController userController = SocialTime.getUserController();
    private final TimeSlotController timeslotController = new TimeSlotController();

    Color foreGroundColor = new Color(102, 255, 204);
    Color backgroundColor = new Color(102, 0, 51);

    JList<String> potentialInvitees, toInviteUsers;

    public RegistrationFrame() {

        this.txtFirstName = new JTextField(20);
        setTitle("Account Registration - Social Time");
        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(new FlowLayout());
        setSize(720, 820);
        setLocationRelativeTo(null);

        allChkAvailableTS = new JCheckBox[8][24];
        allChkUnavailableTS = new JCheckBox[8][24];

        JPanel topContainer = new JPanel();
        topContainer.setPreferredSize(new Dimension(300, 150));
        topContainer.setBackground(backgroundColor);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setForeground(foreGroundColor);
        topContainer.add(lblFirstName);
        topContainer.add(txtFirstName);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setForeground(foreGroundColor);
        topContainer.add(lblLastName);
        topContainer.add(txtLastName);

        JLabel lblLocation = new JLabel("Location");
        lblLocation.setForeground(foreGroundColor);
        topContainer.add(lblLocation);
        topContainer.add(txtLocation);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(foreGroundColor);
        topContainer.add(lblEmail);
        topContainer.add(txtEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(foreGroundColor);
        topContainer.add(lblPassword);
        topContainer.add(txtPassword);

        btnRegister.addActionListener(this);
        btnReset.addActionListener(this);
        btnLogin.addActionListener(this);

        add(topContainer);
        add(inputAreaAvailableTS());
        add(inputAreaUnavailableTS());
        add(btnRegister);
        add(btnReset);
        add(btnLogin);
    }

    private JPanel inputAreaAvailableTS() {
        JPanel pnlAvailableTS = new JPanel(new FlowLayout());
        pnlAvailableTS.setBackground(new Color(102, 0, 51));
        pnlAvailableTS.setPreferredSize(new Dimension(670, 280));
        pnlAvailableTS.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(this.backgroundColor, this.foreGroundColor), "Select available timeslots (Recurrs weekly)", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Georgia", Font.PLAIN, 14), new Color(102, 255, 204)));

        pnlAvailableTS.add(new Canvas());//Hour labels
        allChkAvailableTS = new JCheckBox[8][24];

        for (int dayNo = 1; dayNo < 8; dayNo++) {
            for (int hour = 0; hour < 24; hour++) {
                allChkAvailableTS[dayNo][hour] = new JCheckBox();
                eachChkAvailableTS = allChkAvailableTS[dayNo][hour];
                eachChkAvailableTS.setHorizontalTextPosition(SwingConstants.LEFT);
                eachChkAvailableTS.setFont(new Font("Georgia", Font.PLAIN, 11));
                eachChkAvailableTS.setBackground(new Color(102, 0, 51));
                eachChkAvailableTS.setForeground(new Color(102, 255, 204));

                if (dayNo == 1 && hour == 0) {
                    eachChkAvailableTS.setText("Sun");
                } else if (dayNo == 2 && hour == 0) {
                    eachChkAvailableTS.setText("Mon");
                } else if (dayNo == 3 && hour == 0) {
                    eachChkAvailableTS.setText("Tue");
                } else if (dayNo == 4 && hour == 0) {
                    eachChkAvailableTS.setText("Wed");
                } else if (dayNo == 5 && hour == 0) {
                    eachChkAvailableTS.setText("Thu");
                } else if (dayNo == 6 && hour == 0) {
                    eachChkAvailableTS.setText("Fri");
                } else if (dayNo == 7 && hour == 0) {
                    eachChkAvailableTS.setText("Sat");
                }

                eachChkAvailableTS.setName(dayNo + ";" + hour + ";" + 1);
                eachChkAvailableTS.addActionListener(this);
                eachChkAvailableTS.addItemListener(this);
                pnlAvailableTS.add(eachChkAvailableTS);

            }
        }
        return pnlAvailableTS;
    }

    private JPanel inputAreaUnavailableTS() {
        JPanel pnlUnavailableTS = new JPanel();
        pnlUnavailableTS.setPreferredSize(new Dimension(670, 280));
        pnlUnavailableTS.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(this.backgroundColor, this.foreGroundColor), "Select unavailable timeslots (Recurrs weekly)", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Georgia", Font.PLAIN, 14), new Color(102, 255, 204)));
        pnlUnavailableTS.add(new Canvas());
        pnlUnavailableTS.setBackground(new Color(102, 0, 51));
        allChkUnavailableTS = new JCheckBox[8][24];

        for (int dayNo = 1; dayNo < 8; dayNo++) {
            for (int hour = 0; hour < 24; hour++) {
                allChkUnavailableTS[dayNo][hour] = new JCheckBox();
                eachChkUnavailableTS = allChkUnavailableTS[dayNo][hour];
                eachChkUnavailableTS.setHorizontalTextPosition(SwingConstants.LEFT);
                eachChkUnavailableTS.setFont(new Font("Georgia", Font.PLAIN, 11));
                eachChkUnavailableTS.setBackground(new Color(102, 0, 51));
                eachChkUnavailableTS.setForeground(new Color(102, 255, 204));
                if (dayNo == 1 && hour == 0) {
                    eachChkUnavailableTS.setText("Sun");
                } else if (dayNo == 2 && hour == 0) {
                    eachChkUnavailableTS.setText("Mon");
                } else if (dayNo == 3 && hour == 0) {
                    eachChkUnavailableTS.setText("Tue");
                } else if (dayNo == 4 && hour == 0) {
                    eachChkUnavailableTS.setText("Wed");
                } else if (dayNo == 5 && hour == 0) {
                    eachChkUnavailableTS.setText("Thu");
                } else if (dayNo == 6 && hour == 0) {
                    eachChkUnavailableTS.setText("Fri");
                } else if (dayNo == 7 && hour == 0) {
                    eachChkUnavailableTS.setText("Sat");
                }

                eachChkUnavailableTS.setName(dayNo + ";" + hour + ";" + 2);
                eachChkUnavailableTS.addActionListener(this);
                eachChkUnavailableTS.addItemListener(this);
                pnlUnavailableTS.add(eachChkUnavailableTS);
            }
        }
        return pnlUnavailableTS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object btnSource = (Object) e.getSource();
        if (btnSource.equals(btnRegister)) {
            //Null validation
            if (txtFirstName.getText().equals(null) || txtFirstName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter first name.");
                txtFirstName.setText("");
                txtFirstName.requestFocus();
                return;
            }
            if (txtLastName.getText().equals(null) || txtLastName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Last Name cannot be left blank!");
                txtLastName.setText("");
                txtLastName.requestFocus();
                return;
            }
            if (txtLocation.getText().equals(null) || txtLocation.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Location cannot be left blank!");
                txtLocation.setText("");
                txtLocation.requestFocus();
                return;
            }
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

            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String location = txtLocation.getText();
            String password = new String(txtPassword.getPassword());
            ArrayList<TimeSlot> allAvailableTS, allUnavailableTS;

            //Get timeslot objects parsed from string
            allAvailableTS = timeslotController.parseTS(allAvailableTS_Str);
            allUnavailableTS = timeslotController.parseTS(allUnavailableTS_Str);

            //Send all parameters to userController to register the user.
            userController.registerUser(firstName, lastName, email, location, password, allAvailableTS, allUnavailableTS);
            JOptionPane.showMessageDialog(null, "Registration Succesfull!", "User Regiserted!", JOptionPane.INFORMATION_MESSAGE);
        } else if (btnSource.equals(btnReset)) {
            resetFields();

            for (int dayNo = 1; dayNo < 8; dayNo++) {
                for (int hour = 0; hour < 24; hour++) {
                    eachChkUnavailableTS = allChkUnavailableTS[dayNo][hour];
                    eachChkUnavailableTS.setSelected(false);
                }
            }
            for (int dayNo = 1; dayNo < 8; dayNo++) {
                for (int hour = 0; hour < 24; hour++) {
                    eachChkAvailableTS = allChkAvailableTS[dayNo][hour];
                    eachChkAvailableTS.setSelected(false);
                }
            }
            txtFirstName.requestFocus();
        } else if (btnSource.equals(btnLogin)) {
            dispose();
        }
    }


    //Selection validation
    //Logic to toggle available and unavailable checkbox
    @Override
    public void itemStateChanged(ItemEvent e) {

        JCheckBox selectedCheckBox = (JCheckBox) e.getItem();

        if (e.getStateChange() == ItemEvent.SELECTED) {
            String tokens[] = selectedCheckBox.getName().split(";");
            int day = Integer.parseInt((String) tokens[0]);
            int hour = Integer.parseInt((String) tokens[1]);
            int timeSlotType = Integer.parseInt((String) tokens[2]);

            if (timeSlotType == 1) {
                allAvailableTS_Str.add(selectedCheckBox.getName());

                for (int dayNo = 1; dayNo < 8; dayNo++) {
                    for (int hours = 0; hours < 24; hours++) {
                        eachChkUnavailableTS = allChkUnavailableTS[dayNo][hours];
                        String tokens_u[] = eachChkUnavailableTS.getName().split(";");
                        int day_u = Integer.parseInt((String) tokens_u[0]);
                        int hour_u = Integer.parseInt((String) tokens_u[1]);
                        if (day_u == day && hour_u == hour) {
                            eachChkUnavailableTS.setEnabled(false);
                            break;
                        }

                    }
                }

            } else if (timeSlotType == 2) {
                allUnavailableTS_Str.add(selectedCheckBox.getName());

                for (int dayNo = 1; dayNo < 8; dayNo++) {
                    for (int hours = 0; hours < 24; hours++) {
                        eachChkAvailableTS = allChkAvailableTS[dayNo][hours];
                        String tokens_u[] = eachChkAvailableTS.getName().split(";");
                        int day_u = Integer.parseInt((String) tokens_u[0]);
                        int hour_u = Integer.parseInt((String) tokens_u[1]);
                        if (day_u == day && hour_u == hour) {
                            eachChkAvailableTS.setEnabled(false);
                            break;
                        }
                    }
                }
            }
        }
        if (e.getStateChange() == ItemEvent.DESELECTED) {

            String tokens[] = selectedCheckBox.getName().split(";");
            int day = Integer.parseInt((String) tokens[0]);
            int hour = Integer.parseInt((String) tokens[1]);
            int timeSlotType = Integer.parseInt((String) tokens[2]);

            if (timeSlotType == 1) {
                allAvailableTS_Str.remove(selectedCheckBox.getName());

                for (int dayNo = 1; dayNo < 8; dayNo++) {
                    for (int hours = 0; hours < 24; hours++) {
                        eachChkUnavailableTS = allChkUnavailableTS[dayNo][hours];
                        String tokens_u[] = eachChkUnavailableTS.getName().split(";");
                        int day_u = Integer.parseInt((String) tokens_u[0]);
                        int hour_u = Integer.parseInt((String) tokens_u[1]);
                        if (day_u == day && hour_u == hour) {
                            eachChkUnavailableTS.setEnabled(true);
                            break;
                        }
                    }
                }

            } else if (timeSlotType == 2) {
                allUnavailableTS_Str.remove(selectedCheckBox.getName());

                for (int dayNo = 1; dayNo < 8; dayNo++) {
                    for (int hours = 0; hours < 24; hours++) {
                        eachChkAvailableTS = allChkAvailableTS[dayNo][hours];
                        String tokens_u[] = eachChkAvailableTS.getName().split(";");
                        int day_u = Integer.parseInt((String) tokens_u[0]);
                        int hour_u = Integer.parseInt((String) tokens_u[1]);
                        if (day_u == day && hour_u == hour) {
                            eachChkAvailableTS.setEnabled(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void resetFields() {
        //Clear all fields
        txtFirstName.setText("");
        txtLastName.setText("");
        txtLocation.setText("");
        txtPassword.setText("");
        txtEmail.setText("");

    }

    //Class to draw hour labels on JFrame
    private class Canvas extends JPanel {

        private Canvas() {
            setPreferredSize(new Dimension(650, 40));

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(new Font("Georgia", Font.ITALIC, 11));
            g2d.setColor(new Color(102, 255, 204));
            int x = 20, y = 35;

            g2d.drawString("Hourly blocks", x + 260, y - 20);
            g2d.drawString("Hourly blocks", x + 260, y + 80);

            g2d.setFont(new Font("Georgia", Font.BOLD, 14));
            for (int hr = 0; hr < 24; hr++) {
                //Draw timeslot hour labels
                g2d.drawString(hr + "", x += 13, y);
                g2d.drawString(hr + "", x += 13, y + 100);
                //Draw day labels
                int y2 = 100;
                g2d.drawString(hr + "", x, y2 += 10);
                g2d.drawString(hr + "", x, y2 += 10);
            }
        }

    }
}
