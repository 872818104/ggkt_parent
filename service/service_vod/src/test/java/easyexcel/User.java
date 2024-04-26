package easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class User {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;
    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;
}
