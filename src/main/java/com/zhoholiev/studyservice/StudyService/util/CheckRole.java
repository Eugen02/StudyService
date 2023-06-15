package com.zhoholiev.studyservice.StudyService.util;

import java.util.Objects;

public class CheckRole {

    String roleUser;

    public CheckRole(String role) {
        this.roleUser = role;
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public CheckRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckRole checkRole = (CheckRole) o;

        return Objects.equals(roleUser, checkRole.roleUser);
    }

    @Override
    public int hashCode() {
        return roleUser != null ? roleUser.hashCode() : 0;
    }
}
