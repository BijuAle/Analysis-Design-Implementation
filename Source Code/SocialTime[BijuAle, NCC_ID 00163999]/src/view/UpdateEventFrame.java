package view;

import controller.SocialTime;
import controller.EventController;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;

public class UpdateEventFrame extends JFrame implements ActionListener {

    private int eventID;

    public JTextField getTxtEventName() {
        return txtEventName;
    }

    public void setTxtEventName(String eventName) {
        this.txtEventName.setText(eventName);
    }

    public void setTxtLocation(String location) {
        txtLocation.setText(location);
    }

    public void setTxtDescription(String description) {
        txtDescription.setText(description);
    }

    public void setSpnMinAttendee(int minAttendee) {
        spnMinAttendee.setValue(minAttendee);
    }

    public void setSpnMinThreshold(int minThreshold) {
        spnMinThreshold.setValue(minThreshold);
    }

    public void setSpnDuration(int duration) {
        spnDuration.setValue(duration);
    }

    private JPanel contentPane;
    private JTextField txtEventName, txtLocation;
    private JTextArea txtDescription;
    private JSpinner spnMinAttendee, spnMinThreshold, spnDuration;
    JButton btnSubmit;

    EventController eventController = SocialTime.getEventController();

    /**
     * Create the frame.
     */
    public UpdateEventFrame() {
        setTitle("Update event");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 340, 320);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(102, 0, 51));

        JLabel lblNewLabel = new JLabel("Event Name");
        lblNewLabel.setBounds(82, 25, 85, 14);
        lblNewLabel.setForeground(new Color(102, 255, 204));

        contentPane.add(lblNewLabel);

        JLabel lblLocation = new JLabel("Location");
        lblLocation.setBounds(100, 50, 67, 14);
        lblLocation.setForeground(new Color(102, 255, 204));
        contentPane.add(lblLocation);

        JLabel lblDescription = new JLabel("Description");
        lblDescription.setBounds(87, 75, 80, 14);
        lblDescription.setForeground(new Color(102, 255, 204));
        contentPane.add(lblDescription);

        JLabel lblMinAttendees = new JLabel("Minimum No. of attendees");
        lblMinAttendees.setBounds(25, 181, 200, 14);
        lblMinAttendees.setForeground(new Color(102, 255, 204));
        contentPane.add(lblMinAttendees);

        JLabel lblMinimumThresholdPercent = new JLabel("Minimum Threshold Percent");
        lblMinimumThresholdPercent.setBounds(15, 208, 160, 14);
        lblMinimumThresholdPercent.setForeground(new Color(102, 255, 204));
        contentPane.add(lblMinimumThresholdPercent);

        txtEventName = new JTextField();
        txtEventName.setBounds(177, 25, 127, 20);
        contentPane.add(txtEventName);
        txtEventName.setColumns(10);

        txtLocation = new JTextField();
        txtLocation.setColumns(10);
        txtLocation.setBounds(177, 50, 127, 20);
        contentPane.add(txtLocation);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(218, 236, 89, 23);
        btnSubmit.addActionListener(this);
        contentPane.add(btnSubmit);

        spnMinAttendee = new JSpinner();
        spnMinAttendee.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnMinAttendee.setBounds(177, 178, 55, 20);
        contentPane.add(spnMinAttendee);

        spnMinThreshold = new JSpinner();
        spnMinThreshold.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnMinThreshold.setBounds(177, 205, 55, 20);
        contentPane.add(spnMinThreshold);

        txtDescription = new JTextArea();
        txtDescription.setBounds(177, 78, 127, 60);
        contentPane.add(txtDescription);

        spnDuration = new JSpinner();
        spnDuration.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnDuration.setBounds(177, 150, 55, 20);
        contentPane.add(spnDuration);

        JLabel lblDurationhrs = new JLabel("Duration (hrs.)");
        lblDurationhrs.setBounds(63, 153, 200, 14);
        lblDurationhrs.setForeground(new Color(102, 240, 204));
        contentPane.add(lblDurationhrs);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object btnSource = e.getSource();
        if (btnSource.equals(btnSubmit)) {

            //Null validation
            if (txtEventName.getText().equals(null) || txtEventName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Event name cannot be left blank.");
                txtEventName.setText("");
                txtEventName.requestFocus();
                return;
            }
            if (txtLocation.getText().equals(null) || txtLocation.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Location cannot be left blank!");
                txtLocation.setText("");
                txtLocation.requestFocus();
                return;
            }

            if (txtDescription.getText().equals(null) || txtLocation.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Description cannot be left blank!");
                txtDescription.setText("");
                txtDescription.requestFocus();
                return;
            }

            String eventName = txtEventName.getText();
            String location = txtLocation.getText();
            String description = txtDescription.getText();
            int minAttendees = (Integer) spnMinAttendee.getValue();
            int minAThreshold = (Integer) spnMinThreshold.getValue();
            int duration = (Integer) spnDuration.getValue();

            eventController.updateEvent(eventName, eventID, location, description, duration, minAttendees, minAThreshold);
            JOptionPane.showMessageDialog(null, "Event updated Succesfully!", "Event has been updated!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void resetFields() {
        //Clear all fields
        txtEventName.setText("");
        txtLocation.setText("");
        txtDescription.setText("");
        spnMinAttendee.setValue(1);
        spnMinThreshold.setValue(1);
        spnDuration.setValue(1);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
