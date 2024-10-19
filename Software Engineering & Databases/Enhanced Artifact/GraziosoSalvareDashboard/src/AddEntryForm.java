import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class AddEntryForm extends JFrame {
    private JLabel addEntryTitle;
    private JLabel addEntryIcon;
    private JPanel addEntryPanel;
    private JButton confirmAddButton;
    private JButton cancelAddButton;
    private JLabel addHelpTxt;
    private JTextField ageOutTxtField;
    private JTextField animalIdTxtField;
    private JTextField animalTypeTxtField;
    private JTextField breedTxtField;
    private JTextField entryTxtField;
    private JTextField colorTxtField;
    private JTextField birthdayTxtField;
    private JTextField dateTimeTxtField;
    private JTextField monthYearTxtField;
    private JTextField nameTxtField;
    private JTextField outSubTypeTxtField;
    private JTextField outTypeTxtField;
    private JTextField sexOutTxtField;
    private JTextField locLatTxtField;
    private JTextField locLongTxtField;
    private JTextField ageOutWeeksTxtField;
    private JButton clearDetailsButton;


    public AddEntryForm(String title) {
        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(addEntryPanel);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLocationRelativeTo(getContentPane());
        this.pack();

        confirmAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    addAnimal();

                }catch(Exception excep) {
                    excep.printStackTrace();
                }
            }
        });

        cancelAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        clearDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
            }
        });
    }

    // Method for adding animal to database
    private void addAnimal() {
        int entry = entryTxtField.getText().length();
        String age_upon_outcome = ageOutTxtField.getText();
        String animal_id = animalIdTxtField.getText();
        String animal_type = animalTypeTxtField.getText();
        String breed = breedTxtField.getText();
        String color = colorTxtField.getText();
        String date_of_birth = birthdayTxtField.getText();
        String datetime = dateTimeTxtField.getText();
        String monthyear = monthYearTxtField.getText();
        String name = nameTxtField.getText();
        String outcome_subtype = outSubTypeTxtField.getText();
        String outcome_type = outTypeTxtField.getText();
        String sex_upon_outcome = sexOutTxtField.getText();
        String loc_lat = locLatTxtField.getText();
        String loc_long = locLongTxtField.getText();
        String age_upon_outcome_in_weeks = ageOutWeeksTxtField.getText();

        // Checking for blank text fields. These are considered the minimum to identify and animal
        if (animal_id.isEmpty() || animal_type.isEmpty() || breed.isEmpty() || color.isEmpty()
                || name.isEmpty() || sex_upon_outcome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter the following at minimum: ID, Type, Breed, Color, Name, Sex",
                    "Try again.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        animal = addAnimalToDatabase(entry, age_upon_outcome, animal_id, animal_type, breed, color, date_of_birth,
                                     datetime, monthyear, name, outcome_subtype, outcome_type, sex_upon_outcome,
                                      loc_lat, loc_long, age_upon_outcome_in_weeks);
        if (animal != null){
            dispose();
            JOptionPane.showMessageDialog(this,
                    "New Entry Successfully Added");
            clearTextFields();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register animal.",
                    "Try again.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Animal animal;
    private Animal addAnimalToDatabase(int entry, String age_upon_outcome, String animal_id, String animal_type, String breed,
                                     String color, String date_of_birth, String datetime, String monthyear, String name,
                                     String outcome_subtype, String outcome_type, String sex_upon_outcome,
                                     String loc_lat, String loc_long, String age_upon_outcome_in_weeks) {
        Animal animal = null;
        final String DB_URL= "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);
            // Connected to the database successfully...

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO aac_animals (entry, age_upon_outcome, animal_id, animal_type, breed, color, date_of_birth," +
                    "datetime, monthyear, name, outcome_subtype, outcome_type, sex_upon_outcome, location_lat," +
                    "location_long, age_upon_outcome_in_weeks) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, entry);
            preparedStatement.setString(2, age_upon_outcome);
            preparedStatement.setString(3, animal_id);
            preparedStatement.setString(4, animal_type);
            preparedStatement.setString(5, breed);
            preparedStatement.setString(6, color);
            preparedStatement.setString(7, date_of_birth);
            preparedStatement.setString(8, datetime);
            preparedStatement.setString(9, monthyear);
            preparedStatement.setString(10, name);
            preparedStatement.setString(11, outcome_subtype);
            preparedStatement.setString(12, outcome_type);
            preparedStatement.setString(13, sex_upon_outcome);
            preparedStatement.setString(14, loc_lat);
            preparedStatement.setString(15, loc_long);
            preparedStatement.setString(16, age_upon_outcome_in_weeks);

            // Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                animal = new Animal();
                animal.entry = entry;
                animal.age_upon_outcome = age_upon_outcome;
                animal.animal_id = animal_id;
                animal.animal_type = animal_type;
                animal.breed = breed;
                animal.color = color;
                animal.date_of_birth = date_of_birth;
                animal.datetime = datetime;
                animal.monthyear = monthyear;
                animal.name = name;
                animal.outcome_subtype = outcome_subtype;
                animal.outcome_type = outcome_type;
                animal.sex_upon_outcome = sex_upon_outcome;
                animal.location_latitude = loc_lat;
                animal.location_longitude = loc_long;
                animal.age_upon_outcome_in_weeks = age_upon_outcome;
            }

            statement.close();
            connection.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return animal;
    }

    // Clear the text fields in order to enter another animal
    public void clearTextFields(){

        entryTxtField.setText("");
        ageOutTxtField.setText("");
        animalIdTxtField.setText("");
        animalTypeTxtField.setText("");
        breedTxtField.setText("");
        colorTxtField.setText("");
        birthdayTxtField.setText("");
        dateTimeTxtField.setText("");
        monthYearTxtField.setText("");
        nameTxtField.setText("");
        outSubTypeTxtField.setText("");
        outTypeTxtField.setText("");
        sexOutTxtField.setText("");
        locLatTxtField.setText("");
        locLongTxtField.setText("");
        ageOutWeeksTxtField.setText("");

    }
};
