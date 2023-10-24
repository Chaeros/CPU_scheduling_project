import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class MyTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	int row;
	int column;
	
	MyTableCellRenderer(int row, int column)
	{
		this.row=row;
		this.column=column;
	}
 
	@Override

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

          Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, this.row, this.column);

          if (!isSelected) {

                 if (column == 0) {

                        cell.setForeground(Color.white);

                        if (value.equals("P1")) {

                              cell.setBackground(Color.RED);

                        } else if (value.equals("P2")) {

                              cell.setBackground(Color.ORANGE);

                        } else if (value.equals("P3")) {

                              cell.setBackground(Color.YELLOW);

                        } else if (value.equals("P4")) {

                              cell.setBackground(Color.gray);

                        }

                 } else {

                        cell.setBackground(Color.white);

                 }

          }

          return this;

    }

}
