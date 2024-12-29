package com.example.warehouseproject.UserServiceImplTests;

import com.example.warehouseproject.HelperClass;
import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.models.Role;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.dtos.Register;
import com.example.warehouseproject.models.dtos.UserOutput;
import com.example.warehouseproject.repositories.UserRepository;
import com.example.warehouseproject.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository mockRepository;

    @Mock
    PasswordEncoder mockEncoder;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void get_Should_Return_AllUsers_WhenOptionsAreFulfilled(){
        User testUser = HelperClass.createMockUserOwner();

        UserOutput testUserOutput = new UserOutput();
        testUserOutput.setEmail("ppp.jjj@example.com");

        Mockito.when(conversionService.convert(testUser, UserOutput.class)).thenReturn(testUserOutput);
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(testUser));

        List<UserOutput> result = userService.findAll();
        assertEquals(1, result.size());
        assertEquals("ppp.jjj@example.com", result.get(0).getEmail());
    }


    @Test
    public void get_Should_Return_CurrentUser_WhenOptionsAreFulfilled(){
        User testUser = HelperClass.createMockUserUser();

        UserOutput testUserOutput = new UserOutput();
        testUserOutput.setEmail("ppp.jjj@example.com");

        Mockito.when(conversionService.convert(testUser, UserOutput.class)).thenReturn(testUserOutput);
        Mockito.when(mockRepository.findUserByUserId(1)).thenReturn(Optional.of(testUser));

        UserOutput result = userService.findUserById(1);
        assertEquals("ppp.jjj@example.com", result.getEmail());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(99));
    }

    @Test
    public void get_Should_Return_CurrentUserEntity_WhenOptionsAreFulfilled(){
        User testUser = HelperClass.createMockUserUser();


        Mockito.when(mockRepository.findUserByUserId(1)).thenReturn(Optional.of(testUser));

        User result = userService.findUserEntityById(1);
        assertEquals("ppp.jjj@example.com", result.getEmail());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserEntityById(99));
    }

    @Test
    public void get_Should_Return_CurrentUser_WhenFirstNameIsFulfilled(){
        User testUser = HelperClass.createMockUserOwner();

        UserOutput testUserOutput = new UserOutput();
        testUserOutput.setFirstName("Gogo");

        Mockito.when(conversionService.convert(testUser, UserOutput.class)).thenReturn(testUserOutput);
        Mockito.when(mockRepository.findUserByFirstName("Gogo")).thenReturn(Optional.of(testUser));

        UserOutput result = userService.findUserByFirstName("Gogo");
        assertEquals("Gogo", result.getFirstName());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByFirstName("Nasko"));
    }

    @Test
    public void get_Should_Return_CurrentUser_WhenLastNameIsFulfilled(){
        User testUser = HelperClass.createMockUserOwner();

        UserOutput testUserOutput = new UserOutput();
        testUserOutput.setLastName("Tomov");

        Mockito.when(conversionService.convert(testUser, UserOutput.class)).thenReturn(testUserOutput);
        Mockito.when(mockRepository.findUserByLastName("Tomov")).thenReturn(Optional.of(testUser));

        UserOutput result = userService.findUserByLastName("Tomov");
        assertEquals("Tomov", result.getLastName());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByLastName("Atanasov"));
    }

    @Test
    public void get_Should_Return_CurrentUser_WhenEmailIsFulfilled(){
        User testUser = HelperClass.createMockUserUser();

        UserOutput testUserOutput = new UserOutput();
        testUserOutput.setEmail("ppp.jjj@example.com");

        Mockito.when(conversionService.convert(testUser, UserOutput.class)).thenReturn(testUserOutput);
        Mockito.when(mockRepository.findUserByEmail("ppp.jjj@example.com")).thenReturn(Optional.of(testUser));

        UserOutput result = userService.findUserByEmail("ppp.jjj@example.com");
        assertEquals("ppp.jjj@example.com", result.getEmail());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByEmail("jjj.jjj@example.com"));
    }

    @Test
    public void get_Should_Return_CurrentUserEntity_WhenEmailIsFulfilled(){
        User testUser = HelperClass.createMockUserUser();

        Mockito.when(mockRepository.findUserByEmail("ppp.jjj@example.com")).thenReturn(Optional.of(testUser));

        User result = userService.findUserEntityByEmail("ppp.jjj@example.com");
        assertEquals("ppp.jjj@example.com", result.getEmail());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserEntityByEmail("jjj.jjj@example.com"));
    }

    @Test
    public void get_Should_Return_boolean_WhenEmailIsFulfilled(){
        User testUser = HelperClass.createMockUserUser();

        Mockito.when(mockRepository.existsByEmail("ppp.jjj@example.com")).thenReturn(true);

        boolean result = userService.existsByEmail("ppp.jjj@example.com");
        assertTrue(result);
    }

    @Test
    public void create_Should_ThrowException_When_UserWithSameEmailExist() {
        User testUser = HelperClass.createMockUserOwner();

        Mockito.when(mockRepository.findUSerByEmail(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(mockEncoder.encode(Mockito.anyString())).thenReturn("hashedPassword");

        Register register = new Register();
        register.setFirstName(testUser.getFirstName());
        register.setLastName(testUser.getLastName());
        register.setEmail(testUser.getEmail());
        register.setPassword(testUser.getPassword());
        register.setRole(testUser.getRole());

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.createUser(register));

    }

    @Test
    public void create_Should_CreateUser_When_UserFieldsAreCorrect() {

        Mockito.when(mockEncoder.encode(Mockito.anyString())).thenReturn("hashedPassword");
        Mockito.when(mockRepository.existsByEmail("p.petrov@example.com")).thenReturn(true);

        Register register = new Register();
        register.setFirstName("Petar");
        register.setLastName("Petrov");
        register.setEmail("p.petrov@example.com");
        register.setPassword("13456789");
        register.setRole(Role.OPERATOR);

        userService.createUser(register);

        boolean bool = userService.existsByEmail("p.petrov@example.com");

        assertTrue(bool);


    }
}
