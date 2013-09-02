package org.jas.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jas.gui.util.SynthColors;

public class DescriptionTableStyle extends JTable {

	private static final long serialVersionUID = -1153564046400612566L;

	private Color getEvenRowColor() {
		return SynthColors.CLEAR_GRAY245_245_245;
	}

	private Color getOddRowColor() {
		return SynthColors.WHITE255_255_255;
	}

	private Color getSelectedRowColor() {
		return SynthColors.BLUE175_205_225;
	}

	@Override
	public Color getGridColor() {
		return SynthColors.GRAY150_150_150;
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (rowIndex % 2 == 0) {
        	c.setBackground(getOddRowColor());
        } else {
        	c.setBackground(getEvenRowColor());
        }
        
        if (isRowSelected(rowIndex)) {
        	c.setBackground(getSelectedRowColor());
		}
        
        return c;
    }
	
}
