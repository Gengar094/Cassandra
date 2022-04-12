package queries;

import com.datastax.driver.core.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Query {
    private List<String> requiredArgs;
    private List<String> inputArgs;
    private List<String> argTypes;

    public List<String> getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(List<String> argTypes) {
        this.argTypes = argTypes;
    }

    public Query() {
        inputArgs = new ArrayList<>();
    }

    public List<String> getAllRequiredArgs() {
        return requiredArgs;
    }

    public void addArg(String input) {
        inputArgs.add(input);
    }

    public abstract void run(Cluster cluster) throws ParseException;

    public List<String> getInputArgs() {
        return inputArgs;
    }

    public void setRequiredArgs(List<String> requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

    public List<Row> run(Cluster cluster, String query) throws ParseException {
        Session session = cluster.connect("rubis");
        PreparedStatement statement = session.prepare(query);
        List<String> args = getInputArgs();
        List<String> types = getArgTypes();
        BoundStatement bind = statement.bind();
        for (int i = 0; i < args.size(); i++) {
            switch (types.get(i)) {
                case "int":
                    bind = bind.bind(Integer.parseInt(args.get(i)));
                    break;
                case "String":
                    bind = bind.bind(args.get(i));
                    break;
                case "Date":
                    bind = bind.bind(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(args.get(i)));
                    break;
                case "byte":
                    bind = bind.bind(Byte.parseByte(args.get(i)));
            }
        }
        ResultSet rs = session.execute(bind);

        return rs.all();
    }


}
