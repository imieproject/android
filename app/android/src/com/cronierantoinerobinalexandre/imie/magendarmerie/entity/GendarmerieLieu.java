package com.cronierantoinerobinalexandre.imie.magendarmerie.entity;

import java.util.ArrayList;
import java.util.List;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class GendarmerieLieu {

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int gendarmerieLieuID;
	
	@Column(type = Type.STRING)
	private String intitule;
	
	@Column(type = Type.INTEGER)
	private int coordonneeX;
	
	@Column(type = Type.INTEGER)
	private int coordonneeY;
	
	@Column(type = Type.INTEGER)
	private int radius;
	
	@Column(type = Type.BOOLEAN)
	private boolean actif;
	
	@Column
	@OneToMany
	private ArrayList<GendarmerieLieu> lieux;
}
