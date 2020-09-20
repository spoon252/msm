package modeli;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import entiteti.Izvodjac;

public class IzvodjacListRender extends DefaultListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Izvodjac item = (Izvodjac) value;
		if (item.getPrezime() != "" && item.getPrezime() != null)
			((JLabel) c).setText(item.getIme() + " " + item.getPrezime());
		else
			((JLabel) c).setText(item.getIme());
		return c;
	}
}
