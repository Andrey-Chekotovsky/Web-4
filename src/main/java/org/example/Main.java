package org.example;

import org.example.Dao.AnimalDao;
import org.example.Exceptions.NotFoundException;
import org.example.Exceptions.QueueOverflowException;
import org.example.Models.Animal;

import java.util.List;
import java.util.Scanner;

public class Main {
    static String MENU = """
            0. Выйти
            1. Вывести
            2. Добавить
            3. Удалить
            """;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws QueueOverflowException, NotFoundException, InterruptedException {
        AnimalDao dao = new AnimalDao();
        System.out.println("Hello world!");
        int switcher = -1;
        while(switcher != 0){
            System.out.println(MENU);
            switcher = scanner.nextInt();
            scanner.nextLine();
            switch (switcher){
                case 0 -> { System.out.println("Buy"); return; }
                case 1 -> {
                    List<Animal> list = dao.getAnimals();
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + " " + list.get(i));
                    }
                }
                case 2 -> dao.create(initAnimal());
                case 3 -> {
                    System.out.println(dao.getAnimals());
                    System.out.println("Введите номер");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    dao.delete(id);
                }
                default -> System.out.println("Fuck you");
            }
        }
    }
    private static Animal initAnimal(){
        System.out.println("Введите имя животного");
        String name = scanner.nextLine();
        System.out.println("Введите тип животного");
        String type = scanner.nextLine();
        System.out.println("Введите полное имя владельца");
        String fullOwnerName = scanner.nextLine();
        System.out.println("Введите возраст");
        int age = scanner.nextInt();
        scanner.nextLine();
        return new Animal().toBuilder().name(name).type(type)
                .fullOwnerName(fullOwnerName)
                .age(age).build();
    }
}