package com.maktabsharif.entity.enumeration;

public enum ExpertStatus {
    NEW("New"), APPROVED("Approved"), PENDING_APPROVAL("Pending approval");

    private final String toString;

    private ExpertStatus(String toString) {
        this.toString = toString;
    }

    public String toString(){
        return toString;
    }

}
