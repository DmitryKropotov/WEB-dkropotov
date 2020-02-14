package com.webapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomController {
    @RequestMapping(value="/welcome")
    public ModelAndView welcomeUser()
    {
        System.out.println("requestMapping welcome");
        return new ModelAndView("welcome");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        System.out.println("requestMapping login");
        return "main";
    }

    @RequestMapping(value="/sessionModeOnMainPage", method = RequestMethod.GET)
    public String sessionModeOnMainPage (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("sessionModeOnMainPage myJSPPage");
        return "sessionModeOnMainPage";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("requestMapping logout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/main";
    }

    @RequestMapping(value="/myJSPPage", method = RequestMethod.GET)
    public String myJSPPage (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("requestMapping myJSPPage");
        return "myJSPPage";
    }
}
