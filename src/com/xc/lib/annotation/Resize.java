package com.xc.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解使用缩放
 * @author xxb
 * @version v1.0 创建时间：2016年1月21日 上午8:57:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resize {
	/**
	 * 是否可以使用
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
	boolean textEnable() default false;

	/**
	 * 适配字体的大小  px值
	 * 
	 */
	int textSize() default -1;

}
