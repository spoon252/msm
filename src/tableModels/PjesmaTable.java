package tableModels;

import java.util.Arrays;
import java.util.List;
import models.Pjesma;

@SuppressWarnings("serial")
public class PjesmaTable extends RowTableModel<Pjesma> {

    private static String[] COLUMN_NAMES =
    {
        "Naziv",
        "Album",
        "Trajanje"
    };
    
    private List<Pjesma> pjesme;
    public PjesmaTable()
    {
    	super( Arrays.asList(COLUMN_NAMES) );
		setRowClass( Pjesma.class );
    }
    
	@Override
	public Object getValueAt(int row, int column)
	{
		Pjesma pjesma = getRow(row);

		switch (column)
        {
            case 0: 
            	return pjesma.getNaziv();
            case 1: 
            	return pjesma.getAlbum();
            case 2: 
            	return pjesma.getTrajanje();
            default: 
            	return null;
        }
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Pjesma pjesma = getRow(row);

		switch (column)
        {
            case 0: 
            	pjesma.setNaziv((String)value); 
            	break;
            case 1: 
            	pjesma.setAlbum((String)value);
            	break;
            case 2: 
            	pjesma.setTrajanje((Integer)value); 
            	break;
        }

		fireTableCellUpdated(row, column);
	}
}
