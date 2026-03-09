package com.example.library.repository;


import com.example.library.models.BookModel;
import com.example.library.models.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanModel, UUID> {

    boolean existsByBookAndReturnDateAfter(BookModel book, LocalDate date);

}
