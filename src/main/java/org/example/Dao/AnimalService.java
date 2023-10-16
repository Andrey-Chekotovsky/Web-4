package org.example.Dao;

import org.example.Models.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AnimalService {
    private final AnimalRepository animalRepository;
    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Optional<Animal> getAnimal(int id)
    {
        return animalRepository.findById(id);
    }
    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }

    @Transactional
    public int create(Animal animal)
    {
        return animalRepository.save(animal).getId();
    }
    @Transactional
    public void update(int id, Animal animal)
    {

        animal.setId(id);
        animalRepository.save(animal);
    }
    @Transactional
    public void delete(int id)
    {
        animalRepository.deleteById(id);
    }
}
