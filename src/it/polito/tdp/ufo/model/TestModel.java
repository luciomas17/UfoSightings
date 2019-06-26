package it.polito.tdp.ufo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		List<AnnoAvvistamenti> anni = model.getAnniEAvvistamenti();
		
		System.out.println(anni);
		
		model.creaGrafo(1949);
		
		String st = "ca";
		System.out.format("Predecessori di %s:\n%s\n", st, model.getPredecessori(st));
		System.out.format("Successori di %s:\n%s\n", st, model.getSuccessori(st));
		System.out.format("Raggiungibili da %s:\n%s\n", st, model.getRaggiungibili(st));
		System.out.println("");
		
		System.out.println("Cammino migliore:");
		System.out.println(model.getCammino(st));
		
	}

}
