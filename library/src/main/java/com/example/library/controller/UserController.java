package com.example.library.controller;

import com.example.library.dtos.RecordUserDto;
import com.example.library.models.UserModel;
import com.example.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid RecordUserDto recordUserDto){
        var userModel =  new UserModel();
        BeanUtils.copyProperties(recordUserDto,userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }

    @GetMapping
    public ResponseEntity<List <UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserId(@PathVariable(value = "id") UUID id){
        Optional<UserModel> user = userRepository.findById(id);
        return user.<ResponseEntity <Object>>map(userModel -> ResponseEntity.status(HttpStatus.OK).body(userModel)).orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Founded"));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable (value = "id")UUID id, @RequestBody @Valid RecordUserDto recordUserDto){

        return userRepository.findById(id).map(user -> {
            BeanUtils.copyProperties(recordUserDto,user);
            return  ResponseEntity.status(HttpStatus.OK).body((Object) userRepository.save(user));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Founded"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id")UUID id){
        return userRepository.findById(id).map(user ->{
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body((Object) "User successfully deleted.");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User  not founded"));
    }


}





