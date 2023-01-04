import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    //    изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
    private  static  final  String AGE = "age";
    private  static final String INVOKE_PROCEDURE="call usp_get_older(?)";
    private  static final String GET_MINION_NAME_AND_AGE="select name,age from minions where id=?";
    private  static final String NO_SUCH_MINION="No such minion";
    private  static final String PRINT_MINION_NAME_AND_AGE="%s %d";
    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на заданието
        Scanner scanner = new Scanner(System.in);
//* добавяме променливата която ще четем/въвеждаме
        int minionId = Integer.parseInt(scanner.nextLine());

        Connection connection = Utils.getSqlConnections();
//извикваме и сетваме процедурата
        PreparedStatement invokeProcedure=connection.prepareStatement(INVOKE_PROCEDURE);
        invokeProcedure.setInt(1,minionId);
        invokeProcedure.executeUpdate();
//взимаме и сетваме данните на миньона по id
        PreparedStatement getMinionNameAndAge=connection.prepareStatement(GET_MINION_NAME_AND_AGE);
        getMinionNameAndAge.setInt(1,minionId);
        ResultSet minionNameAndAngeSet=getMinionNameAndAge.executeQuery();
//отпечатваме съобюение ако няма вече миньон
        if (!minionNameAndAngeSet.next()){
            System.out.println(NO_SUCH_MINION);
            return;
        }
//сетваме данните на миньона
        String minionName = minionNameAndAngeSet.getString(Constants.NAME);
        int minionAge = minionNameAndAngeSet.getInt(AGE);
//отпечатваме данните на миньона
        System.out.printf(PRINT_MINION_NAME_AND_AGE,minionName,minionAge);
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
}
