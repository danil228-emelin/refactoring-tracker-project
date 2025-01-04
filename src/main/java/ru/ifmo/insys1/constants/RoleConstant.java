package ru.ifmo.insys1.constants;

import java.util.Set;

public class RoleConstant {

    public static final String MANAGER = "MANAGER";
    public static final String OPERATOR = "OPERATOR";
    public static final String CLIENT = "CLIENT";
    public static final Set<String> ROLES = Set.of(MANAGER, CLIENT, OPERATOR);
}
