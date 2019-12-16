package com.project.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@Controller
//@SessionAttributes("productrequest")
public class SecurityProxy implements InvocationHandler {

    private Object obj;

    private SecurityProxy(Object obj) {
        this.obj = obj;
    }

    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
                .getClass().getInterfaces(), new SecurityProxy(obj));
    }

    @Override
    public Object invoke(Object o, Method m, Object[] args) throws Throwable {
        try {
            if(m.getName().contains("post")) {
                throw new IllegalAccessException("Posts are currently not allowed");
            }
            else {
                return "redirect:sessionModeOn.html";
            }
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: "
                    + e.getMessage());
        }
    }

    /*@Override
    @RequestMapping(value = "/sessionModeOn", method = RequestMethod.GET)
    public String mainInSessionModeOn(Model model) {
        System.out.println("mainInSessionModeOn method get in proxy " + model.toString());
        if (!model.containsAttribute("productrequest")) {
            return "redirect:main.html";
        }
        return sessionModeOnCatcher.mainInSessionModeOn(model);
    }

    @Override
    @RequestMapping(value = "/sessionModeOn", method = RequestMethod.POST)
    public String mainInSessionModeOn(@Valid @ModelAttribute("productrequest") ProductRequest product, BindingResult result, Model model) {
        System.out.println("mainInSessionModeOn method post in proxy " + model.toString());
        return sessionModeOnCatcher.mainInSessionModeOn(product, result, model);
    }*/
}
