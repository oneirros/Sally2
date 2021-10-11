package com.oneirros.sally2.entity;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN;

    public String getRoleName() {
        return this.name().replaceFirst("ROLE_", "");
    }
}
