import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    //    изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
      private  static  final  String MINIONS_NAMES_AGE =
            "select m.name, m.age from minions as m" +
                    " join minions_villains mv on m.id = mv.minion_id" +
                    " where mv.villain_id = ?";

      private  static  final  String VILLAINS_NAMES = "select v.name from villains as v where v.id = ?";
      private  static  final  String NO_VILLAIN = "No villain with ID %d exists in the database.";
      private  static  final  String AGE = "age";
      private  static  final  String PRINT_MATRIX_VILLAINS = "Villain: %s%n";
      private  static  final  String PRINT_MATRIX_MINIONS = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на заданието

        final Connection connection = Utils.getSqlConnections();
//* добавяме  променливата която ще четем/въвеждаме
        final int villainId = new Scanner(System.in).nextInt();
//взимаме името на злодея по id
        final PreparedStatement villainResultById = connection.prepareStatement(VILLAINS_NAMES);

// задаваме параметър или опцията за въвеждане на променлива
        villainResultById.setInt(1, villainId);

        final ResultSet villainSet = villainResultById.executeQuery();
//проверка дали има резултат
        if(!villainSet.next()){
            System.out.printf(NO_VILLAIN,villainId);
//затваряме конекцията за да не оставим потокът от данни отворен
            connection.close();
            return;
        }
        final String villainName = villainSet.getString(Constants.NAME);
//принтираме част от резултата
        System.out.printf(PRINT_MATRIX_VILLAINS,villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(MINIONS_NAMES_AGE);
        minionsStatement.setInt(1, villainId);

        final ResultSet minionsSet = minionsStatement.executeQuery();
//принтираме остатъка от резултата
        for (int i = 1; minionsSet.next(); i++) {
            final String minionsName = minionsSet.getString(Constants.NAME);
            final int minionsAge = minionsSet.getInt(AGE);
            System.out.printf(PRINT_MATRIX_MINIONS,i, minionsName,minionsAge);
        }
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
}
