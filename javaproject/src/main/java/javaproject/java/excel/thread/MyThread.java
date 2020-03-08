package javaproject.java.excel.thread;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;


public class MyThread implements Runnable{
    private String outPutPath = "E:\\JOB\\result";

    private File[] filelist;
    private String threadname;
    private int start;
    private int end;
    private int sentencecount=0;

    public MyThread(String threadname,File[] filelist,int start, int end) {
        this.filelist = filelist;
        this.threadname = threadname;
        this.start = start;
        this.end = end;

//        File file = new File(outPutPath);
//        if(file.exists()){
//            if(file.isDirectory()){
//                file.delete();
//            }
//        }
//
//        file.mkdir();


    }

    @Override
    public void run() {
        try {
            for (int i = start; i < end; i++) {
                if(!filelist[i].isDirectory()){
                    //是文件
                    parseExcel(filelist[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开始解析
    public void parseExcel(File file){
        //获取文章内容
        String sb = getFileContent(file);
        String[] split = sb.split("。");
        //putIntoExcel(file,split);
        int count = getSentenceCount(split);
        sentencecount += count;
        System.out.println(file.getName()+"----"+count+"-----"+sentencecount);
        putIntoTxt(file,split);
    }

    /**
     * 写入txt
     * @param file
     * @param split
     */
    public void putIntoTxt(File file,String[] split){
        try {
            String fileid = file.getName().substring(0,file.getName().length()-4);
            String filepath = outPutPath+File.separator+fileid+".txt";
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath),"GBK"));
            for (int i = 0; i < split.length; i++) {
                String tmpstr = strFilter(split[i]);
                if(tmpstr.trim().replaceAll("\\p{P}", "").length()==0){
                    continue;
                }
                out.write(tmpstr+"。");
                out.newLine();
            }
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 写入excel
     * @param file
     * @param split
     */
    public void putIntoExcel(File file,String[] split){
        Workbook workbook = new XSSFWorkbook(); // 创建工作簿
        Sheet sheet = workbook.createSheet(); // 创建Sheet
        Row header = sheet.createRow(0); // 创建表头行

        Cell headerCell0 = header.createCell(0); // 主键
        headerCell0.setCellValue("主键id");
        Cell headerCell1 = header.createCell(1); // 事件
        headerCell1.setCellValue("events");
        Cell headerCell2 = header.createCell(2); // 时间
        headerCell2.setCellValue("time");
        Cell headerCell3 = header.createCell(3); // 地址
        headerCell3.setCellValue("location");
        Cell headerCell4 = header.createCell(4); // 参与者
        headerCell4.setCellValue("participant");
        Cell headerCell5 = header.createCell(5); // 创建表头单元格
        headerCell5.setCellValue("denoter");
        Cell headerCell6 = header.createCell(6); // 对象
        headerCell6.setCellValue("object");


        CellStyle style = workbook.createCellStyle(); // 普通单元格样式
        style.setWrapText(true);

        String fileid = file.getName().substring(0,file.getName().length()-4);
        for (int i = 0; i < split.length; i++) {
            if(split[i].isEmpty()) continue;

            Row row = sheet.createRow(i); // 写入单元格
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(fileid);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(split[i]+"。");
            cell1.setCellStyle(style);
        }

        try {
            // 最后写出到文件
            //System.out.println(prefix_filename);
            String filepath = outPutPath+fileid+".xlsx";
            FileOutputStream outputStream = new FileOutputStream(filepath);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //获取文章内容
    public String getFileContent(File file){
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));//这里主要是涉及中文
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String strFilter(String str){
        String strtemp = str;
        strtemp=strtemp.replace("\r", "");
        strtemp=strtemp.replace("\n", "");
        strtemp=strtemp.replace(" ", "");
        strtemp=strtemp.replace("/", "");
        strtemp=strtemp.replace("\\", "");
        return strtemp;
    }

    //获取一个文件里的句子数
    public int getSentenceCount(String[] split){
        int count=0;
        for (int i = 0; i < split.length; i++) {
            if(split[i].trim().replaceAll("\\p{P}", "").length()==0){
                //如果这一行只有符号的话
                continue;
            }
            count += 1;
        }
        return count;
    }

}
