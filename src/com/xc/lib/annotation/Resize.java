package com.xc.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xxb
 * @version v1.0 创建时间：2016年1月21日 上午8:57:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Resize {
	/**
	 * 是否可以使用
	 * @return
	 */
	boolean enable() default true;
	/**
	 * 是否是以最小化缩放
	 * @return
	 */
	boolean isMin() default true;
}
