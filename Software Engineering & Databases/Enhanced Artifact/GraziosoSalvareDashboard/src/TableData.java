import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableData {

    public List<String[]> TableData() {
        String path = System.getProperty("user.dir") + "\\src\\tableTesting.csv";
        List<String[]> tableData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(","); // Assuming comma-separated values
                tableData.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String[] row : tableData) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        return tableData;
    }
}