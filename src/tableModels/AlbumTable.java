package tableModels;

import java.util.Arrays;
import models.Album;

public class AlbumTable extends RowTableModel<Album> {

    private static String[] COLUMN_NAMES =
    {
        "Naziv",
        "Godina"        
    };
    
    public AlbumTable()
    {
    	super( Arrays.asList(COLUMN_NAMES) );
		setRowClass( Album.class );
    }
    
	@Override
	public Object getValueAt(int row, int column)
	{
		Album album = getRow(row);

		switch (column)
        {
            case 0: 
            	return album.getNaziv();
            case 1: 
            	return album.getGodina();
            default: 
            	return null;
        }
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Album album = getRow(row);

		switch (column)
        {
            case 0: 
            	album.setNaziv((String)value); 
            	break;
            case 1: 
            	album.setGodina((Integer)value); 
            	break;
        }

		fireTableCellUpdated(row, column);
	}
}
