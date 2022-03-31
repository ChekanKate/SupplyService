using { sap.capire.suppliers as db } from '../db/schema';
using { ServiceOrder.Orders as Orders } from './external/ServiceOrder.csn';
using { ServiceOrder } from './external/ServiceOrder';

service SupplierService {
    entity Suppliers as projection on db.Suppliers;
    entity Orders as projection on ServiceOrder.Orders;
}