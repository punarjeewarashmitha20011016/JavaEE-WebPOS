package dao;

import dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {

    }

    public static DaoFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public SuperDao getDao(DaoTypes types) {
        switch (types) {
            case Admin:
                return new AdminDaoImpl();
            case Cashier:
                return new CashierDaoImpl();
            case Item:
                return new ItemDaoImpl();
            case Customer:
                return new CustomerDaoImpl();
            case OrderDetails:
                return new OrderDetailsDaoImpl();
            case Order:
                return new OrderDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoTypes {
        Admin, Cashier, Item, Customer, Order, OrderDetails
    }
}
