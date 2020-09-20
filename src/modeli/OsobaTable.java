package modeli;

import java.sql.Date;
import java.util.Arrays;

import entiteti.Osoba;

public class OsobaTable extends RowTableModel<Osoba> {

    private static String[] COLUMN_NAMES =
    {
        "Ime",
        "Prezime",
        "Datum rođenja"
    };
    
    public OsobaTable()
    {
    	super( Arrays.asList(COLUMN_NAMES) );
		setRowClass( Osoba.class );
    }
    
	@Override
	public Object getValueAt(int row, int column)
	{
		Osoba osoba = getRow(row);

		switch (column)
        {
            case 0: 
            	return osoba.getIme();
            case 1: 
            	return osoba.getPrezime();
            case 2: 
            	return osoba.getDatumRodjenja();
            default: 
            	return null;
        }
	}


	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Osoba osoba = getRow(row);

		switch (column)
        {
            case 0: 
            	osoba.setIme((String)value); 
            	break;
            case 1: 
            	osoba.setPrezime((String)value);
            	break;
            case 2: 
            	osoba.setDatumRodjenja((Date)value);
            	break;
        }

		fireTableCellUpdated(row, column);
	}
}
