import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardForm extends JFrame {
    private JPanel dashboardPanel;
    private JTable aac_animals;
    private JButton editEntryButton;
    private JButton addAnimalButton;
    private JButton sortTableButton;
    private JButton deleteEntryButton;
    private JScrollPane tableScrollPane;
    private JLabel aacTitle;
    private JButton returnToLogin;
    private JPanel topDashPanel;
    private JPanel bottomDashPanel;
    private JPanel middleDashPanel;

    public DashboardForm(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable aac_animals = (JTable) DisplayFullTable.displayTable();
        middleDashPanel = new JPanel();
        JScrollPane tableScrollPane = new JScrollPane(aac_animals);
        this.getContentPane().add(topDashPanel, BorderLayout.NORTH);
        this.getContentPane().add(middleDashPanel, BorderLayout.CENTER);
        middleDashPanel.add(tableScrollPane, BorderLayout.WEST);
        this.getContentPane().add(bottomDashPanel, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(1200, 1000));
        aac_animals.setPreferredScrollableViewportSize(new Dimension(1080, 900));
        this.setLocationRelativeTo(getContentPane());
        this.setVisible(true);

        // Displays the screen for adding a new animal entry
        addAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEntryForm addEntryForm = new AddEntryForm("Add an Animal Entry");
                addEntryForm.setVisible(true);
            }
        });

        // This is one of the sections I had a problem implementing in the program. I had a really hard time getting
        //   the table to even show up, let alone being able to manipulate the data displayed by it.
        sortTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "FIX ME: Add Filter and Sort Functionality" );
            }
        });

        // This method works by pulling entries from the database and updating them. Changes will not be
        //   reflected in the dashboard table because it is populated by the local CSV file.
        editEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditEntryForm editEntryForm = new EditEntryForm("Edit an Animal Entry");
                editEntryForm.setVisible(true);
            }
        });

        // Like the edit button, this pulls from the database and deletes an entry, but will not be visible in the
        //  table on the dashboard.
        deleteEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteEntryForm deleteEntryForm = new DeleteEntryForm("Delete an Animal Entry");
                deleteEntryForm.setVisible(true);
            }
        });

        returnToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm("Welcome to the AAC Database");
                loginForm.setVisible(true);
                dispose();
            }
        });

    }

}