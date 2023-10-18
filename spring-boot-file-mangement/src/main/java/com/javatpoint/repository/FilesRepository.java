package com.javatpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatpoint.model.Files;
import com.javatpoint.model.Item;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    List<Files> findByItem(Item item);

}