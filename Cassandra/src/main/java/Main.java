import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import exceptions.ArgumentNotMatchedException;
import exceptions.QueryNotExistException;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // open cassandra
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Metadata metadata = cluster.getMetadata();
        for (Host host : metadata.getAllHosts()) {
            System.out.println("===>" + host.getAddress());
        }
        System.out.println("---------------------------");
        for (KeyspaceMetadata keyspaceMetadata : metadata.getKeyspaces()) {
            System.out.println("===>" + keyspaceMetadata.getName());
        }

        // read user input
        Scanner command = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Please enter your command: ");
            
            switch (command.nextLine()) {
                case "exit":
                    System.out.println("Application close");
                    System.exit(0);
                    break;
                case "ls":
                    System.out.println("List all supported queries");
                    listAllQuery();
                    break;
                case "help":
                    printHelp();
                    break;
                case "query":
                    System.out.println("Select a query you want to execute");
                    String query = command.nextLine();
                    try {
                        QueryRunner qr = new QueryRunner(query, cluster);
                        List<String> requiredArgs = qr.getArgs();
                        for (String arg : requiredArgs) {
                            System.out.println("Please enter the value for argument " + arg + " :");
                            String input = command.nextLine();
                            qr.addArg(input);
                        }
                        qr.run();
                    } catch (QueryNotExistException qe) {
                        System.out.println("The Query you entered was not correct");
                        System.out.println("Query should be in form like Q + number (e.g Q3)");
                        System.out.println("Also, number should be in [1,25]");
                    } catch (ArgumentNotMatchedException ae) {
                        System.out.println("The argument(s) you entered were not correct");
                    } catch (ParseException pe) {
                        System.out.println("Date format is not correct. Expecting yyyy/MM/dd HH:mm:ss");
                    } catch (NumberFormatException ne) {
                        System.out.println("Expect a number, but receive wrong type");
                    }
                    break;
                default:
                    System.out.println("No command is found, please command \"help\" to gain more information");
                    break;
            }
        }
        command.close();
        cluster.close();
    }

    private static void printHelp() {
        System.out.println("Help");
    }

    private static void listAllQuery() {
        System.out.println("All queries");
    }
    
    


}
