package it.polito.tdp.rivers.model;

import java.util.*;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	RiversDAO dao;
	Map<Integer,River> idMap;
	
	public Model() {
		dao = new RiversDAO();
		idMap = new HashMap<Integer,River>();
	}

	
	public List<River> getAllRivers(){
		
		for(River r : this.dao.getAllRivers()) {
			idMap.put(r.getId(), r);
		}
		
		return this.dao.getAllRivers();
	}
	
	public List<Flow> getDateFlow(River river) {
		
		List<Flow> result = this.dao.getDateFlow(river, this.idMap);

		Collections.sort(result);
		
		return result;
	}
	
	public double getMedia(River river) {
	
		List<Flow> flow = this.getDateFlow(river);
		
		double somma = 0;
		double totale = 0;
		
		for(Flow f : flow) {
			somma+=f.getFlow();
			totale++;
		}
		double media = Math.round((somma/totale)*100.0)/100.0;
		return media;
	}
	
	public List<Flow> getAllFlows(){
		return this.dao.getAllFlow(this.idMap);
	}
	
	public String simula(River r , Double k) {
		
		Simulatore sim = new Simulatore(r,k , this.getDateFlow(r));
		
		sim.init(sim.getSimFLow(), k , this.getMedia(r));
		sim.run();
		
		return sim.getCont() + "\n" + sim.cMedio();
	}
	
}
