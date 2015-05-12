package com.jlfex.hermes.service.withdraw;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Numbers;

/**
 * 区间提现手续费
 */
@Component("intervalWithdrawFee")
public class IntervalWithdrawFee implements WithdrawFee {

	private static final Pattern PATTERN = Pattern.compile("(\\d+.?\\d*):(\\d+.?\\d*),(\\d+.?\\d*)");
	
	
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.withdraw.WithdrawFee#calcFee(java.math.BigDecimal, java.lang.String)
	 */
	@Override
	public BigDecimal calcFee(BigDecimal amount, String config) {
		for (Interval interval: getIntervals(config)) {
			if (amount.compareTo(interval.max) <= 0) {
				return interval.fee;
			}
		}
		throw new ServiceException("the amount " + amount + " is bigger than the max.", "exception.withdraw.max");
	}
	
	/**
	 * 读取区间
	 */
	protected List<Interval> getIntervals(String config) {
		List<Interval> intervals = new LinkedList<Interval>();
		for (String str: config.split(";")) {
			Matcher matcher = PATTERN.matcher(str);
			if (matcher.find()) {
				BigDecimal min = Numbers.currency(Double.valueOf(matcher.group(1)));
				BigDecimal max = Numbers.currency(Double.valueOf(matcher.group(2)));
				BigDecimal fee = Numbers.currency(Double.valueOf(matcher.group(3)));
				intervals.add(new Interval(min, max, fee));
				Logger.info("init interval '%s:%s,%s'", min, max, fee);
			} else {
				throw new ServiceException("cannot match " + str + ".");
			}
		}
		return intervals;
	}
	
	/**
	 * 区间
	 */
	public class Interval {
		
		/** 最小值 */
		private BigDecimal min;
		
		/** 最大值 */
		private BigDecimal max;
		
		/** 费用 */
		private BigDecimal fee;

		/**
		 * 构造函数
		 */
		public Interval() {}
		
		/**
		 * 构造函数
		 * 
		 * @param min
		 * @param max
		 * @param fee
		 */
		public Interval(BigDecimal min, BigDecimal max, BigDecimal fee) {
			this.min = min;
			this.max = max;
			this.fee = fee;
		}
		
		/**
		 * 读取最小值
		 * 
		 * @return
		 * @see #min
		 */
		public BigDecimal getMin() {
			return min;
		}

		/**
		 * 设置最小值
		 * 
		 * @param min
		 * @see #min
		 */
		public void setMin(BigDecimal min) {
			this.min = min;
		}

		/**
		 * 读取最大值
		 * 
		 * @return
		 * @see #max
		 */
		public BigDecimal getMax() {
			return max;
		}

		/**
		 * 设置最大值
		 * 
		 * @param max
		 * @see #max
		 */
		public void setMax(BigDecimal max) {
			this.max = max;
		}

		/**
		 * 读取费用
		 * 
		 * @return
		 * @see #fee
		 */
		public BigDecimal getFee() {
			return fee;
		}

		/**
		 * 设置费用
		 * 
		 * @param fee
		 * @see #fee
		 */
		public void setFee(BigDecimal fee) {
			this.fee = fee;
		}
	}
}
