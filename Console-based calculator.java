import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class calculator {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/", calculator::handleRequest);
        server.start();

        System.out.println("Calculator running at http://localhost:9090");
    }

    private static void handleRequest(HttpExchange exchange) throws java.io.IOException {

        String result = "";

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            String data = new String(exchange.getRequestBody().readAllBytes());
            Map<String, String> form = parseData(data);

            try {
                double a = Double.parseDouble(form.get("num1"));
                double b = form.get("num2").isEmpty() ? 0 : Double.parseDouble(form.get("num2"));
                result = calculate(a, b, form.get("operation"));
            } catch (Exception e) {
                result = "Invalid Input";
            }
        }

        String response = htmlPage(result);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // Calculation using methods
    private static String calculate(double a, double b, String op) {
        switch (op) {
            case "add": return "Addition = " + (a + b);
            case "sub": return "Subtraction = " + (a - b);
            case "mul": return "Multiplication = " + (a * b);
            case "div": return b == 0 ? "Error: Division by Zero" : "Division = " + (a / b);
            case "sqrt": return "Square Root = " + Math.sqrt(a);
            case "pow": return "Power = " + Math.pow(a, b);
            case "temp": return "Fahrenheit = " + ((a * 9 / 5) + 32);
            case "cur": return "USD = " + (a / 83);
            default: return "Invalid Operation";
        }
    }

    // Parse form data
    private static Map<String, String> parseData(String data) throws java.io.IOException {
        Map<String, String> map = new HashMap<>();
        for (String pair : data.split("&")) {
            String[] parts = pair.split("=");
            map.put(parts[0], parts.length > 1 ? URLDecoder.decode(parts[1], "UTF-8") : "");
        }
        return map;
    }

    // HTML + CSS UI
    private static String htmlPage(String result) {
        return "<!DOCTYPE html>" +
                "<html><head><title>Console-based Calculator</title>" +
                "<style>" +
                "body{font-family:Arial;background:linear-gradient(120deg,#89f7fe,#66a6ff);}" +
                ".box{width:420px;margin:60px auto;background:white;padding:25px;" +
                "border-radius:15px;box-shadow:0 10px 25px rgba(0,0,0,0.3);}" +
                "h2{text-align:center;}" +
                "input,select,button{width:100%;padding:10px;margin:8px 0;" +
                "border-radius:8px;border:1px solid #aaa;}" +
                "button{background:#66a6ff;color:white;font-size:16px;border:none;cursor:pointer;}" +
                ".result{margin-top:15px;padding:12px;background:#f1f1f1;" +
                "border-radius:8px;text-align:center;font-weight:bold;}" +
                "</style></head>" +
                "<body>" +
                "<div class='box'>" +
                "<h2>Console-based Calculator</h2>" +
                "<form method='post'>" +
                "<input type='number' step='any' name='num1' placeholder='Enter First Number' required>" +
                "<input type='number' step='any' name='num2' placeholder='Enter Second Number (optional)'>" +
                "<select name='operation'>" +
                "<option value='add'>Addition</option>" +
                "<option value='sub'>Subtraction</option>" +
                "<option value='mul'>Multiplication</option>" +
                "<option value='div'>Division</option>" +
                "<option value='sqrt'>Square Root</option>" +
                "<option value='pow'>Power</option>" +
                "<option value='temp'>Celsius to Fahrenheit</option>" +
                "<option value='cur'>INR to USD</option>" +
                "</select>" +
                "<button type='submit'>Calculate</button>" +
                "</form>" +
                "<div class='result'>" + result + "</div>" +
                "</div></body></html>";
    }
}
