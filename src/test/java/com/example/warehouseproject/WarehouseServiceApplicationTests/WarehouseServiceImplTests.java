package com.example.warehouseproject.WarehouseServiceApplicationTests;

import com.example.warehouseproject.HelperClass;
import com.example.warehouseproject.exceptions.DuplicateEntityException;
import com.example.warehouseproject.exceptions.EntityNotFoundException;
import com.example.warehouseproject.exceptions.InvalidDataException;
import com.example.warehouseproject.exceptions.UnauthorizedOperationException;
import com.example.warehouseproject.models.User;
import com.example.warehouseproject.models.Warehouse;
import com.example.warehouseproject.models.dtos.WarehouseInput;
import com.example.warehouseproject.models.dtos.WarehouseOutput;
import com.example.warehouseproject.models.dtos.WarehouseOutputId;
import com.example.warehouseproject.repositories.WarehousePartsRepository;
import com.example.warehouseproject.repositories.WarehouseRepository;
import com.example.warehouseproject.services.UserServiceImpl;
import com.example.warehouseproject.services.WarehouseServiceImpl;
import com.example.warehouseproject.services.contracts.PartService;
import com.example.warehouseproject.services.contracts.WarehousePartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplTests {


    @Mock
    WarehouseRepository mockRepository;

    @Mock
    PartService mockPartService;

    @Mock
    private WarehousePartsRepository warehousePartsRepository;

    @Mock
    WarehousePartService warehousePartService;

    @Mock
    UserServiceImpl userService;

    @Mock
    ConversionService conversionService;

    @InjectMocks
    WarehouseServiceImpl warehouseService;

    @Test
    public void getWarehousesWithFilters_Should_ReturnPageOfWarehouses_WhenFiltersAreApplied() {
        String nameFilter = "Warehouse";
        int page = 0;
        int size = 5;
        String sortBy = "name";
        String sortDirection = "ASC";

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("Warehouse 1");
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("Warehouse 2");

        List<Warehouse> warehouseList = List.of(warehouse1, warehouse2);
        Page<Warehouse> mockPage = new PageImpl<>(warehouseList);

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Mockito.when(mockRepository.findWarehousesByMultipleFields("%" + nameFilter + "%", pageable))
                .thenReturn(mockPage);

        Page<Warehouse> result = warehouseService.getWarehousesWithFilters(nameFilter, page, size, sortBy, sortDirection);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Warehouse 1", result.getContent().get(0).getName());
        assertEquals("Warehouse 2", result.getContent().get(1).getName());

        Mockito.verify(mockRepository, Mockito.times(1))
                .findWarehousesByMultipleFields("%" + nameFilter + "%", pageable);
    }


    @Test
    public void getWarehousesWithFilters_Should_ReturnNull_WhenWrongFiltersAreApplied() {
        String nameFilter = "Mkm";
        int page = 0;
        int size = 5;
        String sortBy = "name";
        String sortDirection = "ASC";

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("Warehouse 1");
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("Warehouse 2");

        List<Warehouse> emptyWarehouseList = List.of();
        Page<Warehouse> emptyMockPage = new PageImpl<>(emptyWarehouseList);

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Mockito.when(mockRepository.findWarehousesByMultipleFields("%" + nameFilter + "%", pageable))
                .thenReturn(emptyMockPage);

        Page<Warehouse> result = warehouseService.getWarehousesWithFilters(nameFilter, page, size, sortBy, sortDirection);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getContent().size());

        Mockito.verify(mockRepository, Mockito.times(1))
                .findWarehousesByMultipleFields("%" + nameFilter + "%", pageable);
    }


    @Test
    public void get_Should_Return_AllWarehouses(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findAll()).thenReturn(List.of(mockWarehouse));

        assertEquals(1, warehouseService.findAllWarehouses().size());

    }


    @Test
    public void get_Should_Return_AllWarehouseEntities(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findAll()).thenReturn(List.of(mockWarehouse));

        assertEquals(1, warehouseService.findAllWarehousesEntities().size());

    }



    @Test
    public void get_Should_Return_Warehouse_WhenProperIdIsProvided(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findByWarehouseId(1)).thenReturn(Optional.of(mockWarehouse));

        assertEquals(warehouseService.findWarehouseById(1), conversionService.convert(mockWarehouse, WarehouseOutput.class));

    }

    @Test
    public void get_Should_Return_WarehouseEntity_WhenProperIdIsProvided(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findByWarehouseId(1)).thenReturn(Optional.of(mockWarehouse));

        assertEquals(warehouseService.findWarehouseEntityById(1), mockWarehouse);

    }

    @Test
    public void get_Should_Throw_EntityNotFoundException_WhenWrongIdIsProvided(){

        Assertions.assertThrows(EntityNotFoundException.class, () -> warehouseService.findWarehouseById(1));

    }

    @Test
    public void get_WarehouseEntity_Should_Throw_EntityNotFoundException_WhenWrongIdIsProvided(){

        Assertions.assertThrows(EntityNotFoundException.class, () -> warehouseService.findWarehouseEntityById(1));

    }

    @Test
    public void get_Should_Return_Warehouse_WhenProperNameIsProvided(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findByName("Warehouse 1")).thenReturn(Optional.of(mockWarehouse));

        assertEquals(warehouseService.findWarehouseByName("Warehouse 1"), conversionService.convert(mockWarehouse, WarehouseOutput.class));

    }

    @Test
    public void get_Warehouse_Should_Throw_EntityNotFoundException_WhenWrongNameIsProvided(){

        Assertions.assertThrows(EntityNotFoundException.class, () -> warehouseService.findWarehouseByName("Warehouse 1"));

    }

    @Test
    public void get_Should_Return_WarehouseEntity_WhenProperNameIsProvided(){

        Warehouse mockWarehouse = HelperClass.createMockWarehouse();

        Mockito.when(mockRepository.findByName("Warehouse 1")).thenReturn(Optional.of(mockWarehouse));

        assertEquals(warehouseService.findWarehouseEntityByName("Warehouse 1"), mockWarehouse);

    }

    @Test
    public void get_WarehouseEntity_Should_Throw_EntityNotFoundException_WhenWrongNameIsProvided(){

        Assertions.assertThrows(EntityNotFoundException.class, () -> warehouseService.findWarehouseEntityByName("Warehouse 1"));

    }


    @Test
    public void createWarehouse_Should_Return_WarehouseOutputId_When_Successful(){

        WarehouseInput warehouseInput = new WarehouseInput();
        warehouseInput.setName("Warehouse");

        Warehouse warehouse = Warehouse.builder()
                        .warehouseId(0)
                                .name("Warehouse")
                                        .build();

        Assertions.assertEquals(mockRepository.findWarehouseEntityByName("Warehouse"), null);

        WarehouseOutputId warehouseOutputId = warehouseService.createWarehouse(warehouseInput);

        Mockito.verify(mockRepository, Mockito.times(1)).save(warehouse);
    }

    @Test
    public void createWarehouse_Should_Throw_Exception_When_TheSameWarehouse_Exists(){

        WarehouseInput warehouseInput = new WarehouseInput();
        warehouseInput.setName("Warehouse");

        Warehouse warehouse = Warehouse.builder()
                        .name("Warehouse")
                .build();

        Mockito.when(mockRepository.findWarehouseEntityByName("Warehouse")).thenReturn(warehouse);

        Assertions.assertThrows(DuplicateEntityException.class, () -> warehouseService.createWarehouse(warehouseInput));

        Mockito.verify(mockRepository, Mockito.never()).save(Mockito.any(Warehouse.class));

    }

    @Test
    public void changeNameOdWarehouse_Should_Pass_If_WeDoIt_Correctly(){
        User user = HelperClass.createMockUserOwner();

        Warehouse warehouse = Warehouse.builder()
                .warehouseId(1)
                .name("Warehouse")
                .build();

        Mockito.when(userService.findUserEntityByEmail(user.getEmail())).thenReturn(user);

        Mockito.when(mockRepository.findWarehouseEntityByWarehouseId(warehouse.getWarehouseId())).thenReturn(warehouse);

        warehouseService.changeNameOfWarehouse(warehouse.getWarehouseId(), "Warehouse New", user.getEmail());

        Mockito.verify(mockRepository, Mockito.times(1)).save(Mockito.any(Warehouse.class));
    }



    @Test
    public void changeNameOdWarehouse_Should_Throw_If_UserIsNot_Owner(){
        User user = HelperClass.createMockUserUser();

        Warehouse warehouse = Warehouse.builder()
                .warehouseId(1)
                .name("Warehouse")
                .build();

        Mockito.when(userService.findUserEntityByEmail(user.getEmail())).thenReturn(user);

        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                warehouseService.changeNameOfWarehouse(warehouse.getWarehouseId(), "Warehouse New", user.getEmail()));
    }


    @Test
    public void changeNameOdWarehouse_Should_Throw_If_InputIsNotCorrect(){
        User user = HelperClass.createMockUserOwner();

        Warehouse warehouse = Warehouse.builder()
                .warehouseId(1)
                .name("Warehouse")
                .build();

        Mockito.when(userService.findUserEntityByEmail(user.getEmail())).thenReturn(user);

        Assertions.assertThrows(InvalidDataException.class, () ->
                warehouseService
                        .changeNameOfWarehouse(warehouse.getWarehouseId(),
                                "WarehouseeNewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                                user.getEmail()));
    }
}
