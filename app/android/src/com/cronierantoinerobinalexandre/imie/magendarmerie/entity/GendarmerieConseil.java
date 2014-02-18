package com.cronierantoinerobinalexandre.imie.magendarmerie.entity;

import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Id;

@Entity
public class GendarmerieConseil {

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int gendarmerieConseilID;
	
	@Column(type = Type.STRING)
	private String titre;
	
	@Column(type = Type.STRING)
	private String message;
	
	@Column(type = Type.STRING)
	private String photo;
	
	@Column(type = Type.STRING)
	private String lien;
	
	@Column
	@OneToMany
	ArrayList<GendarmerieCategorie> categorie;
}
