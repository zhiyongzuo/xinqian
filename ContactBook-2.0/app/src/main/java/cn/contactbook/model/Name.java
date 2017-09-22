package cn.contactbook.model;

import android.graphics.drawable.Drawable;

/**
 * Created by zuo81 on 2017/9/11.
 */
public class Name {
        private String name;
        private Drawable drawable;

        public Name(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setImageView(Drawable drawable) {
            this.drawable = drawable;
        }
        public Drawable getImageView() {
            return drawable;
        }

}