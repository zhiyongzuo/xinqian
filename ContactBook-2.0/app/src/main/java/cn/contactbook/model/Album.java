package cn.contactbook.model;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by zuo81 on 2017/9/13.
 */

public class Album extends DataSupport {
    private String name;
  /*  private List<ChipsEntity> armyFriends;
    private List<ChipsEntity> friends;
    private List<ChipsEntity> classmates;
    private List<ChipsEntity> family;
    private List<ChipsEntity> fellowTownsMan;*/

    public void setName(String name) {
        this.name = name;
    }

   /* public void setArmyFriends(List<ChipsEntity> armyFriends) {
        this.armyFriends = armyFriends;
    }

    public void setClassMates(List<ChipsEntity> classmates) {
        this.classmates = classmates;
    }

    public void setFriends(List<ChipsEntity> friends) {
        this.friends = friends;
    }

    public void setFamily(List<ChipsEntity> family) {
        this.family = family;
    }

    public void setFellowTownsMan(List<ChipsEntity> fellowTownsMan) {
        this.fellowTownsMan = fellowTownsMan;
    }*/

    public String getName() {
        return name;
    }

 /*   public List<ChipsEntity> getArmyFriends() {
        return armyFriends;
    }

    public List<ChipsEntity> getClassMates() {
        return classmates;
    }

    public List<ChipsEntity> getFriends() {
        return friends;
    }

    public List<ChipsEntity> getFamily() {
        return family;
    }

    public List<ChipsEntity> getFellowTownsMan() {
        return fellowTownsMan;
    }*/
}
