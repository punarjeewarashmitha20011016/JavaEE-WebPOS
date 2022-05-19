package bo;

import bo.custom.impl.*;

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
            case ItemForm:
                return new ItemFormBOImpl();
            case CustomerForm:
                return new CustomerFormBOImpl();
            case PlaceOrder:
                return new PlaceOrderBoImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        AdminFormSignUp, AdminCashierLogin, cashierFormSignUp, ItemForm, CustomerForm, PlaceOrder
    }
}
