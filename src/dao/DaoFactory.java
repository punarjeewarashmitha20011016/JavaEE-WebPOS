package dao;

import dao.custom.impl.AdminDaoImpl;

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
            default:
                return null;
        }
    }

    public enum DaoTypes {
        Admin
    }
}
