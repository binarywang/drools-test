package com.binarywang.model.fact;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/5/25.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Getter
@Setter
public class AddressCheckResult {

  /**
   * true:通过校验；false：未通过校验
   */
  private boolean postCodeResult = false;
}
