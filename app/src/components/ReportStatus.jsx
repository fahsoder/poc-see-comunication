import { useState, useEffect } from "react";

function ReportStatus({ reportId }) {
    const [status, setStatus] = useState("processing");
    const [downloadUrl, setDownloadUrl] = useState(null);

    useEffect(() => {
        const eventSource = new EventSource(`http://localhost:8080/api/reports/subscribe/${reportId}`);
        
        eventSource.onmessage = (event) => {
            console.log("Mensagem recebida:", event.data);
            const data = JSON.parse(event.data);
            setStatus(data.status);
            if (data.status === "ready") {
                setDownloadUrl(data.downloadUrl);
                eventSource.close(); // Fecha a conexão quando o relatório está pronto
            }
        };

        eventSource.onerror = () => {
            console.error("Erro na conexão SSE");
            eventSource.close();
        };

        return () => eventSource.close(); // Limpa a conexão ao desmontar o componente
    }, [reportId]);

    return (
        <div>
            <p>Status: {status}</p>
            {downloadUrl && (
                <a href={downloadUrl} download>
                    Baixar Relatório
                </a>
            )}
        </div>
    );
}

export default ReportStatus;
