package com.kk.android.jetwanandroid;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by Mera on 3/4/21.
 */
public class IdentifierManager {

    /* renamed from: b */
    private static Object idProviderImplObject;

    /* renamed from: c */
    private static Class<?> idProviderImplClazz;

    /* renamed from: d */
    private static Method getUDIDMethod;

    /* renamed from: e */
    private static Method getOAIDMethod;

    /* renamed from: f */
    private static Method getVAIDMethod;

    /* renamed from: g */
    private static Method getAAIDMethod;

    static {
        try {
            idProviderImplClazz = Class.forName("com.android.id.impl.IdProviderImpl");
            idProviderImplObject = idProviderImplClazz.newInstance();
            getUDIDMethod = idProviderImplClazz.getMethod("getUDID", new Class[]{Context.class});
            getOAIDMethod = idProviderImplClazz.getMethod("getOAID", new Class[]{Context.class});
            getVAIDMethod = idProviderImplClazz.getMethod("getVAID", new Class[]{Context.class});
            getAAIDMethod = idProviderImplClazz.getMethod("getAAID", new Class[]{Context.class});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public static boolean m11380a() {
        return idProviderImplClazz != null && idProviderImplObject != null;
    }

    /* renamed from: b */
    public static String getOAID(Context context) {
        return invoke(context, getOAIDMethod);
    }

    /* renamed from: c */
    public static String getVAID(Context context) {
        return invoke(context, getVAIDMethod);
    }

    /* renamed from: d */
    public static String getAAID(Context context) {
        return invoke(context, getAAIDMethod);
    }

    /* renamed from: a */
    public static String getUDID(Context context) {
        return invoke(context, getUDIDMethod);
    }

    /* renamed from: a */
    private static String invoke(Context context, Method method) {
        Object obj = idProviderImplObject;
        if (!(obj == null || method == null)) {
            try {
                Object invoke = method.invoke(obj, new Object[]{context});
                if (invoke != null) {
                    return (String) invoke;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
