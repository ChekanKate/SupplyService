package com.chekan.SupplyService.handlers;

import cds.gen.serviceorder.ServiceOrder_;
import cds.gen.supplierservice.Orders_;
import cds.gen.supplierservice.SupplierService_;
import com.sap.cds.Result;

import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@ServiceName(SupplierService_.CDS_NAME)
public class OrderServiceHandler implements EventHandler {

    @Autowired
    @Qualifier(ServiceOrder_.CDS_NAME)
    private CqnService service;

    @On(entity = Orders_.CDS_NAME)
    public Result readOrders(CdsReadEventContext context){
        return service.run(context.getCqn());
    }

}