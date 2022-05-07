package com.abs.uploadcsv.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseData {

    private boolean status;
    private List<String> messages = new ArrayList<>();
    private Object payload;
}
