package com.xc.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解注入对象，缩放，字体大小控制等功能
 * 
 * @author xxb
 * @version v1.0 创建时间：2016年1月21日 上午8:57:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resize {
	/**
	 * 是否使用view 适配
	 * 
	 */
	boolean enable() default true;

	/**
	 * 是否是以最小化缩放
	 * 
	 */
	boolean isMin() default true;

	/**
	 * 是否使用字体适配
	 * 
	 */
	boolean textEnable() default true;

	/**
	 * 适配字体的大小 px值
	 * 
	 */
	int textSize() default -1;
	/**
	 * 如果需要自动注入,需要传入view id 并且 context 需要传入一个activity  只支持activity
	 */
	int id() default -1;
	
	/**
	 * 是否使用id 注入
	 */
	boolean idEnable() default true;
	/**
	 * 如果传入的viewholder 是  onClickListener则根据true设置 
	 * @return
	 */
	boolean onClick() default false;
	/**
	 * 当isMin 为false时 。  确认margin需要按照相同方式计算
	 */
	boolean isMargin() default false;

}
