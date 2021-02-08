package com.ashtray.quicksettings.ui;

import androidx.lifecycle.ViewModel;

import com.ashtray.quicksettings.entities.QSConstant;
import com.ashtray.quicksettings.entities.QSItem;

import java.util.ArrayList;

public class QSViewModel extends ViewModel {

    public ArrayList<QSItem> getUpdatedCurrentList() {
        ArrayList<QSItem> items = new ArrayList<>();
        for(int i=0;i<10;i++) {
            QSItem item = new QSItem();
            item.name = String.valueOf(i-3);
            item.type = (i<3) ? QSConstant.QSItem_TYPE_HEADER : QSConstant.QSItem_TYPE_ACTUAL;
            items.add(item);
        }
        return items;
    }

    public ArrayList<QSItem> getUpdatedAvailableList() {
        ArrayList<QSItem> items = new ArrayList<>();
        for(int i=0;i<20;i++) {
            QSItem item = new QSItem();
            item.name = String.valueOf(i);
            item.type = QSConstant.QSItem_TYPE_ACTUAL;
            items.add(item);
        }
        return items;
    }

}
