import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class handles displaying the table without any filtering applied. It is used when the user first logs in
//  and also when they select to remove all filters
public class DisplayFullTable {
    static JComponent displayTable() {
        final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";

        String path = System.getProperty("user.dir") + "\\src\\aac_shelter_notepad.csv";

        JTable table;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String rowQuery = "SELECT * FROM aac_animals";
            int rowCount = getRowCount(rowQuery);
            ResultSet resultSet = connection.createStatement().executeQuery(rowQuery);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            // Hard coding the column names was the only way I knew how to get the correct data type
            //  and get the columns to show up in the app
            String[] columns = new String[]{"Entry", "Age Upon Outcome", "Animal ID#", "Animal Type",
                    "Breed", "Color", "Date of Birth", "Date/Time", "Month/Year",
                    "Name", "Outcome Subtype", "Outcome Type", "Sex Upon Outcome",
                    "Location Latitude", "Location Long", "Age Upon Outcome (Weeks)"};

            // This version of the data object was an attempt to get table parameters from the database
            //Object[][] data = new Object[rowCount][columns.length];

            // I hardcoded the data object to avoid errors. I spent a lot of time trying to have a more flexible
            //  way to get column names and data, but I have a lot more to learn
            Object[][] data = new Object[10000][columns.length];

            // I first constructed the tableData ArrayList, then used it to populate the data Object[][]
            List<String[]> tableData = new ArrayList<>();
            table = new JTable(data, columns);
            table.setFillsViewportHeight(false);
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(","); // Assuming comma-separated values
                tableData.add(row);
            }
            for (int i = 0; i < tableData.size(); i++) {
                for (int j = 0; j < tableData.get(i).length; j++) {
                    data[i][j] = tableData.get(i)[j];
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }

    // This method is for getting the rowCount from the database but can't yet be used to construct the table because
    //  I am still getting table data from the CSV file. Will fix soon.
    private static int getRowCount(String query) throws SQLException {
        final String DB_URL = "jdbc:mysql://localhost:3306/graziososalvare";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        int rowCount;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);
            rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
            }
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
        return rowCount;
    }
}

