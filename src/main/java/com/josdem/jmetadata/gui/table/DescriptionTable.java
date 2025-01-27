/*
   Copyright 2025 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.gui.table;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 */
public class DescriptionTable extends DescriptionTableStyle {
  private static final long serialVersionUID = 1L;

  static String[] columnNames = {
    "Artist", "Track", "Album", "Genre", "Year", "# Trk", "# Trks", "# CD", "# CDs", "Status"
  };

  static Object[][] data = {
    {"", "", "", "", "", "", "", "", "", ""},
  };

  public DescriptionTable() {
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    this.setModel(model);
    this.setName("descriptionTable");
    TableColumnModel tableColumnModel = new DefaultTableColumnModel();

    for (DescriptionTableColumns col : DescriptionTableColumns.values()) {
      tableColumnModel.addColumn(setupColumn(col));
    }

    this.setTableHeader(new DescriptionTableHeader(tableColumnModel));
    setPreferredWidth();
  }

  private TableColumn setupColumn(DescriptionTableColumns descriptionTableColumn) {
    TableColumn column = new TableColumn();
    column.setHeaderValue(descriptionTableColumn.label());
    column.setMinWidth(descriptionTableColumn.minWidth());
    column.setMaxWidth(descriptionTableColumn.maxWidth());
    return column;
  }

  private void setPreferredWidth() {
    for (int i = 0; i < this.getColumnCount(); i++) {
      switch (i) {
        case 0:
          this.getColumnModel().getColumn(i).setPreferredWidth(100);
          break;
        case 1:
          this.getColumnModel().getColumn(i).setMinWidth(180);
          this.getColumnModel().getColumn(i).setMaxWidth(Integer.MAX_VALUE);
          break;
        case 2:
        case 3:
          this.getColumnModel().getColumn(i).setPreferredWidth(100);
          break;
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
          this.getColumnModel().getColumn(i).setMinWidth(50);
          this.getColumnModel().getColumn(i).setMaxWidth(50);
          break;
        case 9:
          this.getColumnModel().getColumn(i).setMinWidth(60);
          this.getColumnModel().getColumn(i).setMaxWidth(60);
          break;
      }
    }
  }
}
