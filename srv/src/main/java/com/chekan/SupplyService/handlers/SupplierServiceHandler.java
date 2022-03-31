package com.chekan.SupplyService.handlers;

import cds.gen.serviceorder.Orders;
import cds.gen.serviceorder.Orders_;
import cds.gen.serviceorder.ServiceOrder_;
import cds.gen.supplierservice.SupplierService_;
import cds.gen.supplierservice.Suppliers;
import cds.gen.supplierservice.Suppliers_;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ServiceName(SupplierService_.CDS_NAME)
public class SupplierServiceHandler implements EventHandler {

    @Autowired
    @Qualifier(ServiceOrder_.CDS_NAME)
    private CqnService remoteOrder;

    @After(event = CqnService.EVENT_READ, entity = Suppliers_.CDS_NAME)
    public void showSupplierWithOrder(List<Suppliers> suppliers){

        List<String> supplierIDs = new ArrayList<>();
        for(Suppliers supplier : suppliers){
            supplierIDs.add(supplier.getId());
        }

        CqnSelect selectOrders = Select.from(Orders_.class).where(a -> a.supplier_id().in(supplierIDs));
        List<Orders> orderList = remoteOrder.run(selectOrders).listOf(Orders.class);

        Map<String, List<Orders>> supplierWithOrder = orderList.stream().collect(Collectors.groupingBy(Orders::getSupplierId));
        suppliers.forEach(order -> order.setOrders(supplierWithOrder.get(order.getId())));

    }

}