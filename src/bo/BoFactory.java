package bo;

import bo.custom.impl.AdminCashierLoginBOImpl;
import bo.custom.impl.AdminFormSignUpBoImpl;
import bo.custom.impl.CashierFormSignUpBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    public BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public SuperBo getBoTypes(BoTypes types) {
        switch (types) {
            case AdminFormSignUp:
                return new AdminFormSignUpBoImpl();
            case AdminCashierLogin:
                return new AdminCashierLoginBOImpl();
            case cashierFormSignUp:
                return new CashierFormSignUpBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        AdminFormSignUp, AdminCashierLogin, cashierFormSignUp
    }
}
