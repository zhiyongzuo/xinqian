package cn.contactbook.model;

/**
 * Created by tomsdeath on 2017/8/15.
 */

public class PhoneInfo {
    private String name;
    private String number;
    public PhoneInfo(String name,String number){
        setName(name);
        setNumber(number);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() { return number; }

    public String getName() {
        return name;
    }
}
