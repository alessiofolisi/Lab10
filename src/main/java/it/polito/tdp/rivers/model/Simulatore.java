package it.polito.tdp.rivers.model;

import java.util.*;
import java.util.PriorityQueue;

public class Simulatore {
		
	//Coda degli eventi
	private PriorityQueue<Event> queue;
	
	//Parametri di simulazione
	private Double Q;
	private Double k;
	private Double C;
	private River r;
	
	//Output simulazione
	private int nGiorni;
	private Double cMedia;
	private int cont=0;
	private Double totale = 0.0;
	private Double mediaRiver;
	
	//stato del mondo simulato
	List<Flow> flow = new ArrayList<Flow>();
	
	public Simulatore(River river,Double k , List<Flow> flows) {
		
		this.k=k;
		this.flow = flows;
	}
	
	public List<Flow> getSimFLow(){
		return this.flow;
	}
	
	public void init(List<Flow> flow, Double k , double media) {
		
		this.Q =(media*86400)*k*30;
		this.C = Q/2;
		
		this.queue = new PriorityQueue<>();
		for(int i=0;i<flow.size();i++) {
		this.queue.add(new Event(i+1,flow.get(i)));}
		this.mediaRiver = media;
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	public void processEvent(Event e) {
		
		double fMinOut = 0.8*this.mediaRiver*86400;
		
		if(Math.random()<=0.05) {
			fMinOut = fMinOut*10;
		}
		
		if(fMinOut>this.C+e.getFlow().getFlow()*86400) {
			this.cont++;
			this.C=0.0;
		}
		if(fMinOut<this.C+e.getFlow().getFlow()*86400) {
			this.C+=(e.getFlow().getFlow()*86400-fMinOut);
		}
		if(C>Q) {
			this.C=Q;
		}
		
		this.totale += C;
		
	}
	
	public int getCont() {
		return this.cont;
	}

	public Double cMedio() {
		return (this.totale/this.flow.size());
	}
}
