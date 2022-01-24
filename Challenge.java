import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;

public class Challenge {
    public static void main(String[] args) throws MalformedURLException, IOException, ProtocolException {

        URL url = new URL("https://interview.outstem.io/tests?test_case=<TEST_CASE_NUMBER_1-6>");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        Scanner scanner = new Scanner(url.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        JSONObject myObject = new JSONObject(response);
        scanner.close();

        String[] data = myObject.getString("data");

        String command = myObject.getString("query");
        String[] commands = command.split(",");

        ArrayList<Prof> profs = new ArrayList<Prof>();

        for (int i = 0; i < data.length; i++) {

            Prof newProf = new Prof();
            newProf.name = data[i];
            newProf.views = Integer.parseInt(data[i + 1]);
            newProf.subject = data[i + 2];
            i += 3;
            profs.add(newProf);

        }

        String[] returnArray;

        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case "EducatorOnline":
                    Prof newProf = new Prof();
                    newProf.name = commands[i];
                    newProf.views = Integer.parseInt(commands[i + 1]);
                    newProf.subject = commands[i + 2];
                    i += 2;
                    profs.add(newProf);

                    break;

                case "UpdateViews":
                    for (Prof prof : profs) {
                        if (prof.name.equals(commands[i + 1])) {
                            if (prof.subject.equals(commands[i + 3])) {
                                prof.views = Integer.parseInt(commands[i + 2]);
                            }
                        }
                    }
                    i += 3;

                    break;

                case "UpdateSubject":
                    for (Prof prof : profs) {
                        if (prof.name.equals(commands[i + 1])) {
                            if (prof.subject.equals(commands[i + 2])) {
                                prof.subject = commands[i + 3];
                            }
                        }
                    }
                    i += 3;

                    break;

                case "EducatorOffline":
                    for (Prof prof : profs) {
                        if (prof.name.equals(commands[i + 1])) {
                            if (prof.subject.equals(commands[i + 2])) {
                                profs.remove(prof);
                            }
                        }
                    }
                    i += 2;

                    break;

                case "ViewsInSubject":
                    int count = 0;
                    for (Prof prof : profs) {
                        if (prof.subject.equals(commands[i + 1])) {
                            count += prof.views;
                        }
                    }
                    i += 1;
                    returnArray[0] = String.valueOf(count);

                    break;

                case "TopEducatorOfSubject":
                    String topEducator = null;
                    int topViews = 0;
                    for (Prof prof : profs) {
                        if (prof.subject.equals(commands[i + 1])) {
                            if (prof.views > topViews) {
                                topViews = prof.views;
                                topEducator = prof.name;
                            }
                        }
                    }
                    if (topEducator != null) {
                        returnArray[0] = topEducator;
                    }
                    i += 1;

                    break;

                case "TopEducator":
                    topEducator = null;
                    topViews = 0;
                    for (Prof prof : profs) {
                        if (prof.views > topViews) {
                            topViews = prof.views;
                            topEducator = prof.name;
                        }
                    }
                    if (topEducator != null) {
                        returnArray[0] = String.valueOf(topViews);
                        returnArray[1] = topEducator;
                    }

                    break;
            }
        }

        System.out.println("Hello World!");
    }
}