package model;

public class Contact implements Comparable<Contact> {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact(String name) {
        this(name, "", "");
    }

    @Override
    public int compareTo(Contact other) {
        return name.compareToIgnoreCase(other.name);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " | Tel: " + phone + " | Mail: " + email;
    }
}
