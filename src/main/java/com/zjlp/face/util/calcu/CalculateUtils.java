package com.zjlp.face.util.calcu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 计算操作�?
 * 
 * @ClassName: CalculateUtils
 * @Description: (常用的计算公�?
 * @author Administrator
 * @date 2014�?�?1�?上午10:12:26
 */
public class CalculateUtils {

    private static DecimalFormat df = new DecimalFormat("0.00");
    
    
    public CalculateUtils(){
    }
    
	public static Double converDiscountRate(Integer discountRate) {
		BigDecimal bd = new BigDecimal(discountRate);
	    BigDecimal bd2 = new BigDecimal(100);
	    return bd.divide(bd2).doubleValue();
	}


    /**
     * 计算收入 计算公式：收�?= 结转金额 / (1-佣金�?
     */
    public static Long getIncome(String priceCarryOver, String commissionRates){
        Long carryOver = converYuantoPenny(priceCarryOver);
        double rates = 1 - Double.valueOf(commissionRates);
        BigDecimal bd = new BigDecimal(carryOver);
        BigDecimal bd2 = new BigDecimal(rates);
        double cash = bd.divide(bd2, 0, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal b = new BigDecimal(cash);
        return b.longValue();
    }

    /**
     * 计算佣金 计算公式 ：佣�?= 收入金额 - 结转金额
     */
    public static Long getCommission(String priceCarryOver, String commissionRates){
        Long income = getIncome(priceCarryOver, commissionRates);
        BigDecimal bd = new BigDecimal(income);
        BigDecimal bd2 = new BigDecimal(commissionRates);
        BigDecimal commission = bd.multiply(bd2);
        return commission.longValue();
    }

    /**
     * 计算金额 计算公式�?单价*数量 = 价格
     * 
     * @Title: get
     * @param unitPrice
     *            单价
     * @param quantity
     *            数量
     * @return Long 价格
     * @date 2014�?�?1�?上午10:11:31
     * @author dzq
     */
    public static Long get(long unitPrice, int quantity){
        return unitPrice * Long.valueOf(quantity);
    }
    
    
    /**
     * 计算折扣金额 去小�?
     * 
     * @Title: getDiscountPrice
     * @Description: (这里用一句话描述这个方法的作�?
     * @param price
     * @param discount
     * @return
     * @date 2014�?�?8�?下午5:47:45
     * @author Administrator
     */
    public static Long getDiscountPrice(Long price, Integer discount){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd.multiply(bd2).divide(bd3).doubleValue();
        return (long) Math.floor(dou);
    }

    /**
     * 计算折扣金额 向上�?
     * 
     * @Title: getDiscountPrice
     * @Description: (这里用一句话描述这个方法的作�?
     * @param price
     * @param discount
     * @return
     * @date 2014�?�?8�?下午5:47:45
     * @author Administrator
     */
    public static Long getDiscountPriceCeil(Long price, Integer discount){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd.multiply(bd2).divide(bd3).doubleValue();
        return (long) Math.ceil(dou);
    }
    
    
    /**
     * 计算折扣金额 向下�?
     * 
     * @Title: getDiscountPrice
     * @Description: (这里用一句话描述这个方法的作�?
     * @param price
     * @param discount
     * @return
     * @date 2014�?�?8�?下午5:47:45
     * @author Administrator
     */
	public static Long getDiscountPriceDown(Long price, Integer discount) {
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd.multiply(bd2).divide(bd3).doubleValue();
        return (long) Math.floor(dou);
	}

    /**
     * 计算金额 计算公式�?单价*数量 = 价格
     * 
     * @Title: get
     * @param unitPrice
     *            单价
     * @param quantity
     *            数量
     * @return Long 价格
     * @date 2014�?�?1�?上午10:11:31
     * @author dzq
     */
    public static Long get(long unitPrice, Long quantity){
        return unitPrice * Long.valueOf(quantity);
    }

    /**
     * 累加总额
     * 
     * @Title: getSum
     * @Description: (这里用一句话描述这个方法的作�?
     * @param sum
     * @param unitPrice
     * @param quantity
     * @return
     * @date 2014�?�?1�?上午10:43:58
     * @author Administrator
     */
    public static Long getSum(long sum, long unitPrice, int quantity){
        return sum += get(unitPrice, quantity);
    }
    

    /**
     * 累加总额
     * 
     * @Title: getSum
     * @Description: (这里用一句话描述这个方法的作�?
     * @param sum
     * @param unitPrice
     * @param quantity
     * @return
     * @date 2014�?�?1�?上午10:43:58
     * @author Administrator
     */
    public static Long getSum(long sum, long unitPrice, long quantity){
        return sum += get(unitPrice, quantity);
    }

    /**
     * 累加
     * 
     * @Title: getSum
     * @Description: (这里用一句话描述这个方法的作�?
     * @param sum
     * @param number
     *            当前
     * @return
     * @date 2014�?�?1�?上午10:42:04
     * @author dzq
     */
    public static Long getSum(Long sum, Long number){
        if(null == sum){
            sum = 0L;
        }
        if(null == number){
            number = 0L;
        }
        return sum += number;
    }
    
    /**
     * 累加
     * 
     * @Title: getSum 
     * @Description: (这里用一句话描述这个方法的作�? 
     * @param longs
     * @return
     * @date 2015�?�?4�?上午10:25:49  
     * @author lys
     */
    public static Long getMulSum(Long...longs) {
    	if(null == longs){
    		return 0L;
    	}
    	Long sum = 0L;
    	for (Long l : longs) {
			if(null ==l )continue;
			sum += l;
		}
    	return sum;
    }
    
    /**
     * 累加
     * 
     * @Title: getSum
     * @Description: (这里用一句话描述这个方法的作�?
     * @param sum
     * @param number
     *            当前
     * @return
     * @date 2014�?�?1�?上午10:42:04
     * @author dzq
     */
    public static Integer getSum(Integer sum, Integer number){
        if(null == sum){
            sum = 0;
        }
        if(null == number){
            number = 0;
        }
        return sum += number;
    }

    /**
     * 金额差额计算 差额 = 原金�?- 现金�?
     * 
     * @Title: getDifference
     * @Description: (这里用一句话描述这个方法的作�?
     * @param oldVal
     * @param newVal
     * @return
     * @date 2014�?�?7�?上午11:08:14
     * @author dzq
     */
    public static Long getDifference(Long oldVal, Long newVal){
        BigDecimal bd = new BigDecimal(oldVal);
        BigDecimal bd2 = new BigDecimal(newVal);
        BigDecimal difference = bd.subtract(bd2);
        return difference.longValue();
    }

    /**
     * 金额差额计算 现金�? 原金�?+差额
     * 
     * @Title: getDifference
     * @Description: (这里用一句话描述这个方法的作�?
     * @param oldVal
     *            原�?
     * @param difference
     *            差额：可正可�?
     * @return
     * @date 2014�?�?7�?上午11:08:14
     * @author dzq
     */
    public static Long getNewVal(Long oldVal, Long difference){
        BigDecimal bd = new BigDecimal(oldVal);
        BigDecimal bd2 = new BigDecimal(difference);
        BigDecimal newVal = bd.add(bd2);
        return newVal.longValue();
    }

    /**
     * 库存扣除计算 期初存量-本期�?期末结存
     * 
     * @Title: getDifference
     * @Description: (这里用一句话描述这个方法的作�?
     * @param oldVal
     * @param newVal
     * @return
     * @date 2014�?�?7�?上午11:08:14
     * @author dzq
     */
    public static Long getBalanceStocks(Long currentStocks, Long salesStocks){
        BigDecimal bd = new BigDecimal(currentStocks);
        BigDecimal bd2 = new BigDecimal(salesStocks);
        BigDecimal balance = bd.subtract(bd2);
        return balance.longValue();
    }

    /**
     * 库存扣除计算 期初存量-本期�?期末结存
     * 
     * @Title: getDifference
     * @Description: (这里用一句话描述这个方法的作�?
     * @param oldVal
     * @param newVal
     * @return
     * @date 2014�?�?7�?上午11:08:14
     * @author dzq
     */
    public static Long getBalanceStocks(Long currentStocks, Integer salesStocks){
        BigDecimal bd = new BigDecimal(currentStocks);
        BigDecimal bd2 = new BigDecimal(salesStocks);
        BigDecimal balance = bd.subtract(bd2);
        return balance.longValue();
    }

    /**
     * 计算金额百分�?向上�?
     * 
     * @Title: getPercentagePriceUp
     * @Description: (用于计算商品折扣�?
     * @param price
     * @param discount
     * @return
     * @date 2014�?�?8�?下午5:47:45
     * @author Administrator
     */
    public static Long getPercentagePriceUp(Long price, String discount){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd.multiply(bd2).divide(bd3).doubleValue();
        return (long) Math.ceil(dou);
    }

    /**
     * 计算金额百分�?向下�?
     * 
     * @Title: getPercentagePriceDown
     * @Description: (用于计算佣金，手续费)
     * @param price
     * @param discount
     * @return
     * @date 2014�?�?5�?上午11:36:01
     * @author Administrator
     */
    public static Long getPercentagePriceDown(Long price, String discount){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        return bd.multiply(bd2).divide(bd3).longValue();
    }
    
    /**
     * 计算金额百分�? 四舍五入
     * 
     * @Title: getFeeHalfUp 
     * @Description: (这里用一句话描述这个方法的作�? 
     * @param price 金额
     * @param discount 百分�?
     * @return
     * @date 2014�?0�?7�?下午2:26:31  
     * @author Administrator
     */
    public static Long getFeeHalfUp(Long price, String discount) {
    	BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(discount);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd.multiply(bd2).divide(bd3).doubleValue();
    	return Math.round(dou);
    }

    /**
     * 根据比例计算手续�?单价*手续�?总价
     * 
     * @Title: getFeeUp
     * @Description: (这里用一句话描述这个方法的作�?
     * @param price
     * @param fee
     * @param totalPrice
     * @return
     * @date 2014�?�?1�?上午11:10:03
     * @author Administrator
     */
    public static Long getFeeUp(Long price, Long fee, Long totalPrice){
        BigDecimal bd = new BigDecimal(price);
        BigDecimal bd2 = new BigDecimal(fee);
        BigDecimal bd3 = new BigDecimal(totalPrice);
        Double dou = bd.multiply(bd2).divide(bd3, 2, RoundingMode.CEILING).doubleValue();
        return (long) Math.ceil(dou);
    }
    
    
    /**
     * 计算收入 计算公式：折�?  = (1  - (进货�?/ 不打折零售价)) * 100
     */
    public static int getDiscount(Long inPrice, Long outPrice){
        BigDecimal in = new BigDecimal(inPrice);
        BigDecimal out = new BigDecimal(outPrice);
        BigDecimal result = BigDecimal.ONE.subtract(in.divide(out, 2))
        		.multiply(new BigDecimal(100));
        return result.intValue();
    }
    
    
    
    /**
     * 计算折扣
     * @Title: getGoldPurchase 
     * @Description: (这里用一句话描述这个方法的作�? 
     * @param salesPrice
     * @param goldPrice
     * @return
     * @date 2015�?�?9�?下午4:32:38  
     * @author fjx
     */
    public static int getGoldPurchase(Long salesPrice,Long goldPrice){
        BigDecimal bd = new BigDecimal(salesPrice);
        BigDecimal bd2 = new BigDecimal(goldPrice);
        BigDecimal bd3 = new BigDecimal(100);
        Double dou = bd2.multiply(bd3).divide(bd, 2, RoundingMode.CEILING).doubleValue();
        return (int) Math.ceil(dou);
    }
    
    
    
    
    
    

    /**
     * 单位换算【元转分�?
     * 
     * @param Yuan
     * @return
     */
    public static Long converYuantoPenny(String yuan){
        BigDecimal bd = new BigDecimal(yuan);
        BigDecimal bd2 = new BigDecimal(100);
        BigDecimal cash = bd.multiply(bd2);
        return cash.longValue();
    }

    /**
     * 单位换算【分转元�?
     * 
     * @param panny
     * @return
     */
    public static String converPennytoYuan(Long panny){
        BigDecimal bd = new BigDecimal(panny);
        return bd.divide(new BigDecimal(100)).toString();
    }

    /**
     * 单位换算【分转元�?
     * 
     * @param panny
     * @return
     */
    public static Long converPennytoYuantoLong(Long panny){
        BigDecimal bd = new BigDecimal(panny);
        return bd.divide(new BigDecimal(100)).longValue();
    }

    /**
     * 单位换算【元转厘�?
     * 
     * @param Yuan
     * @return
     */
    public static Long converYuanToPct(String yuan){
        BigDecimal bd = new BigDecimal(yuan);
        BigDecimal bd2 = new BigDecimal(1000);
        BigDecimal cash = bd.multiply(bd2);
        return cash.longValue();
    }

    /**
     * 单位换算【厘转元�?
     * 
     * @param pct
     * @return
     */
    public static String converPctToYuan(Long pct){
        BigDecimal bd = new BigDecimal(pct);
        return bd.divide(new BigDecimal(1000)).toString();
    }

    /**
     * 单位换算【分转厘�?
     * 
     * @param panny
     * @return
     */
    public static Long converPennyToPct(Long panny){
        BigDecimal bd = new BigDecimal(panny);
        BigDecimal bd2 = new BigDecimal(10);
        BigDecimal cash = bd.multiply(bd2);
        return cash.longValue();
    }

    /**
     * 单位换算【厘转分�?
     * 
     * @param pct
     * @return
     */
    public static String converPctToPenny(Long penny){
        BigDecimal bd = new BigDecimal(penny);
        return bd.divide(new BigDecimal(10)).toString();
    }

    /**
     * @Description: 分转成元，保�?位小�?
     * @param fen
     * @return
     * @date: 2014�?�?1�?下午8:42:51
     * @author: zyl
     */
    public static String converFenToYuan(Long fen){
        return df.format((fen == null ? 0 : fen) / 100.0);
    }

}
