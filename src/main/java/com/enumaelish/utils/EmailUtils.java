package com.enumaelish.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

/**
 * 发邮件工具类， 用的QQ邮箱，其他的请自行更改Properties配置
 */
public class EmailUtils {
    private static final String SENDUSER = "99246255@qq.com";
    private static final String AUTHCODE = "xx";// qq邮箱授权码，不知道的看http://blog.csdn.net/u013871100/article/details/52649767

    /**
     * 发送纯文本邮件
     * @param toUser 接收人
     * @param subject 主题
     * @param content 文本内容
     */
    public static void sendEmail(String toUser,String subject,String content) {
        send(toUser, subject, content, null);
    }
    /**
     * 发送带附件的邮件
     * @param toUser 接收人
     * @param subject 主题
     * @param content 文本内容
     * @param fileDataSources 附件
     */
    public static void sendEmailWithFile(String toUser,String subject,String content, List<FileDataSource> fileDataSources) {
        send(toUser, subject, content, fileDataSources);
    }

    private static void send(String toUser,String subject,String content, List<FileDataSource> fileDataSources){
        try{
            Properties props = System.getProperties(); // 获取系统属性对象
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            /* 开启 SSL 加密（QQ 邮箱需要）
             * 其它邮箱服务，比如 163、新浪邮箱不需要 SSL 加密。
             */
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(props);
            Message msg;
            if(fileDataSources == null){
                msg = createTextMail(session, toUser, subject, content);
            }else{
                msg = createFileMail(session, toUser, subject, content, fileDataSources);
            }
            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", SENDUSER, AUTHCODE);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Message createTextMail(Session session, String toUser, String subject, String content) throws  Exception{
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setText(content);
        msg.setFrom(new InternetAddress(SENDUSER));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
        return msg;
    }
    private static Message createFileMail(Session session, String toUser, String subject, String content, List<FileDataSource> fileDataSources)throws  Exception{
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress(SENDUSER));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
        // 邮件标题
        message.setSubject(subject);
        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html;charset=UTF-8");
        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(mimeBodyPart);
        // 创建邮件附件
        for(int i=0;i<fileDataSources.size();i++){
            MimeBodyPart attach = new MimeBodyPart();
            DataHandler dh = new DataHandler(fileDataSources.get(i));
            attach.setDataHandler(dh);
            attach.setFileName(dh.getName());
            mp.addBodyPart(attach);
        }
        mp.setSubType("mixed");
        message.setContent(mp);
        message.saveChanges();
        //store(message); // 将创建的Email写入盘存储
        return message;
    }
    public static void main(String[] args) {
//        sendEmail("99246255@qq.com", "title", "content");
//        ArrayList<FileDataSource> fileDataSources = new ArrayList<>();
//        fileDataSources.add(new FileDataSource("E:\\11.txt"));
//        sendEmailWithFile("99246255@qq.com", "title", "content", fileDataSources);
        String a = "https://hz.lianjia.com/ershoufang/12";
        if(a.matches("https://[^\\s]*.lianjia.com/ershoufang/")){
            System.out.println("true");
        }
    }
}
