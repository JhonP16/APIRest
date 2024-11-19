package Service;

import model.User;

import java.util.List;

public interface JPAUsers {
    /*Interfaz que contendrá los métodos abtractos(CRUD)
    *que se implementarán en la clase JPAUsersImpl
     */

    public void create(User user);
    public List<User>  readAll();
    void updateById(User updatedUser, int id);
    public void deleteById(int id);
    public void findAll();
    public void findById(int id);

}

