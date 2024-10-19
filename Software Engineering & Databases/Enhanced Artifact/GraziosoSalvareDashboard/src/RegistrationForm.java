import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JFrame{
    private JPanel registrationPanel;
    private JLabel registrationLabel;
    private JTextField addressTxtField;
    private JTextField phoneTxtField;
    private JTextField lastNameTxtField;
    private JTextField firstNameTxtField;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private JPasswordField passwordTxtField;
    private JLabel phoneLabel;
    private JTextField emailTxtField;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel confirmPasswordLabel;
    private JLabel passwordLabel;
    private JButton cancelRegisterButton;
    private JButton submitButton;
    private JLabel registerDogIcon;
    private JPasswordField confirmPassTxtField;

    // Cancel operation and return to previous screen
    public void returnToLogin(){
        dispose();
        JFrame frame = new LoginForm("Grazioso-Salvare Login");
        frame.setVisible(true);
    }

    public RegistrationForm(String title) {
        super(title);
        setTitle("Create a New Account");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(registrationPanel);
        this.setLocationRelativeTo(getParent());
        this.setMinimumSize(new Dimension(600, 300));
        this.pack();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToLogin();
            }
        });
    }

    // User account constructor
    private void registerUser() {
        String firstName = firstNameTxtField.getText();
        String lastName = lastNameTxtField.getText();
        String email = emailTxtField.getText();
        String phone = phoneTxtField.getText();
        String address = addressTxtField.getText();
        String password = String.valueOf(passwordTxtField.getPassword());
        String confirmPassword = String.valueOf(confirmPassTxtField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
                || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields.", "Try again.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm password does not match.", "Try again.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(firstName, lastName, email, phone, address, password);
        if (user != null){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register user.",
                    "Try again.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Communicates with database to add newly created user account to users table
    public User user;
    private User addUserToDatabase(String firstName, String lastName, String email, String phone, String address, String password){
        User user = null;
        final String DB_URL= "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);
            // Connected to the database successfully...

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO users (firstName, lastName, email, phone, address, password) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, password);

            // Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.firstName = firstName;
                user.lastName = lastName;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;
            }

            statement.close();
            connection.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
