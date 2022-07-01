package com.reliable.yang.handler;
import com.reliable.yang.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对加了controller注解的方法进行异常的拦截处理
 * AOP
 * @author Administrator
 * @date 2022-06-21 19:54
 */
@ControllerAdvice
public class AllExceptionHandler {
	//进行异常处理，处理Exception.class的异常
	@ExceptionHandler(Exception.class)
	@ResponseBody //返回json数据
	public Result doException(Exception ex){
		ex.printStackTrace();
		return Result.fail(-999,"您访问的页面出错了");
	}
}