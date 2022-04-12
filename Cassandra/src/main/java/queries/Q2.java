package queries;

import com.datastax.driver.core.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Q2 extends Query{
    private String query;

    public Q2() {
        super();
        List<String> args = new ArrayList<>();
        args.add("Categories.dummy");
        super.setRequiredArgs(args);
        List<String> types = new ArrayList<>();
        types.add("byte");
        super.setArgTypes(types);
        query = "SELECT id, name FROM Categories WHERE dummy = ? ALLOW FILTERING";
    }


    @Override
    public void run(Cluster cluster) throws ParseException {
        List<Row> dataList = super.run(cluster, query);
        System.out.println("------------------Result------------------");
        for(Row row : dataList) {
            System.out.print("id: " + row.getInt("id") + "         ");
            System.out.println("name: " + row.getString("name"));
        }
        System.out.println("------------------------------------------");
    }
}
