package com.webapp.controller.adapter;

import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyCartReturnedObject {
    ModifyCartItemsResults modifyResult;
    int oldQuantity;
    int newQuantity;
}
