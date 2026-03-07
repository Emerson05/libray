package com.example.library.controller;

import com.example.library.dtos.RecordBookDto;
import com.example.library.models.BookModel;
import com.example.library.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;


    @PostMapping("/books")

    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid RecordBookDto  RecordBookDto){
        var bookModel = new BookModel();
        BeanUtils.copyProperties(RecordBookDto, bookModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookModel));

    }

    @GetMapping("/books")
    public ResponseEntity<List <BookModel>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value = "id") UUID id){
        Optional<BookModel> book = bookRepository.findById(id);

        return book.<ResponseEntity<Object>>map(bookModel -> ResponseEntity.status(HttpStatus.OK).body(bookModel)).
                orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not Found"));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id")UUID id, @RequestBody @Valid RecordBookDto RecordBookDto){
            return bookRepository.findById(id).map(book -> {
                BeanUtils.copyProperties(RecordBookDto, book);
                return ResponseEntity.status(HttpStatus.OK).body((Object) bookRepository.save(book));
            }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not Founded"));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Object> deleVook(@PathVariable(value = "id")UUID id){
        return bookRepository.findById(id).map(book -> {
            bookRepository.delete(book);
            return ResponseEntity.status(HttpStatus.OK).body((Object) "Book deleted successfully");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not Found"));
    }



}
