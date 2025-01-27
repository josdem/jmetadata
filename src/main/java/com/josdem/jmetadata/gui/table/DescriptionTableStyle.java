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

import com.josdem.jmetadata.gui.util.SynthColors;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

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
