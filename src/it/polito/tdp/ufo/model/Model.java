package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private Graph<String, DefaultEdge> grafo;
	private List<String> best;
	
	public Model() {
		this.dao = new SightingsDAO();
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		
		List<String> vertici = dao.getStatiByAnno(anno);
		Collections.sort(vertici);
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<StatiSuccessivi> archi = dao.getConnessioniTraStati(anno);
		for(StatiSuccessivi a : archi) 
			this.grafo.addEdge(a.getStato1(), a.getStato2());
		
		System.out.println("Grafo creato!");
		System.out.format("%d vertici e %d archi.\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
	}
	
	public List<String> getCammino(String statoIniziale) {
		List<String> stati = getVertexList();
		List<String> parziale = new ArrayList<>();
		
		this.best = new ArrayList<>();
		parziale.add(statoIniziale);
		
		recursive(stati, parziale, 0);
		
		return best;
	}
	
	private void recursive(List<String> stati, List<String> parziale, int L) {
		if(parziale.size() > this.best.size())
			this.best = new ArrayList<>(parziale);
		
		for(String s : stati) {
			String prec = parziale.get(parziale.size()-1);
			
			if(!parziale.contains(s) && isSuccessore(prec, s)) {
				parziale.add(s);
				recursive(stati, parziale, L+1);
				parziale.remove(s);
			}
		}
	}
	
	private boolean isSuccessore(String prec, String s) {
		List<String> successori = getSuccessori(prec);
		if(successori.contains(s))
			return true;
		else 
			return false;
	}

	public List<AnnoAvvistamenti> getAnniEAvvistamenti() {
		return dao.getAnniEAvvistamenti();
	}
	
	public List<String> getPredecessori(String stato) {
		return Graphs.predecessorListOf(this.grafo, stato);
	}
	
	public List<String> getSuccessori(String stato) {
		return Graphs.successorListOf(this.grafo, stato);
	}
	
	public List<String> getRaggiungibili(String stato) {
		List<String> raggiungibili = new ArrayList<>();
		
		DepthFirstIterator<String, DefaultEdge> i = new DepthFirstIterator<>(this.grafo, stato);
		i.next();
		
		while(i.hasNext())
			raggiungibili.add(i.next());
		
		Collections.sort(raggiungibili);
		
		return raggiungibili;
	}

	public List<String> getVertexList() {
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
}
