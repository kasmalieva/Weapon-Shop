package com.example.tumar.Repo;

import com.example.tumar.Model.Weapon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeaponRepository extends CrudRepository<Weapon, Long> {
    public List<Weapon> findAllByType(String type);
}
