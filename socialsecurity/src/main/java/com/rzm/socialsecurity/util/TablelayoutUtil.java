package com.rzm.socialsecurity.util;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class TablelayoutUtil {

    public static void addTableRow(Context context, TableLayout tableLayout, List<String> titles, List<List<String>> rowData, int titleBgColor, int rowBgColor) {
        // 添加title
        TableRow titleRow = new TableRow(context);
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            TextView textView = new TextView(context);
            textView.setWidth(200);
            textView.setHeight(80);
            textView.setText(title);
            textView.setBackgroundColor(titleBgColor);

            TableRow.LayoutParams param=new TableRow.LayoutParams();
            if (i == 0) {
                param.setMargins(1, 1, 1, 1);
            } else {
                param.setMargins(0, 1, 1, 1);
            }
            textView.setLayoutParams(param);
            titleRow.addView(textView);
        }
        tableLayout.addView(titleRow);
        // 动态添加表格
        for (List<String> rows : rowData) {
            TableRow tabRow = new TableRow(context);
            for (int i = 0; i < rows.size(); i++) {
                String row = rows.get(i);
                TextView textView = new TextView(context);
                textView.setWidth(200);
                textView.setHeight(80);
                textView.setText(row);
                textView.setBackgroundColor(rowBgColor);

                TableRow.LayoutParams param=new TableRow.LayoutParams();
                if (i == 0) {
                    param.setMargins(1, 0, 1, 1);
                } else {
                    param.setMargins(0, 0, 1, 1);
                }
                textView.setLayoutParams(param);
                tabRow.addView(textView);
            }
            tableLayout.addView(tabRow);
        }

    }


}
