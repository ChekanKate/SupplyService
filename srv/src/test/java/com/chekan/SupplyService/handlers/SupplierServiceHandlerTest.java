package com.chekan.SupplyService.handlers;

import cds.gen.serviceorder.Orders;
import cds.gen.supplierservice.Suppliers;
import com.sap.cds.Result;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.cds.CqnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceHandlerTest {

    @InjectMocks
    private SupplierServiceHandler handler;

    @Mock
    private CqnService remoteService;

    @Mock
    private Result result;

    @BeforeEach
    public void beforeAll() {
        when(remoteService.run(any(CqnSelect.class))).thenReturn(result);
    }

    @Test
    @DisplayName("Test how many times the method run is called")
    public void showSupplierWithOrderTest1(){
        //Given
        List<Suppliers> suppliersList = new ArrayList<>();
        suppliersList.add(createSupplier("7a7c2580-6482-4944-96f6-f1e3fc0d5aad"));

        //When
        handler.showSupplierWithOrder(suppliersList);

        //Then
        verify(remoteService).run(any(CqnSelect.class));
    }

    @Test
    @DisplayName("Test amount of Orders in Supplier, Test identity of IDs, Test Orders null")
    public void showSupplierWithOrderTest3(){
        //Given
        List<Suppliers> suppliersList = new ArrayList<>();
        suppliersList.add(createSupplier("7a7c2580-6482-4944-96f6-f1e3fc0d5aad"));
        suppliersList.add(createSupplier("ba25f401-2756-48d4-8824-d78124590ea1"));
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(createOrder("8f0bb2ad-425c-4e71-b642-76f2e44b9245", "7a7c2580-6482-4944-96f6-f1e3fc0d5aad"));
        ordersList.add(createOrder("6e52e92d-d0ee-436e-8fe6-0553d8721561", "7a7c2580-6482-4944-96f6-f1e3fc0d5aad"));

        //When
        when(result.listOf(Orders.class)).thenReturn(ordersList);
        handler.showSupplierWithOrder(suppliersList);

        //Then
        assertEquals(2, suppliersList.get(0).getOrders().size());
        suppliersList.get(0).getOrders().forEach(order -> assertEquals(suppliersList.get(0).getId(), order.getSupplierId()));
        assertNull(suppliersList.get(1).getOrders());
    }

    private Suppliers createSupplier(String id){
        Suppliers supplier = Suppliers.create();
        supplier.setId(id);
        return supplier;
    }

    private Orders createOrder(String id, String supplierId){
        Orders order = Orders.create();
        order.setId(id);
        order.setSupplierId(supplierId);
        return order;
    }

}