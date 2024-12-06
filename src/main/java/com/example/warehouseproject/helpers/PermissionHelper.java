package com.example.warehouseproject.helpers;

import com.example.warehouseproject.exceptions.UnauthorizedOperationException;
import com.example.warehouseproject.models.Role;
import com.example.warehouseproject.models.User;

public class PermissionHelper {

    public static void isOwnerOrManager(User authenticatedUser) {
        boolean isOwnerOrManager = false;

        if(authenticatedUser.getRole().equals(Role.OWNER) ||
                authenticatedUser.getRole().equals(Role.MANAGER)) {
            isOwnerOrManager = true;
        }

        if(!isOwnerOrManager) {
            throw new UnauthorizedOperationException("You are not permitted for this action!");
        }
    }
}
