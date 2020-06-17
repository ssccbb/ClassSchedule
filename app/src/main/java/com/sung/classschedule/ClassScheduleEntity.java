package com.sung.classschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by sung at 2020/6/16
 *
 * @desc:
 * @notice:
 */
public class ClassScheduleEntity {
    private int row;
    private int column;
    private String rowTag;
    private String columnTag;
    private List<String> columnTags;
    private List<String> rowTags;
    private Map<String, ClassScheduleElement> elementMap;

    public ClassScheduleEntity(int row, int column, String rowTag, String columnTag, List<String> columnTags, List<String> rowTags) {
        this.row = row > 1 ? row : 2;
        this.column = column > 1 ? column : 2;
        this.rowTag = rowTag;
        this.columnTag = columnTag;
        this.columnTags = new ArrayList<>();
        if (!columnTags.isEmpty()) {
            this.columnTags.addAll(columnTags);
        }
        this.rowTags = new ArrayList<>();
        if (!rowTags.isEmpty()) {
            this.rowTags.addAll(rowTags);
        }
        this.elementMap = new HashMap<>((row - 1) * (column - 1));
    }

    public Map<String, ClassScheduleElement> getElementMap() {
        return elementMap;
    }

    public String getRowTag() {
        return rowTag;
    }

    public String getColumnTag() {
        return columnTag;
    }

    public int getColumnCounts() {
        return column;
    }

    public int getRowCounts() {
        return row;
    }

    public ClassScheduleElement getElement(int row, int column) {
        Map<String, ClassScheduleElement> elementMap = getElementMap();
        if (elementMap == null || elementMap.keySet().isEmpty()) {
            return null;
        }
        String key = readKey(row, column);
        if (elementMap.containsKey(key)) {
            return elementMap.get(key);
        }
        return null;
    }

    public void addRow(String rowTag) {
        try {
            int rowPos = getRowCounts();
            int columnPos = getColumnCounts();
            for (int i = 0; i < columnPos; i++) {
                if (i == 0) continue;
                addElement(new ClassScheduleElement(rowPos, i, false));
            }
            row++;
            rowTags.add(rowTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColumn(String columnTag) {
        try {
            int rowPos = getRowCounts();
            int columnPos = getColumnCounts();
            for (int i = 0; i < rowPos; i++) {
                if (i == 0) continue;
                addElement(new ClassScheduleElement(i, columnPos, false));
            }
            column++;
            columnTags.add(columnTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addElement(ClassScheduleElement element) {
        String key = "";
        try {
            key = readKey(element.rowPos, element.columnPos);
            if (element.rowPos < 0 || element.columnPos < 0) {
                return key;
            }
            if (elementMap.containsKey(key)) {
                elementMap.replace(key, element);
            } else {
                elementMap.put(key, element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public boolean checkEntityIllegal() {
        if (row <= 1 || column <= 1) {
            return false;
        }
        if (elementMap.isEmpty()) {
            return false;
        }
        int count = (row - 1) * (column - 1);
        if (elementMap.keySet().size() < count) {
            return false;
        }
        return true;
    }

    private String readKey(int row, int column) {
        return row + "-" + column;
    }

    public class ClassScheduleElement {
        private int rowPos;
        private int columnPos;
        private boolean check;

        public ClassScheduleElement(int rowPos, int columnPos, boolean check) {
            this.rowPos = rowPos;
            this.columnPos = columnPos;
            this.check = check;
        }
    }
}
