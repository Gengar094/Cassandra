package queries;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Q3 extends Query{
    private String query;

    public Q3() {
        super();
        List<String> args = new ArrayList<>();
        args.add("Items.id");
        super.setRequiredArgs(args);
        List<String> types = new ArrayList<>();
        types.add("int");
        super.setArgTypes(types);
        query = "SELECT name FROM Items WHERE id = ?";
    }


    @Override
    public void run(Cluster cluster) throws ParseException {
        List<Row> dataList = super.run(cluster, query);
        System.out.println("------------------Result------------------");
        System.out.println("name");
        System.out.println("-----");
        for(Row row : dataList) {
            System.out.println(row.getString("name"));
        }
        System.out.println("------------------------------------------");
    }
}
