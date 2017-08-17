package view;

import controller.SocialTime;
import controller.EventController;
import controller.UserController;
import dao.DAOUser;
import dao.SimpleDAOFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.User;

public class AddEventFrame extends JFrame implements ActionListener, ListSelectionListener {

    private JPanel contentPane;
    private JTextField txtEventName, txtLocation;
    private JTextArea txtDescription;
    private JSpinner spnMinAttendee, spnMinThreshold, spnDuration;
    JButton btnSubmit;

    JPanel pnlLists;
    private final JList lstAllUsers, lstToInvite;

    Color foreGroundColor = new Color(102, 255, 204);
    Color backgroundColor = new Color(102, 0, 51);
    EventController eventController = SocialTime.getEventController();

    Set<User> colLstAllUsers = new HashSet();
    Set<User> colLstToInvite = new HashSet<>();

    public AddEventFrame() {
        setTitle("Add event");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 680);
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

//        JLabel lblInvite = new JLabel("Select list item to transfer users between lists");
//        lblInvite.setBounds(45, 240, 300, 14);
//        lblInvite.setForeground(new Color(110, 255, 204));
//        contentPane.add(lblInvite);
        txtEventName = new JTextField();
        txtEventName.setBounds(177, 25, 200, 20);
        contentPane.add(txtEventName);
        txtEventName.setColumns(10);

        txtLocation = new JTextField();
        txtLocation.setColumns(10);
        txtLocation.setBounds(177, 50, 200, 20);
        contentPane.add(txtLocation);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(300, 600, 89, 23);
        btnSubmit.addActionListener(this);
        contentPane.add(btnSubmit);

        spnMinAttendee = new JSpinner();
        spnMinAttendee.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnMinAttendee.setBounds(200, 178, 55, 20);
        contentPane.add(spnMinAttendee);

        spnMinThreshold = new JSpinner();
        spnMinThreshold.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnMinThreshold.setBounds(200, 205, 55, 20);
        contentPane.add(spnMinThreshold);

        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setBounds(177, 78, 200, 60);
        contentPane.add(txtDescription);

        spnDuration = new JSpinner();
        spnDuration.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spnDuration.setBounds(177, 150, 55, 20);

        //Fetch all users as potential invitee
        for (Object each : ((DAOUser) SimpleDAOFactory.getDAO("DAOUser")).getAll()) {
            User user = (User) each;
            colLstAllUsers.add(user);
            System.out.println(user.getId());
            System.out.println(user.getFirstName());
        }
        //Remove logged in user from potential invitee list
        for (int i = 0; i < colLstAllUsers.size(); i++) {
            colLstAllUsers.remove(UserController.getLoggedInUser());
        }

        //Prepare JList for potential invitee
        lstAllUsers = new JList(new Vector<User>(colLstAllUsers));
        lstAllUsers.addListSelectionListener(this);
        lstAllUsers.setPreferredSize(new Dimension(150, 70));
        lstAllUsers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(this.backgroundColor, this.foreGroundColor), "Potential Invitee", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Georgia", Font.PLAIN, 14), backgroundColor));
        lstAllUsers.setVisibleRowCount(10);
        lstAllUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstAllUsers.setCellRenderer(new DefaultListCellRenderer() {
            @Override//Display Object's data member - string(User's name in list)
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof User) {
                    // Here value will be of the Type 'User'
                    ((JLabel) renderer).setText(((User) value).getFirstName() + " " + ((User) value).getLastName());
                }
                return renderer;
            }
        });

        //Prepare JList for invited users
        colLstToInvite.add(null);
        lstToInvite = new JList(new Vector<User>(colLstToInvite));
        lstToInvite.addListSelectionListener(this);
        lstToInvite.setPreferredSize(new Dimension(150, 70));
        lstToInvite.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(this.backgroundColor, this.foreGroundColor), "To invite", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Georgia", Font.PLAIN, 14), backgroundColor));
        lstToInvite.setVisibleRowCount(10);
        lstToInvite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstToInvite.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof User) {
                    // Here value will be of the Type 'User'
                    ((JLabel) renderer).setText(((User) value).getFirstName() + " " + ((User) value).getLastName());
                }
                return renderer;
            }
        });

        pnlLists = new JPanel(new BorderLayout());
        pnlLists.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(this.backgroundColor, this.foreGroundColor), "Select available timeslots (Recurrs weekly)", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Georgia", Font.PLAIN, 14), new Color(102, 255, 204)));
        pnlLists.setOpaque(false);
        pnlLists.add(new JScrollPane(lstAllUsers), BorderLayout.WEST);
        pnlLists.add(new JScrollPane(lstToInvite), BorderLayout.EAST);
        pnlLists.setBounds(2, 250, 400, 330);
        contentPane.add(pnlLists);
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
            eventController.saveEvent(eventName, location, description, duration, minAttendees, minAThreshold, colLstToInvite);
            JOptionPane.showMessageDialog(null, "Event saved Succesfully!", "Event has been added!", JOptionPane.INFORMATION_MESSAGE);
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

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object lstSource = e.getSource();

        if (lstSource.equals(lstAllUsers)) {
            User selectedUser = ((User) lstAllUsers.getSelectedValue());
            colLstAllUsers.remove(selectedUser);
            colLstToInvite.add(selectedUser);
            lstAllUsers.setListData(new Vector<User>(colLstAllUsers));
            lstToInvite.setListData(new Vector<User>(colLstToInvite));
        } else if (lstSource.equals(lstToInvite)) {
            User selectedUser = ((User) lstToInvite.getSelectedValue());
            colLstToInvite.remove(selectedUser);
            colLstAllUsers.add(selectedUser);
            lstAllUsers.setListData(new Vector<User>(colLstAllUsers));
            lstToInvite.setListData(new Vector<User>(colLstToInvite));
        }
    }
}
