package it.polito.tdp.rivers.model;

public class Event implements Comparable<Event>{

	private int giorno;
	private Flow flow;
	
	public Event(int giorno, Flow flow) {
		this.giorno=giorno;
		this.flow=flow;
	}

	public int getGiorno() {
		return giorno;
	}

	public Flow getFlow() {
		return flow;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.giorno-o.getGiorno();
	}
	
	

}
