import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
//    изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
      private  static  final  String VILLAINS_NAMES =
            "select  v.name, count(distinct mv.minion_id) as mi_count from villains as v" +
            " join minions_villains as mv on v.id = mv.villain_id" +
            " group by mv.villain_id having mi_count > ?" +
            " order by mi_count";

//    private  static  final  String NAME = "v.name";
      private  static  final  String MINIONS_COUNT = "mi_count";
      private  static  final  String PRINT_MATRIX = "%s %d";


    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на sql заданието
       final Connection connection = Utils.getSqlConnections();

       final PreparedStatement statement = connection.prepareStatement(VILLAINS_NAMES);

//       задаваме параметър или опцията за въвеждане на променлива
       statement.setInt(1, 15);

       final ResultSet resultSet = statement.executeQuery();
//обхождаме и принтираме резултата
      while (resultSet.next()){
         final  String getVillainName = resultSet.getString(Constants.NAME);
         final int minionsCount = resultSet.getInt(MINIONS_COUNT);
          System.out.printf(PRINT_MATRIX,getVillainName, minionsCount);
        }
//затваряме конекцията за да не оставим потокът от данни отворен
      connection.close();
    }
}
