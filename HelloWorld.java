import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleWebsite {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new MyHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:8080");
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Java Website</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f4f4;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                        }

                        .card {
                            background: white;
                            padding: 40px;
                            border-radius: 12px;
                            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                            text-align: center;
                        }

                        h1 {
                            color: #333;
                        }

                        button {
                            padding: 10px 20px;
                            border: none;
                            border-radius: 8px;
                            background: #007bff;
                            color: white;
                            cursor: pointer;
                            font-size: 16px;
                        }

                        button:hover {
                            background: #0056b3;
                        }
                    </style>
                </head>
                <body>

                    <div class="card">
                        <h1>Hello from Java!</h1>
                        <p>This website is served using Java.</p>
                        <button onclick="showMessage()">Click Me</button>
                    </div>

                    <script>
                        function showMessage() {
                            alert("Java server is running!");
                        }
                    </script>

                </body>
                </html>
            """;

            exchange.sendResponseHeaders(200, html.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(html.getBytes());
            os.close();
        }
    }
}
