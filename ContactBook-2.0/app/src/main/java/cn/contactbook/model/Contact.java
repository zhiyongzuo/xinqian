package cn.contactbook.model;

/**
 * Created by dell on 2016/10/10.
 */
public class Contact {
    private String name;
    private String phone;
    private String phone2;
    private String email;
    private String photo;
    private String company;
    private String sex;
    private int id;
    //
    private String army_friends;
    private String friends;
    private String classmates;
    private String family;
    private String fellowtownsman;

    public Contact() {
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact(int id, String name, String phone, String phone2, String email, String photo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.photo = photo;
    }

    public Contact(String name, String phone, String phone2, String email, String photo,  String sex,String company) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.photo = photo;
        this.sex = sex;
        this.company = company;

    }
    //
    public Contact(String name, String phone, String phone2, String email, String photo, String sex, String company,String army_friends,String friends,String classmates,String family,String fellowtownsman) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.photo = photo;
        this.sex = sex;
        this.company = company;
        this.army_friends = army_friends;
        this.friends = friends;
        this.classmates = classmates;
        this.family = family;
        this.fellowtownsman = fellowtownsman;

    }

    public Contact(String name, String phone, String phone2, String email, String photo) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.photo = photo;
    }

    public Contact(String name, String phone, String phone2, String email) {
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    //
    public String getArmyFriends() {
        return army_friends;
    }

    public void setArmyFriends(String army_friends) {
        this.army_friends = army_friends;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getClassmates() {
        return classmates;
    }

    public void setClassmates(String classmates) {
        this.classmates = classmates;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFellowtownsman() {
        return fellowtownsman;
    }

    public void setFellowtownsman(String fellowtownsman) {
        this.fellowtownsman = fellowtownsman;
    }
}
