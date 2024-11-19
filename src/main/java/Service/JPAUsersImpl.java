package Service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class JPAUsersImpl implements JPAUsers {
    private static List<User> users = new ArrayList<>();
    public static User user;
    private static int idCounter = 1;

    @Override
    public void create(User user) {
        user.setId(idCounter++);
        users.add(user);
        String response = "Se ha creado el usuario con exito y sus datos son:\n" + "Id:" + user.getId() + ", " + " Nombres: " + user.getNames() + ", " +
                "Email: " + user.getEmail() + ", " + "Teléfono: " + user.getPhone();
        System.out.println(response);
    }

    @Override
    public List<User> readAll() {
        printUsers(users);
        return users;
    }

    @Override
    public void updateById(User updatedUser, int id) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setNames(updatedUser.getNames());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
                break;
            }
        }
    }

    @Override
    public void deleteById(int id) throws IndexOutOfBoundsException {
        User deletedUser = null;
        for (User user : users) {
            if (user.getId() == id) {
                deletedUser = user;
                break;
            }
        }
        if (deletedUser != null) {
            users.remove(deletedUser);
            String response = "Se ha eliminado el usuario con éxito y sus datos son:\n" +
                    "Id:" + deletedUser.getId() + ", " +
                    "Nombres: " + deletedUser.getNames() + ", " +
                    "Email: " + deletedUser.getEmail() + ", " +
                    "Teléfono: " + deletedUser.getPhone();
            System.out.println(response);
        } else {
            System.out.println("No se encontró el usuario con Id:" + id);
        }
    }

    @Override
    public void findAll() {
        // Implementación vacía
    }

    @Override
    public void findById(int id) {
        for (User usuario : users) {
            if (usuario.getId() == id) {
                System.out.println("Se ha encontrado el usuario con Id:" + id);
                System.out.println(usuario.toString());
            }
        }
    }

    static void printUsers(List<User> users) {
        System.out.println("Lista de usuarios:");
        for (User user : users) {
            System.out.println("\n{\n\t\"id\":" + user.getId() + "," + "\n\t\"nombres\":" + user.getNames() + "\"," +
                    "\n\t\"email\":" + "\"" + user.getEmail() + "\",\n\t\"phone\":" + "\"" + user.getPhone() + "\"\n\t\t\t\t\t\t},");
        }
    }
}