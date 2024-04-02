package alex.greendata.unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitSupport {

    public static boolean isLess(List<Object> maps, String column, int index, int value) {
        Map<String, String> lastMap = (Map<String, String>) maps.get(index - 1);
        Map<String, String> map = (Map<String, String>) maps.get(index);
        int lastNumber = Integer.parseInt(lastMap.get(column));
        int number = Integer.parseInt(map.get(column));

        boolean isLess = true;
        if (lastNumber > value || number > value)
            isLess = false;

        return isLess;
    }

    public static boolean isRightOrder(List<Object> maps, String column, int index, boolean isAsc) {
        Map<String, String> lastMap = (Map<String, String>) maps.get(index - 1);
        Map<String, String> map = (Map<String, String>) maps.get(index);
        int lastNumber = Integer.parseInt(lastMap.get(column));
        int number = Integer.parseInt(map.get(column));

        boolean isRightOrder = true;
        if (isAsc) {
            if (lastNumber > number)
                isRightOrder = false;
        } else {
            if (lastNumber < number)
                isRightOrder = false;
        }

        return isRightOrder;
    }

    public static Map<String, String> mapRequest(String location) throws IOException {

        String url = "http://localhost:8080/";

        URL address = new URL(url + location);
        HttpURLConnection connection = (HttpURLConnection) address.openConnection();
        connection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        String answerString = "";
        while ((line = bufferedReader.readLine()) != null) {
            answerString += line;
        }
        bufferedReader.close();

        answerString = answerString.replace("[", "")
                .replace("]", "")
                .replace("{", "")
                .replace("}", "");

        Map<String, String> map = convertStringToMap(answerString);

        return map;
    }

    public static List<Object> listRequest(String location) throws IOException {

        String url = "http://localhost:8080/";

        URL address = new URL(url + location);
        HttpURLConnection connection = (HttpURLConnection) address.openConnection();
        connection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

        String line;
        String answerString = "";
        while ((line = bufferedReader.readLine()) != null) {
            answerString += line;
        }
        bufferedReader.close();

        List<Object> answer = new ArrayList<>();

        String[] answerStringArray = answerString
                .replace("[", "").replace("{", "").replace("}]", "")
                .split("},");

        for (int i = 0; i < answerStringArray.length; i++) {
            Map<String, String> map = convertStringToMap(answerStringArray[i]);
            answer.add(map);
        }

        return answer;
    }

    public static Map<String, String> convertStringToMap(String data) {
        // "address":"Земля","name":"Корпорация Уоллес","organization_form":"Товарищество","id":1,"shortname":"Уоллес"
        Map<String, String> map = new HashMap<>();

        String[] mapString = data.split(",");
        for (int i = 0; i < mapString.length; i++) {
            String[] parameter = mapString[i].replace("\"", "").split(":");
            if (parameter.length > 1)
                map.put(parameter[0], parameter[1]);
        }

        return map;
    }

}
