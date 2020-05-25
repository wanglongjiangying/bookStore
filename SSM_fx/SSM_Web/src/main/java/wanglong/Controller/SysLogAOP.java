package wanglong.Controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import wanglong.domain.SysLog;
import wanglong.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysLogAOP {

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;

    private Class clazz; //执行的类
    private Method method;//执行的方法
    private Date visitedTime;//访问时间


    @Pointcut("execution( * wanglong.Controller.*.*(..))")
    public void pt1(){}

    /**
     * 获取了访问的方法,时间,类
     * @param joinPoint
     * @throws Exception
     */
    @Before("pt1()")
    public void dobefore(JoinPoint joinPoint)throws Exception{

        visitedTime=new Date();//访问时间

        clazz= joinPoint.getTarget().getClass();//当前执行的类

        String methodName = joinPoint.getSignature().getName();//获取访问的方法名

        Object[] args = joinPoint.getArgs();//获取访问的方法的参数

        if(args==null||args.length==0){//无参数的方法
            method=clazz.getMethod(methodName);
        }else{//有参数的方法
            Class[] classArgs=new Class[args.length];
            for(int i=0;i<classArgs.length;i++){
                classArgs[i]=args[i].getClass();//获取参数的类对象
            }
           method=clazz.getMethod(methodName,classArgs);
        }
    }

    /**
     * url,用户名，ip,执行时常
     * @throws Exception
     */
    @After("pt1()")
    public void doAfter(JoinPoint joinPoint)throws Exception{
        Date endTime=new Date();

        long time=endTime.getTime()-visitedTime.getTime();//执行时常

        if(clazz!=null&&method!=null&&clazz!=SysLogAOP.class&&clazz!=SysLogController.class){

            //获取类上的RequestMapping的值
            RequestMapping classRequestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classRequestMapping!=null) {
                String[] classValue = classRequestMapping.value();

                //获取方法上的RequestMapping的值
                RequestMapping methodrequestMapping = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if(methodrequestMapping!=null) {

                    String url="";
                    String[] methodValue = methodrequestMapping.value();
                    url=classValue[0]+methodValue[0];

                    //从Session对象中获取User
                   // String userNAME =(String)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    //获取用户名
                    SecurityContext securityContext= SecurityContextHolder.getContext();
                    User user=(User)securityContext.getAuthentication().getPrincipal();
                    String username=user.getUsername();


                    //获取IP
                    String ip = request.getRemoteAddr();

                    SysLog sysLog=new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setVisitTime(visitedTime);
                    sysLog.setUrl(url);
                    sysLog.setMethod("类："+clazz.getName()+" 方法："+method.getName());
                    sysLog.setUsername(username);
                    sysLog.setIp(ip);

                    //保存到数据库
                    sysLogService.save(sysLog);


                }


            }
        }




    }
}
