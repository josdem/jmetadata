/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.jas.gui.table;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jas.gui.util.SynthFonts;
import org.jas.gui.util.SynthIcons;

public class DescriptionTableHeader extends JTableHeader {

	private static final long serialVersionUID = 1L;

	public DescriptionTableHeader(TableColumnModel columnModel) {
		super(columnModel);
		this.setReorderingAllowed(true);
		this.setDefaultRenderer(new DescriptionTableHeaderCellRenderer());
	}

}


class DescriptionTableHeaderCellRenderer implements TableCellRenderer {
	private static final String CONTAINER_NAME = "tableHeader";
	private static final String CONTAINER_SORT_NAME = "tableSortedHeader";
	private static final Insets ICON_INSETS = new Insets(0, 0, 0, 3);
	private static final Insets LABEL_INSETS = new Insets(0, 4, 0, 3);
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JLabel iconLabel = new JLabel();

	public DescriptionTableHeaderCellRenderer() {
		label.setName(SynthFonts.BOLD_FONT11_BLACK);
		GridBagConstraints iconConstraints = new GridBagConstraints();
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.fill = GridBagConstraints.HORIZONTAL;
		labelConstraints.weightx = 1.0;
		labelConstraints.insets = LABEL_INSETS;
		iconConstraints.gridx = 1;
		iconConstraints.gridy = 0;
		iconConstraints.insets = ICON_INSETS;
		panel.setLayout(new GridBagLayout());
		panel.add(label, labelConstraints);
		panel.add(iconLabel, iconConstraints);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Icon sortIcon = getIcon(table, column);
		iconLabel.setIcon(sortIcon);
		label.setText(value.toString());
		label.setName(SynthFonts.BOLD_FONT11_BLACK);

		setLabelAlignment(value.toString(), label);

		if (sortIcon != null) {
			label.setName(SynthFonts.BOLD_FONT11_WHITE);
			panel.setName(CONTAINER_SORT_NAME);
			iconLabel.setVisible(true);
		} else {
			panel.setName(CONTAINER_NAME);
			iconLabel.setVisible(false);
		}
		return panel;
	}

	private void setLabelAlignment(String columnName, JLabel label) {
		label.setHorizontalAlignment(JLabel.LEFT);
		if (columnName.equals("Plays") || columnName.equals("Time") || columnName.equals("Bitrate")
				|| columnName.equals("Size") || columnName.equals("Skips")) {
			label.setHorizontalAlignment(JLabel.RIGHT);
		}
		if (columnName.equals("Rating")) {
			label.setHorizontalAlignment(JLabel.CENTER);
		}
	}

	private Icon getIcon(JTable table, int column) {
		// DescriptionTable dTable = (DescriptionTable) table;
		if (table == null || table.getRowSorter() == null) {
			return UIManager.getIcon("Table.naturalSortIcon");
		}
		Icon sortIcon = null;

		List<? extends RowSorter.SortKey> sortKeys = table.getRowSorter().getSortKeys();
		if (sortKeys.size() > 0 && sortKeys.get(0).getColumn() == table.convertColumnIndexToModel(column)) {
			switch (sortKeys.get(0).getSortOrder()) {
			case ASCENDING:
				sortIcon = SynthIcons.SORT_ASCENDING_ICON;
				break;
			case DESCENDING:
				sortIcon = SynthIcons.SORT_DESCENDING_ICON;
				break;
			case UNSORTED:
				sortIcon = SynthIcons.SORT_NATURAL_ICON;
				break;
			default:
				throw new AssertionError("Cannot happen");
			}
		}

		return sortIcon;
	}
}
