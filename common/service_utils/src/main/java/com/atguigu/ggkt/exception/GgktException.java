package com.atguigu.ggkt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GgktException extends RuntimeException {
    private Integer code;
    private String msg;
}
