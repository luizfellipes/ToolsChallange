package com.example.toolschallanger.repositories;


import com.example.toolschallanger.models.entities.TransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;


@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, UUID> {
}
