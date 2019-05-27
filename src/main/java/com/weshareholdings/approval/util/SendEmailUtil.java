package com.weshareholdings.approval.util;

import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.ProInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("mail")
@Slf4j
public class SendEmailUtil {

    @Autowired
    JavaMailSender mailSender;

    @Value("${stmp.account}")
    private String fromEmail;
    @Value("${stmp.subject}")
    private String subject;
    @Value("${stmp.host}")
    private String host;
    @Value("${stmp.password}")
    private String password;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("sendemail")
    public Object sendEmail(String fromEmail, String toEmail, String subject, Map<String, Object> params) {
        try {
            // 组装内容
            String sendContext = assertContext(params);

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            String[] toEmailList = toEmail.split(",");
            InternetAddress[] internetAddressTo = InternetAddress.parse(toEmail);
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            // message.setCc(fromEmail);
            // message.setTo(toEmail);
            message.setFrom(fromEmail);
            message.setSubject(subject);
            message.setText(sendContext);
            this.mailSender.send(mimeMessage);
            saveEmailToSentMailFolder(mimeMessage);
            log.info(fromEmail + " TO " + toEmail + "邮件发送成功");
            return "邮件发送成功！";
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return null;
        }
    }

    private String assertContext( Map<String, Object> params) {
        String sendContext = null;
        String contentType = String.valueOf(params.get("contentType"));
        if (StringUtils.isBlank(contentType)){
            log.error("邮件类型为空！");
            return null;
        }
        switch (contentType){
            case "0":
                sendContext = "【" + params.get("proName") + "】" + "--您好！【审批系统】中有您的待办事项！请在【企业微信-项目审批-待办审批】中及时进行项目审批！";
                break;
            case "1":
                sendContext = "【" + params.get("proName") + "】" + "--您好！审批系统中【" + params.get("proName")+"】项目当前流程已完成，请您及时跟进信息！";
                break;
                default:
                    break;
        }
        return sendContext;
    }

    /**
     * 发送html格式的邮件
     *
     * @param toEmail 接受者
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // String[] toEmailList = toEmail.split(",");
            InternetAddress[] internetAddressTo = InternetAddress.parse(toEmail);
            message.setRecipients(Message.RecipientType.TO, internetAddressTo);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            saveEmailToSentMailFolder(message);
            mailSender.send(message);
            logger.info("发送邮件成功");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 获取用户的发件箱文件夹
     *
     * @param message 信息
     * @param store   存储
     * @return
     */
    private Folder getSentMailFolder(Message message, Store store)
            throws IOException, MessagingException {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", "143");

        // 创建Session实例对象
        Session session = Session.getInstance(props);
        URLName urln = new URLName("imap", host, 143, null,
                fromEmail, password);
        // 创建IMAP协议的Store对象
        store = session.getStore(urln);
        store.connect();

        // 获得发件箱
        Folder folder = store.getFolder("已发送邮件");
        // 以读写模式打开发件箱
        folder.open(Folder.READ_WRITE);

        return folder;
    }

    /**
     * 保存邮件到发件箱
     *
     * @param message 邮件信息
     */
    private void saveEmailToSentMailFolder(Message message) {

        Store store = null;
        Folder sentFolder = null;
        try {
            sentFolder = getSentMailFolder(message, store);
            // 设置已读标志
            message.setFlag(Flags.Flag.SEEN, true);
            sentFolder.appendMessages(new Message[]{message});

            System.out.println("已保存到发件箱...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 判断发件文件夹是否打开如果打开则将其关闭
            if (sentFolder != null && sentFolder.isOpen()) {
                try {
                    sentFolder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            // 判断邮箱存储是否打开如果打开则将其关闭
            if (store != null && store.isConnected()) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendEmail(ProInfo proInfo,List<String> emails){
        if (emails != null && emails.size() > 0) {
            String toEmail = emails.toString().substring(1, emails.toString().length() - 1);
            Map<String, Object> params = new HashMap<>();
            params.put("proName", proInfo.getProName());
            params.put("contentType", BaseConstant.EMAIL_TYPE.TASK_CONTENT.getCode());
            sendEmail(fromEmail, toEmail, subject, params);
        }
    }
}
