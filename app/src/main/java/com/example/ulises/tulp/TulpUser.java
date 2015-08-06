package com.example.ulises.tulp;

import java.io.Serializable;

public class TulpUser implements Serializable {
	private String name;
	private long points;
	private String mail;
	//private String[] friendsMails;

	public TulpUser(String rawString) {
		String[] parts = rawString.split("#");
		String nombre = parts[0];
		String puntos = parts[1];
		String correo = parts[2];
		name=nombre;
		points= Long.parseLong(puntos);
		mail=correo;

	}
	public TulpUser(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long l) {
		this.points = l;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	/*
	public String[] getFriendsMails() {
		return friendsMails;
	}
	
	public void setFriendsMails(String[] friends) {
		friendsMails = friends;
	}*/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result =
				name+"#"+points+"#"+mail;
		return result;
	}
	
	
	
}
