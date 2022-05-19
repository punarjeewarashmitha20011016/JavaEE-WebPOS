package entity;

public class Customer {
    private String id;
    private String name;
    private int contactNo;
    private String nic;
    private String address;

    public Customer() {
    }

    public Customer(String id, String name, int contactNo, String nic, String address) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.nic = nic;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo=" + contactNo +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
