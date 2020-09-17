package club.javafamily.runner.controller;

import club.javafamily.runner.annotation.Audit;
import club.javafamily.runner.annotation.AuditObject;
import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.domain.Customer;
import club.javafamily.runner.enums.ActionType;
import club.javafamily.runner.enums.ResourceEnum;
import club.javafamily.runner.service.CustomerService;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.vo.EmailCustomerVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;

@RestController
@RequestMapping(SecurityUtil.CLIENT_API_VERSION)
public class SecurityApiController {

  @Audit(value = ResourceEnum.Customer, actionType = ActionType.Login)
  @PostMapping("/login")
  public String login(@RequestParam @AuditObject String userName,
                      @RequestParam String password,
                      @RequestParam(required = false) boolean rememberMe)
  {
    Subject currentUser = SecurityUtils.getSubject();

    if(!currentUser.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

      token.setRememberMe(rememberMe);

      try {
        currentUser.login(token);
      } catch (AuthenticationException e) {
        return e.getMessage();
      }
    }

    return null;
  }

  @PostMapping("/signup")
  public void signup(@Valid @ModelAttribute EmailCustomerVO customerVO,
                     HttpServletRequest request)
  {
    String account = customerVO.getAccount();
    Customer user = customerService.getCustomerByAccount(account);

    if(user != null) {
      throw new MessageException("The account has been registered: " + account);
    }

    StringBuilder path = SecurityUtil.getBaseUrl(request);
    path.append(API_VERSION);
    path.append("/customer/verify");

    customerService.signup(customerVO, path.toString());
  }

  @Autowired
  public SecurityApiController(CustomerService customerService)
  {
    this.customerService = customerService;
  }

  private final CustomerService customerService;

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityApiController.class);
}
