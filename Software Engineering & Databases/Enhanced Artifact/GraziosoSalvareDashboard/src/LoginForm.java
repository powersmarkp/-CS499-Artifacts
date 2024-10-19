import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    private JPanel loginPanel;
    private JButton loginButton;
    private JButton registerNewUserButton;
    private JTextField passwordTxtField;
    private JTextField userTxtField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel grazDogIcon;

    // This page contains the Main method as the login screen will be the first one the users are presented with
    public LoginForm(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.setMinimumSize(new Dimension(400, 300));
        this.setLocationRelativeTo(getContentPane());
        this.pack();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm regForm = new RegistrationForm(null);
                dispose();
                regForm.setVisible(true);
                User user = regForm.user;
                if (user != null) {
                    System.out.println("Successful registration of " + user.firstName + " " + user.lastName);
                }
                else {
                    System.out.println("Registration canceled.");
                }
            }
        });
    }

    // Will check entered credentials against user table in database.
    private void login() {
        String email = userTxtField.getText();
        String password = passwordTxtField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Email address and password are required to continue.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (!email.isEmpty() && !password.isEmpty()) {
            final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
            final String USERNAME = "root";
            final String PASSWORD = "";

            try {
                Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                String loginQuery = "SELECT * FROM users WHERE email = '" + userTxtField.getText() + "'"+
                        " AND password = '" + passwordTxtField.getText() + "'";
                ResultSet resultSet = connection.createStatement().executeQuery(loginQuery);

                if (resultSet.next() == true) {
                    dispose();
                    DashboardForm dashForm = new DashboardForm("Grazioso-Salvare Dashboard");
                    dashForm.setVisible(true);
                }
                else if (resultSet.next() == false) {
                    JOptionPane.showMessageDialog(null,
                            "Login not found or password incorrect. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new LoginForm("Welcome to the AAC Database");
        frame.setVisible(true);
    }
}