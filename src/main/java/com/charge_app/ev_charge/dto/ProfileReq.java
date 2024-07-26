package com.charge_app.ev_charge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileReq {
    private String image;
    private String note;
}
