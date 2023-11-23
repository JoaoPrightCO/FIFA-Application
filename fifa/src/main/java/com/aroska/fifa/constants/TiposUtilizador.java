package com.aroska.fifa.constants;

public enum TiposUtilizador {
	NO_VERIFICATION(99),
	ADMIN(1),
	NORMAL_USER(2);

	private int id;

	private TiposUtilizador(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

}
