package controller;

import Service.JPAUsers;
import Service.JPAUsersImpl;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private String method;
    private String url = "http://localhost:8080/api/users";
    private final String versionProtocol = "HTTP/1.1";
    private String headers;

    private String body;
    private int id;//para consultas por Id

    @Autowired
    private JPAUsers user;//principio de Inversión de Dependencias (IoD)

    public Server(String method, String url, String headers, String body) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.user = new JPAUsersImpl();//una referencia q se ha creado en el constructor de la clase Server JPAUsersImpl
        //validamos que tipo de Método nos llegó al servidor

    }//fin si


    public Server(String method, String url, String headers, String body, int id) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.id = id;
        this.user = new JPAUsersImpl();//una referencia q se ha creado en el constructor de la clase Server JPAUsersImpl

        switch (method) {
            case "PUT":
                if (url.equals("http://localhost:8080/api/users")) {
                    PutUserById(body, id);
                }
                break;
            case "DELETE":
                if (url.equals("http://localhost:8080/api/users")) {
                    DeleteUserById(id);
                }
                break;
            case "GET":
                if (url.equals("http://localhost:8080/api/users")) {
                    GetUserById(id);
                }
                break;
            default:
                System.out.println("Método no soportado");
                break;
        }


    }//end constructor


    public String GetUsers() {
        List<User> users = user.readAll();
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("URL: ").append(url).append("\n")
                .append(versionProtocol).append(" 200 Ok\r\n")
                .append("Content-Type: application/json\r\n")
                .append("Content-Length: ").append(users.toString().length()).append("\r\n")
                .append("\r\n");
        for (User user : users) {
            responseBuilder.append(user.toString()).append("\n");
        }
        responseBuilder.append("Method: GET");
        return responseBuilder.toString();
    }

    public String GetUserById(int id) {
        String response = "URL: " + url + "\n" +
                versionProtocol + " 200 OK\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + id + "\r\n" +
                "\r\n" +
                "Method: GET";
        user.findById(id);
        return response;
    }

    public String PostUser(String names, String email, String phone) {
        User newUser = new User(0, names, email, phone);
        user.create(newUser);

        String response = "URL: " + url + "\n" +
                versionProtocol + " 201 Created\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + newUser.toString().length() + "\r\n" +
                "\r\n" +
                newUser.toString() + "\n" +
                "Method: POST";
        return response;
    }


    public String DeleteUserById(int id) {
        user.deleteById(id);
        String response = "URL: " + url + "\n" +
                versionProtocol + " 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n" +
                "Se ha eliminado el usuario con Id: " + id + "\n" +
                "Method: DELETE";
        return response;
    }

    public String PutUserById(String body, int id) {
        String[] userDetails = body.split(",");
        User updatedUser = new User(id, userDetails[0], userDetails[1], userDetails[2]);
        user.updateById(updatedUser, id);
        String response = "URL: " + url + "\n" +
                versionProtocol + " 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + updatedUser.toString().length() + "\r\n" +
                "\r\n" +
                "Se ha actualizado el usuario con éxito y sus datos son:\n" +
                "Id:" + updatedUser.getId() + ", " +
                "Nombres: " + updatedUser.getNames() + ", " +
                "Email: " + updatedUser.getEmail() + ", " +
                "Teléfono: " + updatedUser.getPhone() + "\n" +
                "Method: PUT";
        return response;
    }


    @Override
    public String toString() {
        return "Server{" +
                "url='" + url + '\'' +
                ", headers='" + headers + '\'' +
                ", method='" + method + '\'' +
                ",\n body='" + body + '\'' +

                '}';

    }


}