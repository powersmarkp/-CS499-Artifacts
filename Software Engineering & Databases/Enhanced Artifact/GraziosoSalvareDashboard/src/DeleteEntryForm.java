import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// This is the class for displaying the form for deleting animals from the database.
public class DeleteEntryForm extends JFrame {
    private JLabel deleteEntryTitle;
    private JLabel deleteEntryIcon;
    private JPanel deleteEntryPanel;
    private JTextField deleteIdTxtField;
    private JButton deleteConfirmButton;
    private JButton cancelDeleteButton;
    private JLabel animalIdLabel;
    private JLabel deleteHelpTxt;


    public DeleteEntryForm(String title) {
        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(deleteEntryPanel);
        this.setMinimumSize(new Dimension(500, 400));
        this.setLocationRelativeTo(getContentPane());
        this.pack();

        // Find the entered animal ID and delete it.
        deleteConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAnimalEntry();
            }
        });

        // Cancel operation and close delete form
        cancelDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void deleteAnimalEntry(){
        final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{

            if (deleteIdTxtField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please enter an animal ID");
            }

            // Asking for confirmation and handling input validation
            else{
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete " + deleteIdTxtField.getText() + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String sql = "DELETE FROM aac_animals WHERE animal_id = '" + deleteIdTxtField.getText() + "'";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    int deletedEntry = preparedStatement.executeUpdate();
                    if (deletedEntry > 0) {
                        JOptionPane.showMessageDialog(null,
                                "Animal ID " + deleteIdTxtField.getText() + " was deleted successfully");
                        deleteIdTxtField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Animal ID not found");
                    }
                }
                else if(choice == JOptionPane.NO_OPTION){
                    JOptionPane.showMessageDialog(null,
                            "Deletion Cancelled");
                }
            }

        }catch(Exception excep) {
            excep.printStackTrace();
        }
    }
};
