package com.cronierantoinerobinalexandre.imie.magendarmerie.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Id;

@Entity
public class GendarmerieAlerte {
	
	@Id
	@Column(type = Type.INTEGER, hidden = true)
	private int gendarmerieAlerteID;
	
	@Column(type = Type.STRING)
	private String message;
}
