package com.example.warehouseproject.services;

import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.Register;
import com.example.warehouseproject.models.dtos.UserOutput;
import com.example.warehouseproject.models.dtos.UserOutputId;
import com.example.warehouseproject.repositories.UserRepository;
import com.example.warehouseproject.services.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserOutput> findAll() {
        List<User> listOfUsers = userRepository.findAll();
        List<UserOutput> listOfUserOutputs = new ArrayList<>();
        for (User user : listOfUsers){
            listOfUserOutputs.add(conversionService.convert(user, UserOutput.class));
        }
        return listOfUserOutputs;
    }

    @Override
    public UserOutput findUserById(int id){
        User user = userRepository.findUserByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("User", id));

        return conversionService.convert(user, UserOutput.class);
    }

    @Override
    public User findUserEntityById(int id) {
        User user = userRepository.findUserByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("User", id));
        return user;
    }

    @Override
    public UserOutput findUserByFirstName(String firstName){
        User user = userRepository.findUserByFirstName(firstName).orElseThrow(
                () -> new EntityNotFoundException("User",  "first name", firstName));

        return conversionService.convert(user, UserOutput.class);
    }

    @Override
    public UserOutput findUserByLastName(String lastName){
        User user = userRepository.findUserByLastName(lastName).orElseThrow(
                () -> new EntityNotFoundException("User",  "last name", lastName));

        return conversionService.convert(user, UserOutput.class);
    }

    @Override
    public UserOutput findUserByEmail(String email){
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User", "email", email));

        return conversionService.convert(user, UserOutput.class);
    }

    @Override
    public User findUserEntityByEmail(String email){

        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User", "email", email));
    }


    @Override
    public UserOutputId createUser(Register register) {

        String hashedPassword = passwordEncoder.encode(register.getPassword());

        User user = User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .password(hashedPassword)
                .role(register.getRole())
                .build();

        User existingUser = userRepository.findUSerByEmail(register.getEmail());

        if (existingUser != null) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }

        userRepository.save(user);

        return conversionService.convert(user, UserOutputId.class);
    }

}
