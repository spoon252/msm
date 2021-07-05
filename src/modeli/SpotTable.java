package modeli;
import java.util.Arrays;
import java.util.List;
import entiteti.Spot;

@SuppressWarnings("serial")
public class SpotTable extends RowTableModel<Spot> {

    private static String[] COLUMN_NAMES =
    {
        "Pjesma",
        "Godina",
        "Spot"
    };
    
    public SpotTable()
    {
    	super( Arrays.asList(COLUMN_NAMES) );
		setRowClass( Spot.class );
    }
    
	@Override
	public Object getValueAt(int row, int column)
	{
		Spot spot = getRow(row);

		switch (column)
        {
            case 0: 
            	return spot.getNazivPjesme();
            case 1: 
            	return spot.getGodina();
            case 2: 
            	return spot.getYoutubeLink();
            default: 
            	return null;
        }
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Spot spot = getRow(row);

		switch (column)
        {
            case 0: 
            	spot.setNazivPjesme((String)value); 
            	break;
            case 1: 
            	spot.setGodina((Integer)value);
            	break;
            case 2: 
            	spot.setYoutubeLink((String)value); 
            	break;
        }

		fireTableCellUpdated(row, column);
	}
}
