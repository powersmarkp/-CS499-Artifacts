import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class EditEntryForm extends JFrame {
    private JLabel editEntryTitle;
    private JLabel editEntryIcon;
    private JPanel editEntryPanel;
    private JTextField textField1;
    private JButton confirmChangesButton;
    private JButton cancelEditButton;
    private JLabel animalIdLabel;
    private JLabel editHelpTxt;
    private JButton lookUpButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JButton clearSelectionButton;


    public EditEntryForm(String title) {
        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(editEntryPanel);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLocationRelativeTo(getContentPane());
        this.pack();

        // Confirm changes and send ddta to database
        confirmChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAnimalEntry();
            }
        });

        cancelEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Clear all text fields in order to start again
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
            }
        });

        lookUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lookUpAnimal();
            }
        });
    }

    // There was probably a much better way to handle these text fields, but I tried to create them and populate
    //    them with a for loop but couldn't get it to work properly
    public void clearTextFields() {
        ArrayList<JTextField> textFieldArray = new ArrayList<>();
        textFieldArray.add(textField1);
        textFieldArray.add(textField2);
        textFieldArray.add(textField3);
        textFieldArray.add(textField4);
        textFieldArray.add(textField5);
        textFieldArray.add(textField6);
        textFieldArray.add(textField7);
        textFieldArray.add(textField8);
        textFieldArray.add(textField9);
        textFieldArray.add(textField10);
        textFieldArray.add(textField11);
        textFieldArray.add(textField12);
        textFieldArray.add(textField13);
        textFieldArray.add(textField14);
        textFieldArray.add(textField15);
        textFieldArray.add(textField16);
        textFieldArray.add(textField17);

        for(int i = 0; i<textFieldArray.size(); i++) {
            textFieldArray.get(i).setText("");
        }
    }

    public void lookUpAnimal() {

        if (textField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter an animal ID#.");
        }

        // This fetches the entry associated with the entered animal ID and fills the text fields with its
        //    current data
        else {
            final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
            final String USERNAME = "root";
            final String PASSWORD = "";
            try{
                Connection connection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
                String query = "SELECT * FROM aac_animals WHERE animal_id= '" + textField1.getText() +"'";
                ResultSet resultSet = connection.createStatement().executeQuery(query);
                if (resultSet.next() == true) {
                        textField6.setText(resultSet.getString("entry"));
                        textField2.setText(resultSet.getString("age_upon_outcome"));
                        textField3.setText(resultSet.getString("animal_id"));
                        textField4.setText(resultSet.getString("animal_type"));
                        textField5.setText(resultSet.getString("breed"));
                        textField7.setText(resultSet.getString("color"));
                        textField8.setText(resultSet.getString("date_of_birth"));
                        textField9.setText(resultSet.getString("datetime"));
                        textField15.setText(resultSet.getString("monthyear"));
                        textField16.setText(resultSet.getString("name"));
                        textField17.setText(resultSet.getString("outcome_subtype"));
                        textField10.setText(resultSet.getString("outcome_type"));
                        textField11.setText(resultSet.getString("sex_upon_outcome"));
                        textField12.setText(resultSet.getString("location_lat"));
                        textField13.setText(resultSet.getString("location_long"));
                        textField14.setText(resultSet.getString("age_upon_outcome_in_weeks"));
                }
                else if (resultSet.next() == false) {
                    JOptionPane.showMessageDialog(null,
                            "Could not find the animal ID you entered. Pleas try again.");
                }
            }catch(Exception excep) {
                excep.printStackTrace();
            }


        }
    }

    public void editAnimalEntry(){
        final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            // Looking for any blank text fields
            if(textField3.getText().equals("") ||
                    textField4.getText().equals("") ||
                    textField16.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Cannot update record with no ID, animal type, or name field.");
            }
            // grab all values in text fields and update the entry in the database with those values
            else {
                Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                String editString = "UPDATE aac_animals " +
                        "SET age_upon_outcome=?, animal_id=?, animal_type=?, breed=?, " +
                        "color=?, date_of_birth=?, datetime=?, monthyear=?, name=?, " +
                        " outcome_subtype=?, outcome_type=?, sex_upon_outcome=?, location_lat=?, "+
                        "location_long=?, age_upon_outcome_in_weeks=? " +
                        "WHERE animal_id=?";

                PreparedStatement preparedStatement = connection.prepareStatement(editString);
                preparedStatement.setString(1, textField2.getText());
                preparedStatement.setString(2, textField3.getText());
                preparedStatement.setString(3, textField4.getText());
                preparedStatement.setString(4, textField5.getText());
                preparedStatement.setString(5, textField7.getText());
                preparedStatement.setString(6, textField8.getText());
                preparedStatement.setString(7, textField9.getText());
                preparedStatement.setString(8, textField15.getText());
                preparedStatement.setString(9, textField16.getText());
                preparedStatement.setString(10, textField17.getText());
                preparedStatement.setString(11, textField10.getText());
                preparedStatement.setString(12, textField11.getText());
                preparedStatement.setString(13, textField12.getText());
                preparedStatement.setString(14, textField13.getText());
                preparedStatement.setString(15, textField14.getText());
                preparedStatement.setString(16, textField1.getText());

                int updatedRows = preparedStatement.executeUpdate();
                if(updatedRows > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Record updated successfully.");
                    clearTextFields();
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Something went wrong, record not updated. Please try again");
                }
            }

        }catch(Exception excep) {
            excep.printStackTrace();
        }
    }
};
