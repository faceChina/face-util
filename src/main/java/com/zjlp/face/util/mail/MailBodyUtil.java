package com.zjlp.face.util.mail;

public class MailBodyUtil {

	public static final String MAIL_ONE = "为了保障您的账户安全，请保管好并定期更改登录及支付密码。";
	
	
	
	/**
	 * 登录密码修改成功
	 * @return
	 */
	public static String bodyAuth(String username){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append(_putBr("您的登录密码重置成功！"));
		message.append(_putBr(MAIL_ONE));
		return message.toString();
	}
	
	/**
	 * 支付密码修改成功
	 * @return
	 */
	public static String bodyPay(String username){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append(_putBr("您的支付密码修改成功！"));
		message.append(_putBr(MAIL_ONE));
		return message.toString();
	}
	
	
	/**
	 * 手机号码修改成功
	 * @return
	 */
	public static String bodyPhone(String username){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append(_putBr("您的管家国际绑定手机号码修改成功！"));
		message.append(_putBr(MAIL_ONE));
		return message.toString();
	}
	
	/**
	 * 卖家发货通知
	 * @param order 订单号
	 * @param sendGoodCode 送货单号
	 * @param name 快递公司
	 * @return
	 */
	public static String sendGood(String username,String order,String sendGoodCode,String name){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append("感谢您的订购！您的订单").append(order).append("已发货，将在近期内送达！").append("<br/>");
		message.append("送货单信息：").append("<br/>");
		message.append("送货单号：").append(sendGoodCode).append("<br/>");
		message.append("配送公司名称").append(name).append("<br/>");
		return message.toString();
	}
	
	/**
	 * 订单取消
	 * @param order 订单号
	 * @param date  订单到期时间
	 * @return
	 */
	public static String concelOrder(String username,String order,String date){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append("很抱歉的通知您，您的订单").append(order).append("因超时未支付已于").append(date).append("被系统取消。").append("<br/>");
		message.append("您可以进入订单详情页面查看订单的处理情况或重新购买。").append("<br/>");
		return message.toString();
	}
	
	/**
	 * 订单生成未付款
	 * @param order
	 * @return
	 */
	public static String noPay(String username,String order){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append("您的订单").append(order).append("已成功生成，如您选择网上支付，请尽快付款，感谢您的订购。").append("<br/>");
		return message.toString();
	}
	
	/**
	 * 服务即将到期提醒
	 * @param name 产品名
	 * @param type 产品类型
	 * @param date 产品到期时间
	 * @return
	 */
	public static String serviceDue(String username,String name,String type,String date){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append("您的管家国际").append(name).append("(").append(type).append(")");
		message.append("服务即将到期（到期时间").append(date).append("）。").append("<br/>");
		message.append("为了保障您的服务能正常使用，请尽快完成续费！").append("<br/>");
		return message.toString();
	}


	/**
	 * 服务到期提醒
	 * @param name 产品名
	 * @param type 类型
	 * @return
	 */
	public static String serviceDueed(String username,String name,String type){
		StringBuffer message = new StringBuffer();
		message.append("<h4>尊敬的").append(username).append("您好：</h4>");
		message.append("<p style='line-height:30px;margin-top:3%;'>");
		message.append("您的管家国际").append(name).append("(").append(type).append(")");
		message.append("服务已经到期。").append("<br/>");
		message.append("为了保障您的服务能正常使用，请尽快完成续费！").append("<br/>");
		return message.toString();
	}
	
	
	private static String _putBr(String s){
		StringBuffer message = new StringBuffer();
		message.append(s).append("<br/>");
		return message.toString();
	}
}
