package com.beyti.models;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Connection;

public class BaseModel {
    protected static Connection conn;

    public static void setConnection(Connection conn) {
        BaseModel.conn = conn;
    }
}