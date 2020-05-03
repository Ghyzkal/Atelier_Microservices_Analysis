import com.google.gson.Gson;
import service.GatewayService;


import static spark.Spark.*;


public class GatewayDomain {
    private static GatewayService gatewayService = new GatewayService();
    private static int PORT = 4566;

    public static void main(String[] args) {
        Gson gson = new Gson();

        port(GatewayDomain.PORT);

        get("/test", (request, response) -> {
            response.type("application/json");
            return gatewayService.test();
        }, gson::toJson);

        get("/help", (request, response) -> {
            response.type("application/json");
            return gatewayService.help();
        }, gson::toJson);

        get("/:domain/:action", (req, res) -> {
            res.type("application/json");
            String domain = req.params(":domain");
            String action = req.params(":action");
            return gatewayService.gateway(domain,action);
        }, gson ::toJson);
    }
}
