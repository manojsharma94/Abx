package com.kot.mylibrary123.Model;

import android.net.Uri;

import java.util.List;

public class Sub_problem {
    String name;
    boolean check=false;
    List<Uri>img;

    public List<Uri> getImg() {
        return img;
    }

    public void setImg(List<Uri> img) {
        this.img = img;
    }

    public Sub_problem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
