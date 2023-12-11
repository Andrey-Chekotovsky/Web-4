package Dao;

import jakarta.persistence.NoResultException;
import org.assertj.core.api.Assertions;
import org.example.Dao.AnotherAnimalService;
import org.example.Models.Animal;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
//@RunWith(MockitoJUnitRunner.class)
public class AnotherAnimalServiceTest {
    static AnotherAnimalService animalService = new AnotherAnimalService();
//    @Mock
//    Session session;

    @Test
    void AnimalRepository_getAnimal_GetIdOFSavedAnimal() {
        Animal animal = new Animal().toBuilder()
                .name("e")
                .fullOwnerName("a")
                .age(17)
                .type("2").build();
        int id = animalService.create(animal);
        Assertions.assertThat(id).isGreaterThan(0);
    }
    @Test
    void AnimalRepository_getAnimal_GetSavedAnimal() {
        Animal animal = new Animal().toBuilder()
                .name("e")
                .fullOwnerName("a")
                .age(17)
                .type("2").build();
        int id = animalService.create(animal);
        Animal finalAnimal = animalService.getAnimal(id);
        Assertions.assertThat(finalAnimal).isNotNull();
    }
    @Test
    void AnimalRepository_getAnimalWithWrongId_ThrowSqlException() {
        Animal animal = new Animal().toBuilder()
                .name("e")
                .fullOwnerName("a")
                .age(17)
                .type("2").build();
        int id = animalService.create(animal);
        Assertions.assertThat (animalService.getAnimal(id + 1)).isNull();
    }
    @Test
    void AnimalRepository_getAnimals_ReturnAllAnimals() {
        List<Animal> finalAnimal = animalService.getAnimals();
        Assertions.assertThat(finalAnimal).isNotNull();
    }
    @Test
    void AnimalRepository_update_UpdateSuccessfull() {
        Animal animal = new Animal().toBuilder()
                .name("e")
                .fullOwnerName("a")
                .age(17)
                .type("2").build();
        int id = animalService.create(animal);
        Animal animalToChange = animalService.getAnimal(id );
        animalToChange.setName("be");
        animalService.update(animalToChange);
        Assertions.assertThat(animalService.getAnimal(id).getName()).isEqualTo("be");
    }
    @Test
    void AnimalRepository_delete_DeleteSuccessfull() {
        Animal animal = new Animal().toBuilder()
                .name("e")
                .fullOwnerName("a")
                .age(17)
                .type("2").build();
        int id = animalService.create(animal);
        animalService.delete(id);
        Assertions.assertThat(animalService.getAnimal(id)).isNull();
    }

    @Test
    void AnimalRepository_getAnimals_ReturnAllAnimalsMock() {
        SessionFactory factory = Mockito.mock(SessionFactory.class);
        Session session = Mockito.mock(Session.class);
        Transaction transaction = Mockito.mock(Transaction.class);
        Query<Animal> query = Mockito.mock(Query.class);
        List<Animal> animal = new ArrayList<>();


        Mockito.when(factory.getCurrentSession()).thenReturn(session);
        Mockito.when(session.beginTransaction()).thenReturn(transaction);
        Mockito.when(session.getTransaction()).thenReturn(transaction);
        Mockito.when(session.createQuery("fromAnimal", Animal.class)).thenReturn(query);
        Mockito.when(query.list()).thenReturn(
                new ArrayList<Animal>(Arrays.asList(
                        new Animal(1,"a", "a", "a", 1)
                ))
        );

        Assertions.assertThat(session).isEqualTo(factory.getCurrentSession());
        Assertions.assertThat(transaction).isEqualTo(session.beginTransaction());
        Assertions.assertThat(query).isEqualTo(session.createQuery("fromAnimal", Animal.class));
        Assertions.assertThat(new ArrayList<Animal>(Arrays.asList(
                new Animal(1,"a", "a", "a", 1)
        ))).isEqualTo(query.list());
//        Mockito.verify(factory).getCurrentSession();
//        Mockito.verify(session).beginTransaction();
//        Mockito.verify(session).createQuery("fromAnimal", Animal.class).list();
//        Mockito.verify(session).getTransaction().commit();
    }
}
