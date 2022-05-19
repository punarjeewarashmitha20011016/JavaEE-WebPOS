package dto;

public class CashierDTO {
    private String id;
    private String name;
    private String nic;
    private int contactNo;
    private String username;
    private String password;
    private String address;

    public CashierDTO() {
    }

    public CashierDTO(String id, String name, String nic, int contactNo, String username, String password, String address) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.contactNo = contactNo;
        this.username = username;
        this.password = password;
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CashierDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", contactNo=" + contactNo +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
