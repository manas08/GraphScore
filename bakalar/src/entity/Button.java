package entity;

import javax.swing.JButton;

public class Button {
	JButton btn;
	int id;

	public Button(JButton btn, int id) {
		this.btn=btn;
		this.id=id;
	}

	public JButton getBtn() {
		return btn;
	}

	public void setBtn(JButton btn) {
		this.btn = btn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
