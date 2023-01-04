import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {

    private  static  final   String GET_ALL_MINION_NAMES ="select m.name from minions as m";

    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getSqlConnections();

        PreparedStatement getAllMinionNames = connection.prepareStatement(GET_ALL_MINION_NAMES);
        ResultSet allMinionNamesSet = getAllMinionNames.executeQuery();

        List<String> minionNames = new ArrayList<>();

        while (allMinionNamesSet.next()){
            minionNames.add(allMinionNamesSet.getString(Constants.NAME));
        }

        for (int i = 0; i < minionNames.size()/2; i++) {
            System.out.println(minionNames.get(i));
            System.out.println(minionNames.get(minionNames.size()-1-i));
        }

    }

}
