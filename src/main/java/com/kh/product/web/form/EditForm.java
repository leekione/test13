package com.kh.product.web.form;

import lombok.Data;

@Data
public class EditForm {
    private Long pId;
    private String pname;
    private Integer count;
    private Integer price;
}
