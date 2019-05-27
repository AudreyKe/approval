package com.weshareholdings.approval;

import com.alibaba.fastjson.JSON;
import com.weshareholdings.approval.po.ProAttachTask;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.repository.*;
import com.weshareholdings.approval.util.SendEmailUtil;
import com.weshareholdings.approval.util.SeqUtil;
import com.weshareholdings.approval.vo.FileApprovalVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snaker.engine.IProcessService;
import org.snaker.engine.IQueryService;
import org.snaker.engine.ITaskService;
import org.snaker.engine.core.Execution;
import org.snaker.engine.core.SnakerEngineImpl;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApprovalApplicationTests {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProOptMapper proOptMapper;
    @Autowired
    private SysSeqMapper sysSeqMapper;
    @Autowired
    private SeqUtil seqUtil;
    @Autowired
    private IQueryService iQueryService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendEmailUtil sendEmailUtil;

    @Autowired
    private IProcessService iProcessService;
    @Autowired
    private SnakerEngineImpl snakerEngine;
    @Autowired
    private ITaskService iTaskService;
    @Autowired
    private ProAttachTaskMapper proAttachTaskMapper;

    @Test
    public void contextLoads() {
//        String filePath = "E:\\data.xlsx";
//        File file = new File(filePath);
//        this.readExcel(file);
        //sendEmailUtil.sendEmail("ling.li@weshareholdings.com","1538516018@qq.com,v_wangsiyuan@weshareholdings.com","test","测试邮件发送.时间：" + new Date());
    }

    public String readExcel(File file) {
        // 判断文件是否合法
        validateExcel(file);
        //读取流
        List<User> userList = new ArrayList<>();
        try {
            String filePath = file.getAbsolutePath();
            InputStream is = new FileInputStream(filePath);

            Workbook workbook = new XSSFWorkbook(is);
            // excel的页签数量
            int sheet_size = workbook.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                Sheet sheet = workbook.getSheetAt(index);
                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    User user = new User();
                    for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                        String contents = sheet.getRow(i).getCell(j).getStringCellValue();
                        switch (j) {
                            case 0:
                                user.setFullName(contents);
                                break;
                            case 1:
                                user.setEmail(contents);
                                user.setUserName(contents.replaceAll("@weshareholdings.com", ""));
                                break;
                            default:
                                break;
                        }
                    }
                    userMapper.insertUser(user);
                }
            }
            System.out.println(JSON.toJSONString(userList));
            return JSON.toJSONString(userList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查文件是否合法
     *
     * @param file
     * @return
     */
    public boolean validateExcel(File file) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            if (file == null || !file.exists()) {
                throw new RuntimeException("文件不存在");
            }
            return true;
        } else {
            throw new RuntimeException("文件格式错误");
        }
    }

  /*  public void contextLoads() {

       System.out.println(VoteTask.get("meetApproval"));
        *//*int step;
        String result = proOptMapper.getStep(267);
        if(StringUtils.isBlank(result)){
            step = 1;
        }else{
            step = Integer.valueOf(result)+1;
        }
        System.out.println(step);*//*

        // projectMapper.deleteAttachByProId("12345");
       // projectMapper.selectAttachList()
//        List<Attach>  attachList = projectMapper.queryAttactByProId("12345");
//        List<Map> attachMap = new ArrayList<>();
       *//* for(int i =0; i<3;i++){
            Map map = new HashMap();
            map.put("fileName", i);
            map.put("filePath", i);
            map.put("id", i);
            map.put("proId",12345);
            map.put("realFileName", i);
            attachMap.add(map);
        }*//*
        // 未改动情况：同时为null、同时size为空、值相等
       *//* if((attachList == null && attachMap == null) || (attachList.size()==0 &&  attachMap.size() == 0)){
            System.out.println("未更改");
        }else if(attachList.size() == attachMap.size()){
            for(int i = 0; i < attachList.size(); i++){
                if(attachList.get(i).getId() != (Integer.valueOf((Integer) attachMap.get(i).get("id")))){
                    System.out.println("已更改");
                    break;
                }
            }
        }else{
            System.out.println("已更改");
        }*//*
        // projectMapper.batchInsertAttach(attachMap);

       *//* List<String> list = new ArrayList();
//        list.add("74");
//        list.add("75");
        List<Attach> attachList = projectMapper.selectAttachList(list);
        System.out.println(attachList);

        projectMapper.batchInsertAttachCopy(attachList);*//*
//        QueryFilter queryFilter = new QueryFilter();
//       //  List<HistoryTask> historyTasks =iQueryService.getHistoryTasks(queryFilter.setOrderId("111").setState(0).setName("222"));
//        HistoryTask historyTask = new HistoryTask();
//        historyTask.setOrderId("111");
//        historyTask.setTaskName("111");
//        historyTask.setTaskState(0);
//        taskMapper.selectAll(historyTask);
    }*/


}
