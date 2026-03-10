package com.example.library.controller;

import com.example.library.dtos.RecordLoanDto;
import com.example.library.dtos.RecordUserDto;
import com.example.library.models.BookModel;
import com.example.library.models.LoanModel;
import com.example.library.models.UserModel;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "*")
public class LoanController {


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Object> saveLoan(@RequestBody @Valid RecordLoanDto recordLoanDto){

        Optional<UserModel>  user = userRepository.findById(recordLoanDto.userId());
        Optional<BookModel> book = bookRepository.findById(recordLoanDto.bookId());



        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Founded");
        }

        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not Founded");
        }

        boolean isBookBusy = loanRepository.existsByBookAndReturnDateAfter(book.get(), LocalDate.now());

        if(isBookBusy){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This book is already on loan and has not yet been returned.");
        }


        var loanModel = new LoanModel();

        loanModel.setUser(user.get());
        loanModel.setBook(book.get());
        loanModel.setLoanDate(LocalDate.now());
        loanModel.setReturnDate(LocalDate.now().plusDays(recordLoanDto.daysToReturn()));

        return  ResponseEntity.status(HttpStatus.CREATED).body(loanRepository.save(loanModel));
    }

    @GetMapping
    public ResponseEntity<List<LoanModel>> getAllLoans(){
        return ResponseEntity.status(HttpStatus.OK).body(loanRepository.findAll());
    }

    @GetMapping("/{id}")

    public ResponseEntity<Object> getLoanById(@PathVariable(value = "id") UUID id ){

        Optional<LoanModel> loan = loanRepository.findById(id);

        return loan.<ResponseEntity<Object>>map(loanModel-> ResponseEntity.status(HttpStatus.OK).body(loanModel)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Not Founded"));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLoan(@PathVariable(value = "id")UUID id){
        Optional<LoanModel>  loan = loanRepository.findById(id);

        if(loan.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Not Founded");
        }
        loanRepository.delete(loan.get());

        return ResponseEntity.status(HttpStatus.OK).body("Loan successfully deleted/returned.");


    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> renewLoan(@PathVariable(value = "id") UUID id, @RequestBody @Valid RecordLoanDto recordLoanDto) {
        Optional<LoanModel> loan = loanRepository.findById(id);

        if (loan.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Not Founded");
        }

        var loanModel = loan.get();

        loanModel.setReturnDate(loanModel.getReturnDate().plusDays(recordLoanDto.daysToReturn()));

        return ResponseEntity.status(HttpStatus.OK).body(loanRepository.save(loanModel));
    }



}
