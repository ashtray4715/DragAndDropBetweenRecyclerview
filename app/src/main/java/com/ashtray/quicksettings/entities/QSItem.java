package com.ashtray.quicksettings.entities;

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
}
