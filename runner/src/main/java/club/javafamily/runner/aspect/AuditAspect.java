package club.javafamily.runner.aspect;

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.model.data.CommonsKVModel;
import club.javafamily.runner.domain.Log;
import club.javafamily.commons.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.*;
import club.javafamily.runner.util.WebMvcUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Aspect
public class AuditAspect {

   @Autowired
   public AuditAspect(LogService logService,
                      UserHandler userHandler)
   {
      this.logService = logService;
      this.userHandler = userHandler;
   }

   @Pointcut("@annotation(club.javafamily.runner.annotation.Audit) && within(club.javafamily.runner..*)")
   public void audit() {
   }

   @Around("audit()")
   public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
      Log log = null;

      try {
         MethodSignature signature = (MethodSignature) pjp.getSignature();
         Audit annotation = signature.getMethod().getAnnotation(Audit.class);
         ResourceEnum resource = annotation.value();
         ActionType actionType = annotation.actionType();
         String objectName = annotation.objectName();

         Annotation[][] paramAnnotations
            = signature.getMethod().getParameterAnnotations();
         List<CommonsKVModel<Integer, String>> objectNames = new ArrayList<>();

         for(int i = 0; i < paramAnnotations.length; i++) {
            for(int j = 0; j < paramAnnotations[i].length; j++) {
               if(paramAnnotations[i][j] instanceof AuditObject) {
                  AuditObject auditObject = (AuditObject) paramAnnotations[i][j];
                  Object arg = pjp.getArgs()[i];

                  if(auditObject.value().isEmpty()) {
                     objectName = String.valueOf(arg);
                  }
                  else {
                     StandardEvaluationContext context = new StandardEvaluationContext(arg);
                     Expression expr = spelParser.parseExpression(auditObject.value());
                     objectName = expr.getValue(context, String.class);
                  }

                  objectNames.add(new CommonsKVModel<>(auditObject.order(), objectName));
               }
            }
         }

         if(objectNames.size() > 1) {
            objectNames.sort(Comparator.comparingInt(CommonsKVModel::getKey));
            objectName = objectNames.stream()
               .map(CommonsKVModel::getValue)
               .collect(Collectors.joining("/"));
         }

         log = new Log();
         log.setDate(new Date());
//      log.setCustomer(getAuditUser());// set in insert log for login action
         log.setResource(resource.getLabel() + ": " + objectName);
         log.setAction(actionType.getLabel());
         log.setIp(WebMvcUtil.getIP());
      }
      catch (Exception ignore) {
         LOGGER.warn("Build Log Failed!");
      }

      Object result;

      try {
         result = pjp.proceed();
      }
      catch(Throwable throwable) {
         if(log != null) {
            log.setMessage("Execute Failed: " + throwable.getMessage());
         }

         throw throwable;
      }
      finally {
         if(log != null) {
            log.setCustomer(getAuditUser());
            logService.insertLog(log);
         }
      }

      return result;
   }

   private String getAuditUser() {
      return userHandler.getAuditUser();
   }

   private final LogService logService;
   private final UserHandler userHandler;
   private final SpelExpressionParser spelParser = new SpelExpressionParser();

   private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
}
