import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeTownNamesCasing {
//изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
    private  static  final  String UPDATE_TOWN_NAME = "update towns as t set name = upper(name) where t.country = ?";
    private  static  final  String GET_ALL_TOWNS_NAME = "select t.name from towns as t  where t.country = ?";
    private  static  final  String TOWNS_AFFECTED = "%d town names were affected.%n";
    private  static  final  String NO_TOWNS_AFFECTED = "No town names were affected.%n";

    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на заданието
        final Connection connection = Utils.getSqlConnections();
//прочитане и взимане на променливи от входа
        final Scanner scanner = new Scanner(System.in);
        final  String townName = scanner.nextLine();
//добавяме името на града
       final PreparedStatement statement = connection.prepareStatement(UPDATE_TOWN_NAME);
       statement.setString(1,townName);
       final  int count = statement.executeUpdate();
//проверяваме ако няма град и принтираме съответното съобщение
        if(count == 0){
            System.out.println(NO_TOWNS_AFFECTED);
            return;
        }

        System.out.printf(TOWNS_AFFECTED, count);
//взимаме всички градове, държава и принтираме съответното съобщение
        final PreparedStatement selectTowns = connection.prepareStatement(GET_ALL_TOWNS_NAME);
        selectTowns.setString(1, townName);

        final ResultSet allTownsResultSet = selectTowns.executeQuery();
        ArrayList<String>  towns = new ArrayList<>();

        while (allTownsResultSet.next()){
            towns.add(allTownsResultSet.getString(Constants.NAME));
        }

        System.out.println(towns);
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
}
