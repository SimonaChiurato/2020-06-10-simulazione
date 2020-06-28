package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;


import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	ImdbDAO dao;
	SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo;
	Map<Integer, Actor> idMap;
	private Simulator sim;



	public Model() {
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<>();
		this.dao.listAllActors(idMap);

	}

	public List<String> listAllGeneri() {
		return this.dao.listAllGeneri();
	}

	public void creaGrafo(String genere) {
		this.grafo = new SimpleWeightedGraph<Actor, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.dao.listAllVertici(genere, idMap));

		for (Adiacenza a : this.dao.listAllAdiacenza(genere, idMap)) {
			if (grafo.containsVertex(a.getActor1()) && grafo.containsVertex(a.getActor2())) {
				Graphs.addEdge(grafo, a.getActor1(), a.getActor2(), a.getPeso());
			}
		}
	}

	public Set<Actor> vertici() {
		return this.grafo.vertexSet();
	}

	public Set<DefaultWeightedEdge> archi() {
		return this.grafo.edgeSet();
		}
	public List<Actor> connessi(Actor actor){
		List<Actor> result = new ArrayList<>();
		BreadthFirstIterator<Actor, DefaultWeightedEdge> it = new BreadthFirstIterator<Actor, DefaultWeightedEdge>(grafo, actor);
		while(it.hasNext()) {
			result.add(it.next());
		}
		Collections.sort(result, new ComparatorOrdineAlfabetico());
		return result; 
		}
	
	public void  simula(int N) {
		this.sim= new Simulator(N, grafo);
		this.sim.run();
	}
	public int giorniPausa() {
		return this.sim.giorniPausa();
	}
	public List<Actor> intervistati(){
		return this.sim.intervistati();
	}
}