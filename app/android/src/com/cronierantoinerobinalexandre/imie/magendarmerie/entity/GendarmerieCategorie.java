package com.cronierantoinerobinalexandre.imie.magendarmerie.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class GendarmerieCategorie {

	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int gendarmerieCategorieID;
	
	@Column(type = Type.STRING)
	private String titre;
}
