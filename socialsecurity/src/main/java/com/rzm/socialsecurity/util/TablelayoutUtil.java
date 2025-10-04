package com.rzm.socialsecurity.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rzm.socialsecurity.R;

import java.util.List;

public class TablelayoutUtil {

    public static void addTableRow(Context context, TableLayout tableLayout, List<String> titles, List<List<String>> rowData, int titleBgColor, int rowBgColor, int rowWidth, int rowHeight) {
        // 添加title
        TableRow titleRow = new TableRow(context);
//        titleRow.setBackgroundColor(titleBgColor);
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            TextView textView = new TextView(context);
            if (i == 0) {
                textView.setWidth(150);
            } else if (i == 1) {
                textView.setWidth(280);
            }else if (i == 2) {
                textView.setWidth(150);
            }else if (i == 3) {
                textView.setWidth(200);
            }
            textView.setHeight(rowHeight);
            textView.setText(title);
            textView.setTextSize(13);
            textView.setTextColor(context.getColor(R.color.color_ff999999));
            textView.setBackgroundColor(titleBgColor);

            TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (i == 0) {
                param.setMargins(1, 1, 1, 1);
            } else {
                param.setMargins(0, 1, 1, 1);
            }
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(param);
            titleRow.addView(textView);
        }
        tableLayout.addView(titleRow);
        // 动态添加表格
        for (List<String> rows : rowData) {
            TableRow tabRow = new TableRow(context);
//            tabRow.setBackgroundColor(titleBgColor);
            for (int i = 0; i < rows.size(); i++) {
                String row = rows.get(i);
                TextView textView = new TextView(context);
                if (i == 0) {
                    textView.setWidth(150);
                } else if (i == 1) {
                    textView.setWidth(280);
                }else if (i == 2) {
                    textView.setWidth(150);
                }else if (i == 3) {
                    textView.setWidth(200);
                }
                textView.setHeight(rowHeight);
                textView.setText(row);
                textView.setTextSize(13);
                textView.setTextColor(context.getColor(R.color.color_ff333333));
                textView.setBackgroundColor(rowBgColor);

                TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (i == 0) {
                    param.setMargins(1, 0, 1, 1);
                } else {
                    param.setMargins(0, 0, 1, 1);
                }
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(param);
                tabRow.addView(textView);
            }
            tableLayout.addView(tabRow);
        }

    }


}
