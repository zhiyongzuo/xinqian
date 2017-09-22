package cn.contactbook.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;

public class ChipsEntity extends DataSupport {
    @DrawableRes
    private Integer drawableResId;

    @Nullable
    private String description;

    @NonNull
    private String name;

    private Album album;

    private String resource;

    private String father;
    private Integer repeatStr;

    public void setRepeatStr(Integer repeatStr) {
        this.repeatStr = repeatStr;
    }

    public Integer getRepeatStr() {
        return repeatStr;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getFather() {
        return father;
    }


    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDrawableResId(Integer drawableResId) {
        this.drawableResId = drawableResId;
    }

    public Integer getDrawableResId() {
        return drawableResId;
    }
}
