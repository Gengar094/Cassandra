import queries.*;

public class QueryFactory {
    private QueryFactory() {}

    public static Query fetch(String qname) {
        Query q;
        switch (qname) {
            case "Q1":
                q = new Q1();
                break;
            case "Q2":
                q = new Q2();
                break;
            case "Q3":
                q = new Q3();
                break;
            case "Q4":
                q = new Q4();
                break;
            default:
                q = null;

        }
        return q;
    }
}
