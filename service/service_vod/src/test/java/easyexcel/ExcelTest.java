package easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    //循环设置要添加的数据，最终封装到list集合中
    private static List<User> data() {
        List<User> list = new ArrayList<User>();
        for (int i = 1; i < 10; i++) {
            User data = new User();
            data.setSno(i);
            data.setSname("张三" + i);
            list.add(data);
        }
        return list;
    }

//    public static void main(String[] args) throws Exception {
//        // 写法1
//        String fileName = "D:\\Users\\Suma\\Desktop\\讲师信息.xlsx";
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        // 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, User.class).sheet("写入方法").doWrite(data());
//    }

    //调用实现最终的读取**
    public static void main(String[] args) throws Exception {
        String fileName = "D:\\Users\\Suma\\Desktop\\讲师信息.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, User.class, new ExcelListener()).sheet().doRead();
    }
}
