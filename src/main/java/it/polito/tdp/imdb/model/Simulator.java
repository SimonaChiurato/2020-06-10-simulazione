package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Simulator {

	int N;
	SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo;

	List<Actor> intervistati;
	List<Actor> vertici;
	int giorniPausa;
	boolean pausa;
	boolean giornoDopoPausa;
	Random r;

	public Simulator(int N, SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo) {
		this.N = N;
		this.grafo = grafo;
		this.intervistati = new ArrayList<>();
		this.giorniPausa = 0;
		this.pausa = false;
		this.r = new Random();
		this.vertici = new ArrayList<>(this.grafo.vertexSet());
		this.giornoDopoPausa=false;
	}

	public void run() {
		while (N != 0) {
			System.out.println("ciao");
			if (pausa == true) {
				System.out.println("pausa");
				N--;
				this.giorniPausa++;
				pausa = false;
				this.giornoDopoPausa=true;

			} else {
				int prob = r.nextInt(100);

				if (intervistati.size() == 0 || this.giornoDopoPausa==true|| prob < 60 ) {
					int p = r.nextInt(this.vertici.size());
					Actor a = vertici.get(p);
					intervistati.add(a);
					vertici.remove(a);
					N--;
				} else {
					Actor a = this.attoreMassimo(intervistati.get(intervistati.size() - 1));
					if (a == null) {
						int p = r.nextInt(this.vertici.size());
						a = vertici.get(p);
					}
					intervistati.add(a);
					vertici.remove(a);
					N--;
				}

				if (intervistati.size() > 2 && intervistati.get(intervistati.size() - 1).getGender()
						.equals(intervistati.get(intervistati.size() - 2).getGender())) {
					int prob1 = r.nextInt(100);
					if (prob1 < 90) {
						pausa = true;
					}
				}
			}
		}
	}

	private Actor attoreMassimo(Actor actor) {
		Actor result = null;
		double grado = 0.0;
		for (Actor a : Graphs.neighborListOf(grafo, actor)) {
			if (grafo.getEdgeWeight(grafo.getEdge(actor, a)) > grado) {
				result = a;
				grado = grafo.getEdgeWeight(grafo.getEdge(actor, a));
			}
		}
		return result;
	}

	public int giorniPausa() {
		return this.giorniPausa;
	}
	public List<Actor> intervistati(){
		return this.intervistati;
	}
}
