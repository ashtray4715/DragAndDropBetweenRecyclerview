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
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Find my phone"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Flashlight"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("NFC"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Power"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Close"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Battery"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Wifi"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Brightness"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Date time"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Settings"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Sound"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Movie"));
        return items;
    }

    public ArrayList<QSItem> getUpdatedAvailableList() {
        ArrayList<QSItem> items = new ArrayList<>();
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Flight mode"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Bluetooth"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Scan QR code"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Location"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Do not disturb"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Smart view"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Secure folder"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Sync"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Dark mode"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Music"));
        items.add(new QSItem().setType(QSConstant.QSItem_TYPE_ACTUAL).setName("Focus mode"));
        return items;
    }



}
