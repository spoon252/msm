package modeli;

import java.util.Arrays;
import entiteti.Izvodjac;

public class IzvodjacTable extends RowTableModel<Izvodjac> {

    private static String[] COLUMN_NAMES =
    {
        "Ime",
        "Prezime",
        "Tip"
    };
    
    public IzvodjacTable()
    {
    	super( Arrays.asList(COLUMN_NAMES) );
		setRowClass( Izvodjac.class );
    }
    
	@Override
	public Object getValueAt(int row, int column)
	{
		Izvodjac izvodjac = getRow(row);

		switch (column)
        {
            case 0: 
            	return izvodjac.getIme();
            case 1: 
            	return izvodjac.getPrezime();
            case 2: 
            	return izvodjac.getTip();
            default: 
            	return null;
        }
	}


	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Izvodjac izvodjac = getRow(row);

		switch (column)
        {
            case 0: 
            	izvodjac.setIme((String)value); 
            	break;
            case 1: 
            	izvodjac.setPrezime((String)value);
            	break;
            case 2: 
            	izvodjac.setTip((String)value); 
            	break;
        }

		fireTableCellUpdated(row, column);
	}
}
