package com.manager.icecream.repository;

import com.manager.icecream.entity.IceCream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IceCreamRepository extends JpaRepository<IceCream, Long> {

    Optional<IceCream> findByName(String name);
}
