package com.node.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    @author www.github.com/Acc2020
    @date  2020/4/8
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private String title;
    private String img;
    private String price;
    private String shop;
    // private String collapse_title;
}
