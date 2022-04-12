import com.datastax.driver.core.*;
import exceptions.ArgumentNotMatchedException;
import exceptions.QueryNotExistException;
import queries.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

public class QueryRunner {
    private String query;
    private Cluster cluster;
    private Query q;


    public QueryRunner(String query, Cluster cluster) throws QueryNotExistException, ArgumentNotMatchedException {
        if (!checkQuery(query)) throw new QueryNotExistException();

        this.query = query;
        this.cluster = cluster;
        this.q = QueryFactory.fetch(query);
    }

    public void run() throws ParseException, NumberFormatException{
        q.run(cluster);
    }

    private boolean checkQuery(String query) {
        if (query.charAt(0) != 'Q') return false;
        String num = query.substring(1);
        try {
            int number = Integer.parseInt(num);
            if (number < 1 || number > 25) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean checkArguments(String query, String[] arguments) {
        return true;
    }


    public List<String> getArgs() {
        return q.getAllRequiredArgs();
    }

    public void addArg(String input) {
        q.addArg(input);
    }
}
