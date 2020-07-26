package club.javafamily.runner.common;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
   /**
    * 响应码
    */
   private String code;

   /**
    * 响应消息
    */
   private String message;

   /**
    * 业务数据
    */
   private T data;

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public T getData() {
      return data;
   }

   public void setData(T data) {
      this.data = data;
   }

   /**
    * @param code 错误代码
    * @param message 错误消息
    * @param data data
    */
   @SuppressWarnings({"unchecked", "rawtypes"})
   public static <T> ResponseResult build(String code, String message, T data) {
      ResponseResult result = new ResponseResult();
      result.setCode(code);
      result.setMessage(message);
      result.setData(data);

      return result;
   }

   @SuppressWarnings("rawtypes")
   public static ResponseResult build(String code, String message) {
      return build(code, message, null);
   }
}
