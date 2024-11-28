package com.pocsse.app.controller;

import com.pocsse.app.domain.ReportRequest;
import com.pocsse.app.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
//
//    // Endpoint para iniciar a geração de relatório
//    @PostMapping
//    public String generateReport(@RequestBody ReportRequest request) {
//        String reportId = reportService.initiateReportGeneration(request, );
//        log.info("Solicitacao iniciada!");
//        return "Relatório iniciado com ID: " + reportId;
//    }

    @CrossOrigin(origins = "http://localhost:3000")
    // Endpoint para enviar eventos SSE
    @GetMapping(value = "/subscribe/{reportId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToReport(@PathVariable String reportId) {
        SseEmitter emitter = new SseEmitter();
        log.info("Solicitacao de vinculo ao evento iniciada");
        reportService.subscribeToReport(reportId, emitter);
        return emitter;
    }
}
