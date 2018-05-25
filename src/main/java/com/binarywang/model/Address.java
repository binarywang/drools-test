package com.binarywang.model;

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
public class Address {
  private String postcode;

  private String street;

  private String state;
}
