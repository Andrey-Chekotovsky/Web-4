package org.example.Dao;

import lombok.Getter;


public class PreparedStatements {
    @Getter
    static private String CREATE_STATEMENT = "INSERT INTO animals(name, type, full_owner_name, age)" +
            " VALUES (?, ?, ?, ?) RETURNING id;";
    @Getter
    static private String UPDATE_STATEMENT = "UPDATE animals SET name = ?, type = ?, full_owner_name = ?, age = ?" +
            " WHERE id = ?;";
    @Getter
    static private String READ_BY_ID_STATEMENT = "SELECT * FROM animals WHERE id = ?;" ;
    @Getter
    static private String READ_ALL_STATEMENT = "SELECT * FROM animals;";
    @Getter
    static private String DELETE_BY_ID_STATEMENT = "DELETE FROM animals WHERE id = ?;";
}
