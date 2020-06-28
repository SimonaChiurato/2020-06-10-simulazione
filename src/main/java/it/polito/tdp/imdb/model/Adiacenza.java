package it.polito.tdp.imdb.model;

public class Adiacenza {
	private Actor actor1;
	private Actor actor2; 
	private double peso;
	public Actor getActor1() {
		return actor1;
	}
	public void setActor1(Actor actor1) {
		this.actor1 = actor1;
	}
	public Actor getActor2() {
		return actor2;
	}
	public void setActor2(Actor actor2) {
		this.actor2 = actor2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public Adiacenza(Actor actor1, Actor actor2, double peso) {
		super();
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.peso = peso;
	}
	
	

}
