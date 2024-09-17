package com.webapp.controller.adapter;

import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;

public class ModifyCartReturnedObject {
    ModifyCartItemsResults modifyResult;
    int oldQuantity;
    int newQuantity;

    public ModifyCartReturnedObject(ModifyCartItemsResults modifyResult, int oldQuantity, int newQuantity) {
        this.modifyResult = modifyResult;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
    }

    public ModifyCartItemsResults getModifyResult() {
        return modifyResult;
    }

    public void setModifyResult(ModifyCartItemsResults modifyResult) {
        this.modifyResult = modifyResult;
    }

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}
