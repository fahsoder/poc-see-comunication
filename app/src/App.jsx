import ReportStatus from "./components/ReportStatus";

function App() {
    const reportId = "22"; // ID do relatório de exemplo

    return (
        <div>
            <h1>Monitoramento de Relatório</h1>
            <ReportStatus reportId={reportId} />
        </div>
    );
}

export default App;
