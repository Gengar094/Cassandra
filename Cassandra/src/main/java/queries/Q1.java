package queries;

import com.datastax.driver.core.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Q1 extends Query{
    private String query;

    public Q1() {
        super();
        List<String> args = new ArrayList<>();
        args.add("Users.id");
        super.setRequiredArgs(args);
        List<String> types = new ArrayList<>();
        types.add("int");
        super.setArgTypes(types);
        query = "SELECT nickname, password FROM Users WHERE id = ?";
    }

    @Override
    public void run(Cluster cluster) throws ParseException, NumberFormatException {
        List<Row> dataList = super.run(cluster, query);
        System.out.println("------------------Result------------------");
        for(Row row : dataList) {
            System.out.print("nickname: " + row.getString("nickname") + "     ");
            System.out.println("password: " + row.getString("password"));
        }
        System.out.println("------------------------------------------");
    }

}
