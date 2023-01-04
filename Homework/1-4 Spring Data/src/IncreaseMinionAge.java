import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class IncreaseMinionAge {
    //изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
    private  static  final  String INCREASE_MINION_AGE = "update minions set age = age + 1, name = lower(name) where id = ?";
    private  static  final  String GET_ALL_MINIONS = "select m.name, m.age from minions as m";
    private  static  final  String PRINT_MINION_INFORMATION = "%s %d%n";
    private  static  final  String AGE = "age";

    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на заданието
        Scanner scanner = new Scanner(System.in);
//прочитане и взимане на променливи от входа
        Connection connection = Utils.getSqlConnections();
//създаваме лист от миньони по id
        List<Integer> minionIds = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toList();

        PreparedStatement increaseMinionAge = connection.prepareStatement(INCREASE_MINION_AGE);
//обхождаме и сетваме миньоните
        for (Integer minionId : minionIds) {
            increaseMinionAge.setInt(1, minionId);
            increaseMinionAge.executeUpdate();
        }
//взимаме всички миньони
        PreparedStatement getAllMinions = connection.prepareStatement(GET_ALL_MINIONS);
        ResultSet allMinions = getAllMinions.executeQuery();
//докато съществува минион отпечатваме техните имена и възраст
        while (allMinions.next()) {
            String minionName = allMinions.getString(Constants.NAME);
            int minionAge = allMinions.getInt(AGE);
            System.out.printf(PRINT_MINION_INFORMATION, minionName, minionAge);
        }
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
}
