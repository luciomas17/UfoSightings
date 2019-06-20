package it.polito.tdp.ufo.model;

import java.util.List;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	
	public Model() {
		this.dao = new SightingsDAO();
	}

	public List<AnnoAvvistamenti> getAnniEAvvistamenti() {
		return dao.getAnniEAvvistamenti();
	}
	
}
