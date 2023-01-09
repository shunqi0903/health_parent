package com.shunqi.controller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.constant.MessageConstant;
import com.shunqi.entity.Result;
import com.shunqi.service.MemberService;
import com.shunqi.service.ReportService;
import com.shunqi.service.SetmealService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            String format = new SimpleDateFormat("yyyy.MM").format(calendar.getTime());
            months.add(format);
        }
        List<Integer> memberCount = memberService.memberCount(months);
        Map<String, Object> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        List<Map<String, Object>> setmealCountList = setmealService.findSetmealCount();
        List<String> nameList = new ArrayList<>();
        for (Map<String, Object> map : setmealCountList) {
            String name = (String) map.get("name");
            nameList.add(name);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount",setmealCountList);
        map.put("setmealNames",nameList);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS);
    }

    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String, Object> data = reportService.getBusinessReportData();
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
    }

    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> data = reportService.getBusinessReportData();
            String reportDate = (String) data.get("reportDate");
            String todayNewMember = (String) data.get("todayNewMember");
            String thisWeekNewMember = (String) data.get("thisWeekNewMember");
            String thisMonthNewMember = (String) data.get("thisMonthNewMember");
            String totalMember = (String) data.get("totalMember");
            String todayVisitsNumber = (String) data.get("todayVisitsNumber");
            String thisWeekVisitsNumber = (String) data.get("thisWeekVisitsNumber");
            String thisMonthVisitsNumber = (String) data.get("thisMonthVisitsNumber");
            String todayOrderNumber = (String) data.get("todayOrderNumber");
            String thisWeekOrderNumber = (String) data.get("thisWeekOrderNumber");
            String thisMonthOrderNumber = (String) data.get("thisMonthOrderNumber");
            List<Map> hotSetmeal = (List<Map>) data.get("hotSetmeal");

            String templatePath = request.getSession().getServletContext().getRealPath("template")+File.separator+"report_template.xlsx";
            File file = new File(templatePath);
            XSSFWorkbook wb = new XSSFWorkbook(file);

            XSSFSheet sheet1 = wb.createSheet("sheet1");
            XSSFRow row_2 = sheet1.createRow(2);
            XSSFCell cell_2_5 = row_2.createCell(5);
            cell_2_5.setCellValue(reportDate);
            XSSFRow row_4 = sheet1.createRow(4);
            XSSFCell cell_4_5 = row_4.createCell(5);
            cell_4_5.setCellValue(todayNewMember);
            XSSFCell cell_4_7 = row_4.createCell(7);
            cell_4_7.setCellValue(totalMember);
            XSSFRow row_5 = sheet1.createRow(5);
            XSSFCell cell_5_5 = row_5.createCell(5);
            XSSFCell cell_5_7 = row_5.createCell(7);
            cell_5_5.setCellValue(thisWeekNewMember);
            cell_5_7.setCellValue(thisMonthNewMember);
            XSSFRow row = sheet1.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);
            row=sheet1.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);
            row=sheet1.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            for (int i = 0; i < hotSetmeal.size(); i++) {
                Map map = hotSetmeal.get(i);
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet1.getRow(12+i);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);

            response.setContentType("application/vnd.ms-excel");
            String filename = (String) data.get("reportDate");
            response.addHeader("content-Disposition", "attachment;filename=report"+filename+".xlsx");

            ServletOutputStream sos = response.getOutputStream();
            bos.writeTo(sos);

            sos.flush();
            bos.close();
            wb.close();

            return null;
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, Object> result = reportService.getBusinessReportData();

            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            String jrxmlPath  = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jrxml";
            String jasperPath  = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jasper";

            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint = JasperFillManager.fillReport(jrxmlPath, result, new JRBeanCollectionDataSource(hotSetmeal));

            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/pdf");
            String filename = (String) result.get("reportDate");
            response.addHeader("content-Disposition", "attachment;filename=report"+filename+".pdf");

            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint,os);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
