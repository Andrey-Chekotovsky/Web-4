package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    private int id;
    private String name;
    private String type;
    private String fullOwnerName;
    private int age;
}
