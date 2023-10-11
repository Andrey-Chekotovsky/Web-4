package org.example.Dao;

import org.example.Exceptions.NotFoundException;
import org.example.Exceptions.QueueOverflowException;
import org.example.Models.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AnimalDao {
    private static int poolSize = 50;
    private static BlockingQueue<Connection> connectionPool = new ArrayBlockingQueue<>(poolSize);
    private static BlockingQueue<Connection> takenConnections = new ArrayBlockingQueue<>(poolSize);

    static {
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(ConnectionConfigure.getConnection());
        }
    }
    private Connection connection;

    public AnimalDao() throws InterruptedException {
        this.connection = connectionPool.take();
        takenConnections.add(connection);
    }

    public int create(Animal animal){
        try (PreparedStatement statement = connection.prepareStatement(PreparedStatements.getCREATE_STATEMENT())){
            statement.setString(1, animal.getName());
            statement.setString(2, animal.getType());
            statement.setString(3, animal.getFullOwnerName());
            statement.setInt(4, animal.getAge());
            ResultSet res = statement.executeQuery();
            res.next();
            return res.getInt("id");
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Animal animal){
        try (PreparedStatement statement = connection.prepareStatement(PreparedStatements.getUPDATE_STATEMENT())){
            statement.setString(1, animal.getName());
            statement.setString(2, animal.getType());
            statement.setString(3, animal.getFullOwnerName());
            statement.setInt(4, animal.getAge());
            statement.setInt(5, animal.getId());
            statement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(int id){
        try (PreparedStatement statement = connection.prepareStatement(PreparedStatements.getDELETE_BY_ID_STATEMENT())){
            statement.setInt(1, id);
            statement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Animal getAnimal(int id) throws NotFoundException {
        try (PreparedStatement statement = connection.prepareStatement(PreparedStatements.getREAD_BY_ID_STATEMENT())){
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return animalFromRes(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new NotFoundException("Animal not found");
        }
    }
    public List<Animal> getAnimals() throws NotFoundException{
        try (PreparedStatement statement = connection.prepareStatement(PreparedStatements.getREAD_ALL_STATEMENT())){
            ResultSet rs = statement.executeQuery();
            return animalsFromRes(rs);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new NotFoundException("Animal not found");
        }
    }
    private  Animal animalFromRes(ResultSet rs) throws SQLException{
        Animal animal = new Animal();
        rs.next();
        animal.setName(rs.getString("name"));
        animal.setType(rs.getString("type"));
        animal.setId(rs.getInt("id"));
        animal.setFullOwnerName(rs.getString("full_owner_name"));
        animal.setAge(rs.getInt("age"));
        return animal;
    }
    private  List<Animal> animalsFromRes(ResultSet rs) throws SQLException{
        List<Animal> animals = new ArrayList<>();
        while (rs.next()) {
            Animal animal = new Animal();
            animal.setName(rs.getString("name"));
            animal.setType(rs.getString("type"));
            animal.setId(rs.getInt("id"));
            animal.setFullOwnerName(rs.getString("full_owner_name"));
            animal.setAge(rs.getInt("age"));
            animals.add(animal);
        }
        return animals;
    }
    public void close(){
        connectionPool.add(connection);
        takenConnections.remove(this.connection);
        connection = null;
    }
}
