import javax.swing.*;
import java.awt.*;
import java.util.List;
public class MainGUI extends JFrame {
    private JTextField idField;
    private JTextField titleField;
    private JTextField mentorField;
    private JTextField dateField;
    private JTextField locationField;
    private JTextField maxField;

    private JTextArea outputArea;

    // there should be a private member variable named `sessions` :
    // private SomethingOrOther sessions;

    // the constructor for the class. This will initialize
    // the class's member variables:
    public MainGUI() {
        // set sessions to a new empty list:
        // sessions = ...
        setTitle("Employee Mentorship and Inclusion Manager");
        setSize(600, 600);
        // when this frame/window closes, halt the whole program:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);
    }

    // Create all of the display elements in the frame:
    private void createGUI() {
        // first, the input panel contains all of the field entry elements:
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8,2,5,5));
        // these are all of the input fields that will be in the frame:
        idField = new JTextField();
        titleField = new JTextField();
        mentorField = new JTextField();
        dateField = new JTextField();
        locationField = new JTextField();
        maxField = new JTextField();
        inputPanel.add(new JLabel("Session ID"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Title"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Mentor"));
        inputPanel.add(mentorField);
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Location"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Max Participants"));
        inputPanel.add(maxField);
        add(inputPanel, BorderLayout.NORTH);

        // next, the lower half of the window contains an output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        add(scroll, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Session");
        JButton displayButton = new JButton("Display");
        JButton searchButton = new JButton("Search");
        JButton removeButton = new JButton("Remove");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addSession());
        displayButton.addActionListener(e -> displaySessions());
        searchButton.addActionListener(e -> searchSession());
        removeButton.addActionListener(e -> removeSession());
        registerButton.addActionListener(e -> registerParticipant());
        exitButton.addActionListener(e -> System.exit(0));
    }

    // set all input fields to empty strings, give focus to the first
    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        mentorField.setText("");
        dateField.setText("");
        locationField.setText("");
        maxField.setText("");
        // Put the cursor back in the first field
        idField.requestFocus();
    }
    //creating classes
    public class session{
        int Id;
        String title;
        String mentor;
        String date;
        String location;
        int maxParticipants;
        public session(int Id, String title, String mentor, String date, String location, int maxParticipants){
            this.Id = Id;
            this.title= title;
            this.mentor= mentor;
            this.date=date;
            this.location=location;
            this.maxParticipants=maxParticipants;

        }

    }
    public record session_list(session first, session_list rest){}

    static session_list session = null;

    // the action of the Add Session button
    private void addSession() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String mentor = mentorField.getText();
            String date = dateField.getText();
            String location = locationField.getText();
            int maxParticipants = Integer.parseInt(maxField.getText());

            // TO DO: construct a session object, insert it into
            // the list of sessions\

            session newSession= new session(id, title, mentor, date, location, maxParticipants);
            session= new session_list(newSession,session);



            outputArea.setText("Session Added Successfully\n");
            // Clear the input fields
            clearFields();
        }
        catch(Exception e) {
            outputArea.setText("Invalid input");
        }
    }

    // display all sessions in the output area
    private void displaySessions() {
        outputArea.setText("");

        // iterate over sessions; display each one
        // to the output window, using the `append`
        // method of the outputArea.

        // between each one, print a separator line,
        // as e.g.
        session_list current=session;
        while (current != null){
            outputArea.append("ID: "+current.first.Id+" - ");
            outputArea.append("Title: "+current.first.title+" - ");
            outputArea.append("Mentor: "+current.first.mentor+" - ");
            outputArea.append("Date: "+current.first.Id+" - ");
            outputArea.append("Location: "+current.first.location+" - ");
            outputArea.append("Max_participants: "+current.first.maxParticipants+" - ");
            current= current.rest;
        }
    }
    public static session searchByID(int id){
        session_list current=session;
        while(current != null){
            if (current.first.Id == id){
                return current.first;
            } else{
                current= current.rest;
            }
        }
        return null;
    }
    public static session_list searchByMentor(String Mentor){
        session_list current=session;
        session_list  name_list = null;
        while(current != null){
            if (current.first.mentor.equals(Mentor)){

                name_list= new session_list(current.first, name_list);
                current= current.rest;
            } else{
                current= current.rest;
            }
        }
        return name_list;
    }




    // search by ID if presesnt, mentor otherwise, display results
    private void searchSession() {
        // Search by ID if the ID field is not empty
        session_list current =session;
        if (!idField.getText().trim().isEmpty()) {
            int id = Integer.parseInt(idField.getText().trim());
            // find session by ID, using a `searchByID` method
            // ... code here ..
            session session1 = searchByID(id);
            if (session1!=null){
                outputArea.append("ID: "+session1.Id+" - ");
                outputArea.append("Title: "+session1.title+" - ");
                outputArea.append("Mentor: "+session1.mentor+" - ");
                outputArea.append("Date: "+session1.date+" - ");
                outputArea.append("Location: "+session1.location+" - ");
                outputArea.append("Max_participants: "+session1.maxParticipants+" - ");
            }

            /* if (result != null)

                // display session to the output area...
            else
                outputArea.setText("Session not found.");
             */
        }
        // Otherwise, search by mentor if the Mentor field is not empty
        else if (!mentorField.getText().trim().isEmpty()) {
            String mentor = mentorField.getText().trim();
            // find session by mentor. In this case, the result
            // may be a list of sessions...
            // ... code here ...
            session_list session2 = searchByMentor(mentor);
            while (session2!=null){
                outputArea.append("ID: "+session2.first.Id+" - ");
                outputArea.append("Title: "+session2.first.title+" - ");
                outputArea.append("Mentor: "+session2.first.mentor+" - ");
                outputArea.append("Date: "+session2.first.date+" - ");
                outputArea.append("Location: "+session2.first.location+" - ");
                outputArea.append("Max_participants: "+session2.first.maxParticipants+" - ");
            }
            /*
            if (result != null)
                // display all sessions in the list
            else
                outputArea.setText("No session found for mentor: " + mentor);
             */
        }
        // Nothing entered
        else {
            outputArea.setText("Please enter a Session ID or Mentor name.");
        }
    }

    // given an id, remove that session from the list
    public static session_list removeByID(session_list list, int id){
        if (list==null){
            return null;
        }
        else if (list.first.Id==id){
            return list.rest;
        }
        session= new session_list(list.first, removeByID(list.rest,id));
        return new session_list(list.first, removeByID(list.rest,id));
    }
    private void removeSession() {
        int id = Integer.parseInt(idField.getText());
        // remove the session, print an error to the outputArea
        // if it's not found
        // ... code here ...
        if (searchByID(id)!=null){
            outputArea.append("error:session id not found");
        }
        else{
            removeByID(session,id);
        }

    }

    // add one to the count of the specified session.
    // MUTATES participant count of session.
    private void registerParticipant() {
        int id = Integer.parseInt(idField.getText());
        // increment participants field of session,
        // print success or failure message.
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
