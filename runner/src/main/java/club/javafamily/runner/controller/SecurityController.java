package club.javafamily.runner.controller;

import club.javafamily.runner.common.MessageException;
import club.javafamily.runner.common.model.amqp.RegisterUserInfo;
import club.javafamily.runner.common.service.RedisClient;
import club.javafamily.runner.domain.Customer;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static club.javafamily.runner.util.SecurityUtil.API_VERSION;
import static club.javafamily.runner.util.SecurityUtil.REGISTERED_USER_STORE_PREFIX;

@Controller
public class SecurityController {

  @PostMapping(API_VERSION + "/login")
  public String login(@RequestParam String userName, @RequestParam String password,
                      @RequestParam(required = false) boolean rememberMe) {
    Subject currentUser = SecurityUtils.getSubject();

    if (!currentUser.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

      token.setRememberMe(rememberMe);

      try {
        currentUser.login(token);
      } catch (AuthenticationException e) {
        throw e;
      }
    }

    // redirect index page when login success.
    return "redirect:/";
  }

  @GetMapping("signup")
  public String gotoSignupPage(ModelMap map) {
    EmailCustomerVO customerVO = new EmailCustomerVO();

    map.put("customer", customerVO);

    return "signup";
  }

  @PostMapping(API_VERSION + "/signup")
  public String signup(@Valid @ModelAttribute EmailCustomerVO customerVO,
                       BindingResult bindingResult,
                       HttpServletRequest request)
  {
    List<ObjectError> allErrors = bindingResult.getAllErrors();
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < allErrors.size(); i++) {
      ObjectError error = allErrors.get(i);

      if(error instanceof FieldError) {
        sb.append(((FieldError) error).getField())
           .append(": ")
           .append(error.getDefaultMessage());
      }
      else {
        sb.append(error.getDefaultMessage());
      }

      if(i < allErrors.size() - 1) {
        sb.append("\n");
      }
    }

    if(StringUtils.hasText(sb)) {
      throw new MessageException(sb.toString());
    }

    StringBuilder path = SecurityUtil.getBaseUrl(request);
    path.append(API_VERSION);
    path.append("/customer/verify");

    customerService.signup(customerVO, path.toString());

    // goto login after sign up.
    return "signupSuccess";
  }

  @GetMapping(API_VERSION + "/customer/verify")
  public String verify(String token,
                       String identity,
                       ModelMap modelMap,
                       HttpServletRequest request)
  {
    String key = REGISTERED_USER_STORE_PREFIX + identity;
    RegisterUserInfo info = redisClient.get(key);
    boolean verify = false;

    LOGGER.debug("Getting registered user info is: {}", info);
    StringBuilder redirectLink = SecurityUtil.getBaseUrl(request);

    if(info != null) {
      String realToken = info.getToken();

      if(token.equals(realToken)) {
        Customer user = info.convertModel();
        user.setActive(true);
        customerService.insertCustomer(user);
        redisClient.delete(key);
        LOGGER.debug("Registered user: {}", user);
        verify = true;
        redirectLink.append("/login");
      }
      else {
        redirectLink.append("/signup");
        modelMap.put("reason", "Token is not correct!");
      }
    }
    else {
      redirectLink.append("/signup");
      modelMap.put("reason", "This token is expired!");
    }

    modelMap.put("result", verify);
    modelMap.put("redirectLink", redirectLink);

    return "verifyResult";
  }

  @GetMapping("/error")
  public String gotoErrorPage() {
    return "/error/exception";
  }

  @Autowired
  public SecurityController(CustomerService customerService,
                            RedisClient<RegisterUserInfo> redisClient)
  {
    this.redisClient = redisClient;
    this.customerService = customerService;
  }

  private final CustomerService customerService;
  private final RedisClient<RegisterUserInfo> redisClient;

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
}
