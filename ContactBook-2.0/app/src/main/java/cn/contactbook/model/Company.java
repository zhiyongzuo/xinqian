package cn.contactbook.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by zuo81 on 2017/9/5.
 */
public class Company {
    private String company;
    private Drawable drawable;

    public Company(String company) {
        this.company = company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setImageView(Drawable drawable) {
        this.drawable = drawable;
    }
    public Drawable getImageView() {
        return drawable;
    }

}