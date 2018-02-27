package com.ingetis.ikheiry.web.session;

import com.ingetis.ikheiry.model.enumeartion.TypeSalarie;

public class ConnexionHandler {
	private String login;
	private String motDePasse;
	private TypeSalarie fonction;
	public ConnexionHandler() {
		
	}
	public ConnexionHandler(String login, String motDePasse) {
		this.login = login;
		this.motDePasse = motDePasse;
	}
	public ConnexionHandler(String login, String motDePasse, TypeSalarie fonction) {
		this.login = login;
		this.motDePasse = motDePasse;
		this.fonction = fonction;
	}
	public boolean isRH(){
		return fonction.equals(TypeSalarie.RH) ? true : false;
	}
	public boolean isEmploye(){
		return fonction.equals(TypeSalarie.EMPLOYE) ? true : false;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public TypeSalarie getFonction() {
		return fonction;
	}
	public void setFonction(TypeSalarie fonction) {
		this.fonction = fonction;
	}
	/*	 
    Variable Expressions: ${…}
    Selection Variable Expressions: *{…}
    Message Expressions: #{…}
    Link URL Expressions: @{…}
	*/
}
