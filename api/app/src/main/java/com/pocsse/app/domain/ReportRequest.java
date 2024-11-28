package com.pocsse.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReportRequest {
    private String name;
    private String date;
    private String filter;
}
