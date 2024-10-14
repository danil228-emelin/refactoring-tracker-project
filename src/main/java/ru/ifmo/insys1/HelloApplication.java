package ru.ifmo.insys1;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;
import static ru.ifmo.insys1.constants.RoleConstant.USER;

@ApplicationPath("/api")
@DeclareRoles({USER, ADMIN})
public class HelloApplication extends Application {

}