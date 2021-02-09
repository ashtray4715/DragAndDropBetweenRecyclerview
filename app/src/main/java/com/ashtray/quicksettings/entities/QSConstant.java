package com.ashtray.quicksettings.entities;

public class QSConstant {
    public static final int QSItem_TYPE_HEADER = 0;
    public static final int QSItem_TYPE_ACTUAL = 1;
    public static final int QSItem_TYPE_DUMMY = 2;

    public static final String QSItem_NAME_DUMMY = "";
    public static final String QSItem_IMAGE_URL_DUMMY = "http://via.placeholder.com/300.png";

    public static final String QS_LABEL_CURRENT_ITEM = "current_item";
    public static final String QS_LABEL_AVAILABLE_ITEM = "available_item";

    public static QSItem newDummyItem() {
        QSItem ret = new QSItem();
        ret.type = QSItem_TYPE_DUMMY;
        ret.name = QSItem_NAME_DUMMY;
        ret.imageUrl = QSItem_IMAGE_URL_DUMMY;
        return ret;
    }
}
