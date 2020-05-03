import com.google.gson.Gson;
import service.AnalyseService;

import static spark.SparkBase.setPort;

public class AnalyseDomain {
    private static AnalyseService analyseService = new AnalyseService();
    private static int PORT = 4571;

    public static void main(String[] args) {
        Gson gson = new Gson();

        //setPort(analyseService.PORT);
    }
}
