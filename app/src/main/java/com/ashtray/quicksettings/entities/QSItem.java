package com.ashtray.quicksettings.entities;

import androidx.annotation.NonNull;

public class QSItem {
    public int type = QSConstant.QSItem_TYPE_ACTUAL;
    public String name = "Unknown";
    public String imageUrl = "http://via.placeholder.com/300.png";

    public QSItem getNewCopy() {
        QSItem newCopy = new QSItem();
        newCopy.type = this.type;
        newCopy.name = this.name;
        newCopy.imageUrl = this.imageUrl;
        return newCopy;
    }

    public QSItem setType(int type) {
        this.type = type;
        return this;
    }

    public QSItem setName(String name) {
        this.name = name;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "QSItem" +
                "[type=" + type +
                "], [name=" + name +
                "], [imageUrl=" + imageUrl +
                ']';
    }
}
