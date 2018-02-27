package com.ingetis.ikheiry.web.controller;

import javax.servlet.http.HttpSession;

import com.ingetis.ikheiry.web.session.ConnexionHandler;

public class SessionController {
	
	public static boolean isSession(HttpSession session){
		// Real app session life cycle
		ConnexionHandler agent =  (ConnexionHandler) session.getAttribute("agent");
		boolean isThereSession = (agent==null) ? false : true;
		return isThereSession;
		
		// Pour le test rapide
		// session admin med
		//ConnexionHandler agent1 = new ConnexionHandler("admin", "123", TypeSalarie.RH);
		//session.setAttribute("agent", agent1);
		//return true;
	}
	
	public static boolean isRH(HttpSession session){
		return ((ConnexionHandler) session.getAttribute("agent")).isRH();
	}
	
	public static boolean isEmploye(HttpSession session){
		return ((ConnexionHandler) session.getAttribute("agent")).isEmploye();
	}
}
