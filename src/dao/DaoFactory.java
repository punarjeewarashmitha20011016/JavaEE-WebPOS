package dao;

import dao.custom.impl.AdminDaoImpl;
import dao.custom.impl.CashierDaoImpl;

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
            default:
                return null;
        }
    }

    public enum DaoTypes {
        Admin, Cashier
    }
}
