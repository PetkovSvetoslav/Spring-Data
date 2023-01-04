import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    //    изнасяме sql кода/куери и нужните променливите,в нови, за по добра четимост
    private  static  final  String GET_ALL_VILLAINS = "select v.name from villains as v  where v.id = ?";
    private  static  final  String NO_SUCH_VILLAINS = "No such villain was found";
    private  static  final  String GET_MINIONS_COUNT_BY_VILLAINS_ID =
            "select count(mv.minion_id) as m_count from minions_villains as mv where mv.villain_id = ?";
    private  static  final  String COUNT_MINIONS = "m_count";
    private  static  final  String DELETE_MINIONS_VILLAINS = "delete from minions_villains as mv where mv.villain_id = ?";
    private  static  final  String DELETE_VILLAINS = "delete from villains as v where v.id = ?";
    private  static  final  String DELETED = "%s was deleted%n";
    private  static  final  String DELETED_MINIONS_COUNT = "%d minions released%n";

    public static void main(String[] args) throws SQLException {
//създаваме "матрицата" за четене и принтиране на заданието
        final Connection connection = Utils.getSqlConnections();
//прочитане и взимане на променливи от входа
        final Scanner scanner = new Scanner(System.in);
        final int villainId = scanner.nextInt();

        final PreparedStatement selectedVillain = connection.prepareStatement(GET_ALL_VILLAINS);
        selectedVillain.setInt(1,villainId);
        final ResultSet villainResultSet = selectedVillain.executeQuery();
//проверяваме дали злодеят съществува
        if(!villainResultSet.next()){
            System.out.println(NO_SUCH_VILLAINS);
            return;
        }
//взимаме името на злодея
       final String villainName = villainResultSet.getString(Constants.NAME);

       final PreparedStatement allMinionsCount = connection.prepareStatement(GET_MINIONS_COUNT_BY_VILLAINS_ID);
        allMinionsCount.setInt(1,villainId);
//миньоните
        final ResultSet countMinionsSet = allMinionsCount.executeQuery();
        countMinionsSet.next();
//изтритите миньони
        final int countDeletedMinions = countMinionsSet.getInt(COUNT_MINIONS);
        connection.setAutoCommit(false);
//трием, сетваме и обновяваме
        try (
            PreparedStatement deleteMinionStatement = connection.prepareStatement(DELETE_MINIONS_VILLAINS);
            PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_VILLAINS);
            ){
//сетваме параметрите
            deleteMinionStatement.setInt(1, villainId);
            deleteMinionStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();
//качваме нашата инова информация и принтираме по заданието
            connection.commit();
            System.out.printf(DELETED, villainName);
            System.out.printf(DELETED_MINIONS_COUNT, countDeletedMinions);

        } catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
//затваряме конекцията за да не оставим потокът от данни отворен
        connection.close();
    }
}
