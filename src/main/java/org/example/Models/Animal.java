package org.example.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @Column(name = "id", updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", updatable = true)
    private String name;
    @Column(name = "type", updatable = true)
    private String type;
    @Column(name = "full_owner_name", updatable = true)
    private String fullOwnerName;
    @Column(name = "age", updatable = true)
    private int age;
}
