package com.ashtray.quicksettings.ui;

import androidx.lifecycle.ViewModel;

import com.ashtray.quicksettings.entities.QSConstant;
import com.ashtray.quicksettings.entities.QSItem;

import java.util.ArrayList;

public class QSViewModel extends ViewModel {

    public ArrayList<QSItem> getUpdatedCurrentList() {
        ArrayList<QSItem> items = new ArrayList<>();
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_HEADER).setName(""));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_HEADER).setName(""));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_HEADER).setName(""));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("a"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("b"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("c"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("d"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("e"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("f"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("g"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("h"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("i time"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("j"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("k"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("l"));
        return items;
    }

    public ArrayList<QSItem> getUpdatedAvailableList() {
        ArrayList<QSItem> items = new ArrayList<>();
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("1"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("2"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("3"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("4"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("5"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("6"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("7"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("8"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("9"));
        return items;
    }



}
