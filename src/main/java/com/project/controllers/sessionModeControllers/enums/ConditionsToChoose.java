package com.project.controllers.sessionModeControllers.enums;

public enum ConditionsToChoose {
    EQUAL,
    MORE,
    MORE_OR_EQUAL,
    LESS,
    LESS_OR_EQUAL;

    @Override
    public String toString() {
        switch (this) {
            case EQUAL: {
                return "=";
            }
            case MORE: {
                return ">";
            } case MORE_OR_EQUAL: {
                return ">=";
            } case LESS: {
                return "<";
            } case LESS_OR_EQUAL: {
                return "<=";
            } default: {
                return super.toString();
            }
        }
    }
}
