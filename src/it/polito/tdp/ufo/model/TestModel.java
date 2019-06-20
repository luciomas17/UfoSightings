package it.polito.tdp.ufo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		List<AnnoAvvistamenti> anni = model.getAnniEAvvistamenti();
		
		System.out.println(anni);
	}

}
