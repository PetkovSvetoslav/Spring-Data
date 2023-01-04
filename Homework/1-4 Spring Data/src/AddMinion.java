import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddMinion {
//изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
      private  static  final  String GET_TOWN = "select t.id from towns as t where t.name = ?";
      private  static  final  String ADD_TOWN = "insert into towns(name) values(?)";
      private  static  final  String PRINT_MATRIX_TOWN = "Town %s was added to the database.%n";
      private  static  final   String  ID = "id";

      private  static  final  String GET_VILLAIN  = "select v.id from villains as v where v.name = ?";
      private  static  final  String ADD_VILLAIN = "insert into villains(name,evilness_factor) values(?,?)";
      private  static  final  String EVILNESS_FACTOR = "evil";
      private  static  final  String PRINT_MATRIX_ADD_VILLAIN = "Villain %s was added to the database.%n";

      private  static final  String ADD_INTO_MINIONS = "insert into minions(minions.name, age, town_id) values(?,?,?)";
      private  static final  String LAST_MINION = "select m.id from minions as m order by m.id desc limit 1";
      private  static final  String ADD_MINIONS_TO_VILLAIN = "insert into minions_villains(minion_id,villain_id) values(?,?)";
      private  static  final  String PRINT_MATRIX_VILLAIN_MINIONS = "Successfully added %s to be minion of %s.%n";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSqlConnections();

//прочитане и взимане на променливи от входа
        final Scanner scanner = new Scanner(System.in);

        final String[] minionInformation = scanner.nextLine().split(" ");
        final String minionName = minionInformation[1];
        final int minionAge = Integer.parseInt(minionInformation[2]);
        final String minionTown = minionInformation[3];

        final String[] villainInformation = scanner.nextLine().split(" ");
        final String villainName = villainInformation[1];
//променливи обработващи входа чрез метод getId
        final int townId = getId(connection, List.of(minionTown),GET_TOWN,ADD_TOWN,PRINT_MATRIX_TOWN);
        final int villainId = getId(connection,List.of(villainName,EVILNESS_FACTOR),GET_VILLAIN,ADD_VILLAIN,PRINT_MATRIX_ADD_VILLAIN);
//обработка на базата
//добавяме липсващият миньон и го сетваме
        final PreparedStatement minionStatement = connection.prepareStatement(ADD_INTO_MINIONS);
        minionStatement.setString(1,minionName);
        minionStatement.setInt(2, minionAge);
        minionStatement.setInt(3,townId);
//обновяване на информацията
        minionStatement.executeUpdate();
//взимаме последния миньон, за да го добавим към злодея
        final PreparedStatement lastMinion = connection.prepareStatement(LAST_MINION);
        final ResultSet resultSet = lastMinion.executeQuery();
        resultSet.next();
        final int lastMinionId = resultSet.getInt(ID);
//добавяме миньона към злодея
        final PreparedStatement addMinionsVillains = connection.prepareStatement(ADD_MINIONS_TO_VILLAIN);
        addMinionsVillains.setInt(1, lastMinionId);
        addMinionsVillains.setInt(2, villainId);
//обновяване на информацията
        addMinionsVillains.executeUpdate();
//отпечатване
        System.out.printf(PRINT_MATRIX_VILLAIN_MINIONS, minionName, villainName);
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
//....................................
//създаваме метод за преизползване
    public static int getId(Connection connection, List<String> arguments,
                            String selectQuery, String insertQuery, String printFormat) throws SQLException {
        final String name = arguments.get(0);
//създаваме куери което извлича информацията за от name
        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, name);

        final ResultSet resultSet = selectStatement.executeQuery();
//проверка дали съществува
        if (!resultSet.next()) {
            final PreparedStatement statement = connection.prepareStatement(insertQuery);
//добавяне на аргументи
            for (int i = 1; i <= arguments.size(); i++) {
                statement.setString(i,arguments.get(i-1));
            }
//обновяване на информацията
            statement.executeUpdate();

            final ResultSet newResultSet = selectStatement.executeQuery();
            newResultSet.next();
//отпечатване и връщане и сетване на резултата
            System.out.printf(printFormat, name);
            return newResultSet.getInt(ID);
        }

        return resultSet.getInt(ID);
    }

    }




