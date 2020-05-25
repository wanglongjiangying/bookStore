package wanglong.Utils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendJMail {

    /**
     *1.stmp服务器地址：stmp.163.com
     * 2.发送邮件的端口号：默认25
     *   3.发件人的账户和密码   *
     * @param email  收件人的邮箱地址
     * @param emailMsg  邮件的内容
     * @return
     */
    final static String smtphost="smtp.163.com";//stmp服务器地址：stmp.163.com
    final static String from="wl13247038396@163.com";//发件人的账户
    final static String username="wl13247038396@163.com";// 发件人的账户
    final static String password="MGQWVXTIENAQVPEE";//授权码

    public static  boolean sendMail(String email,String emailMsg)  {
        String to=email;//
        Properties properties=System.getProperties();
        properties.setProperty("mail.smtp.host",smtphost);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");

        Session session=Session.getInstance(properties);
        session.setDebug(true);

        try {

            //通过session获取MimeMessage对象,相当与一封邮件
            MimeMessage message = new MimeMessage(session);
             //设置发件人
            message.setFrom(new InternetAddress(from));
            //设置邮件主题
            message.setSubject("用户激活");
            //设置邮件内容
            message.setContent((emailMsg),"text/html;charset=utf-8");
            //设置附件
            //message.setDataHandler(dh);
            //获取发送对象
            Transport transport = session.getTransport();
            transport.connect(smtphost,25, username, password);
            //设置收件人地址,并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(to)});
            transport.close();
            return true;

        }catch (MessagingException m){
            m.printStackTrace();
            return false;
        }

    }
}
