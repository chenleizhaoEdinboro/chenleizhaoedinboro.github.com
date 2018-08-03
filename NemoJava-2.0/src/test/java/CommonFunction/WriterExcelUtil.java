package CommonFunction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.*;
import jxl.write.*;

public class WriterExcelUtil {
	public static void writeExcel(List<Map<String, Object>> list, String path,List<Map<String, Object>> data) {
        try {
            // Excel底部的表名
            String sheetn = "table1";
            String sheetn1 = "table2";
            // 用JXL向新建的文件中添加内容
            File myFilePath = new File(path);
            if (!myFilePath.exists())
                myFilePath.createNewFile();
            OutputStream outf = new FileOutputStream(path);
            WritableWorkbook wwb = Workbook.createWorkbook(outf);
            jxl.write.WritableSheet writesheet = wwb.createSheet(sheetn, 1);
            jxl.write.WritableSheet writesheet1 = wwb.createSheet(sheetn1, 1);
            
            // 设置标题
            if (list.size() > 0) {
                int j = 0;
                for (Entry<String, Object> entry : list.get(0).entrySet()) {
                    String title = entry.getKey();
                    writesheet.addCell(new Label(j, 0, title));
                    j++;
                }
            }
            
         // 设置标题
            if (list.size() > 0) {
                int j = 0;
                for (Entry<String, Object> entry : data.get(0).entrySet()) {
                    String title = entry.getKey();
                    writesheet1.addCell(new Label(j, 0, title));
                    j++;
                }
            }
            // 内容添加
            for (int i = 1; i <= list.size(); i++) {
                int j = 0;
                for (Entry<String, Object> entry : list.get(i - 1).entrySet()) {
                    Object o = entry.getValue();
                    if (o instanceof Double) {
                        writesheet.addCell(new jxl.write.Number(j, i, (Double) entry.getValue()));
                    } else if (o instanceof Integer) {
                        writesheet.addCell(new jxl.write.Number(j, i, (Integer) entry.getValue()));
                    } else if (o instanceof Float) {
                        writesheet.addCell(new jxl.write.Number(j, i, (Float) entry.getValue()));
                    } else if (o instanceof Float) {
                        writesheet.addCell(new jxl.write.DateTime(j, i,(Date) entry.getValue()));
                    } else if (o instanceof BigDecimal) {
                        writesheet.addCell(new jxl.write.Number(j, i, ((BigDecimal) entry
                                .getValue()).doubleValue()));
                    } else if (o instanceof Long) {
                        writesheet.addCell(new jxl.write.Number(j, i, ((Long) entry.getValue())
                                .doubleValue()));
                    } else {
                        writesheet.addCell(new Label(j, i, (String) entry.getValue()));
                    }
                    j++;
                }
            }
            
            // 内容添加
            for (int i = 1; i <= data.size(); i++) {
                int j = 0;
                for (Entry<String, Object> entry : data.get(i - 1).entrySet()) {
                    Object o = entry.getValue();
                    if (o instanceof Double) {
                        writesheet1.addCell(new jxl.write.Number(j, i, (Double) entry.getValue()));
                    } else if (o instanceof Integer) {
                        writesheet1.addCell(new jxl.write.Number(j, i, (Integer) entry.getValue()));
                    } else if (o instanceof Float) {
                        writesheet1.addCell(new jxl.write.Number(j, i, (Float) entry.getValue()));
                    } else if (o instanceof Float) {
                        writesheet1.addCell(new jxl.write.DateTime(j, i,(Date) entry.getValue()));
                    } else if (o instanceof BigDecimal) {
                        writesheet1.addCell(new jxl.write.Number(j, i, ((BigDecimal) entry
                                .getValue()).doubleValue()));
                    } else if (o instanceof Long) {
                        writesheet1.addCell(new jxl.write.Number(j, i, ((Long) entry.getValue())
                                .doubleValue()));
                    } else {
                        writesheet1.addCell(new Label(j, i, (String) entry.getValue()));
                    }
                    j++;
                }
            }
            wwb.write();
            wwb.close();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}