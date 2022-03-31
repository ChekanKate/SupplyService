namespace sap.capire.suppliers;

using { cuid } from '@sap/cds/common';
using { ServiceOrder.Orders as Orders } from 'C:\Users\user\IdeaProjects\SupplyService\srv\external\ServiceOrder.csn';
using { ServiceOrder } from 'C:\Users\user\IdeaProjects\SupplyService\srv\external\ServiceOrder';

entity Suppliers : cuid {
   name : String;
   score : Integer;
   address : Address;
   orders : Association to many Orders on orders.supplier_id = $self.ID;
};

type Address {
    country : String;
    city : String;
    street : String;
    numOfHouse : Integer;
}