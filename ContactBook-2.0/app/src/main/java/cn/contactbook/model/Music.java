package cn.contactbook.model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuo81 on 2017/9/14.
 */

public class Music extends DataSupport {
    private String armyFriends;
    private String friends;
    private String classmates;
    private String family;
    private String fellowTownsMan;
    private Album album;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private List<ChipsEntity> chipsEntityList = new ArrayList<>();

    public void setChipsEntityList(List<ChipsEntity> chipsEntityList) {
        this.chipsEntityList = chipsEntityList;
    }

    public List<ChipsEntity> getChipsEntityList() {
        return chipsEntityList;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public void setArmyFriends(String armyFriends) {
        this.armyFriends = armyFriends;
    }

    public void setClassMates(String classmates) {
        this.classmates = classmates;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setFellowTownsMan(String fellowTownsMan) {
        this.fellowTownsMan = fellowTownsMan;
    }

    public String getArmyFriends() {
        return armyFriends;
    }

    public String getClassMates() {
        return classmates;
    }

    public String getFriends() {
        return friends;
    }

    public String getFamily() {
        return family;
    }

    public String getFellowTownsMan() {
        return fellowTownsMan;
    }
}
