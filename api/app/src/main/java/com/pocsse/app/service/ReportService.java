package com.pocsse.app.service;

import com.pocsse.app.domain.ReportRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ReportService {

    private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    // Inicia a geração do relatório
    public String initiateReportGeneration(ReportRequest request, String reportId) {

        // Simula o processo assíncrono de geração de relatório
        sendEvent(reportId, "{\"status\":\"pending\", \"downloadUrl\":\"" + reportId + "\"}");
        executor.submit(() -> generateReport(reportId, request));
        log.info("Solicitacao executada!");
        return reportId;
    }

    // Assina o cliente para receber notificações do relatório
    public void subscribeToReport(String reportId, SseEmitter emitter) {
        emitters.put(reportId, emitter);
        emitter.onCompletion(() -> emitters.remove(reportId));
        emitter.onTimeout(() -> emitters.remove(reportId));
        log.info("Frontend plugado");
        initiateReportGeneration(ReportRequest.builder().name("soder").date("2024-11-29").filter("filter").build(), reportId);
    }

    // Simula a geração de relatório
    private void generateReport(String reportId, ReportRequest request) {
        try {
            // Simula processamento pesado
            Thread.sleep(5000);

            log.info("Relatorio em andamento!");
            // Simula o relatório estar pronto
            sendEvent(reportId, "{\"status\":\"processing step 1\", \"downloadUrl\":\"" + reportId + "\"}");
            Thread.sleep(30000); // Simula mais processamento

            sendEvent(reportId, "{\"status\":\"processing step 2\", \"downloadUrl\":\"" + reportId + "\"}");
            Thread.sleep(30000); // Simula mais processamento
            sendEvent(reportId, "{\"status\":\"processing step 3\", \"downloadUrl\":\"" + reportId + "\"}");
            Thread.sleep(30000); // Simula mais processamento
            sendEvent(reportId, "{\"status\":\"processing step 4\", \"downloadUrl\":\"" + reportId + "\"}");
            Thread.sleep(30000); // Simula mais processamento
            // Envia a URL de download (simulação)
            sendEvent(reportId, "{\"status\":\"ready\", \"downloadUrl\":\"/api/reports/download/" + reportId + "\"}");
            log.info("Evento enviado!");

        } catch (Exception e) {
            log.error("Erro ao enviar o evento!");
            sendEvent(reportId, "{\"status\":\"error\", \"message\":\"Erro ao gerar relatório\"}");
        }
    }

    // Envia um evento SSE
    private void sendEvent(String reportId, String data) {
        SseEmitter emitter = emitters.get(reportId);
        if (emitter != null) {
            try {
                emitter.send(data);
                if (data.contains("ready") || data.contains("error")) {
                    emitter.complete();
                }
            } catch (IOException e) {

                emitter.completeWithError(e);
            }
        }
    }

    // Gera um ID único para o relatório
    private String generateReportId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
