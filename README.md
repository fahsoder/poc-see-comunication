
# Comunicação entre Frontend e Backend com Server-Sent Events (SSE)
## Descrição
Este projeto demonstra como implementar a comunicação em tempo real entre o frontend (React) e o backend (Spring Boot) usando Server-Sent Events (SSE). O SSE é uma tecnologia baseada em HTTP que permite ao servidor enviar eventos em tempo real para o cliente, sem que o cliente precise fazer novas requisições.

## Visão Geral
#### Backend (Spring Boot)
No lado do backend, o Spring Boot fornece suporte para SSE através do `SseEmitter`. O servidor mantém uma conexão aberta com o cliente e envia dados em tempo real sempre que necessário. Isso é ideal para aplicações que precisam de atualizações contínuas, como notificações, feeds de dados ou relatórios em progresso.

#### Frontend (React)
No frontend, o React usa a API EventSource para se conectar ao endpoint SSE no servidor. A cada evento enviado pelo servidor, o cliente processa a mensagem e pode atualizar a interface do usuário conforme necessário.

## Instalando e Executando o Projeto
#### Backend: Iniciar o servidor Spring Boot:
Para instalar as dependências e rodar o backend, use o Maven. No diretório do projeto `api`, execute:

```bash
    mvn clean install
    mvn spring-boot:run
```


#### Backend: Iniciar o servidor React:
Para instalar as dependências e rodar o frontend, use o npm. No diretório do projeto `app`, execute:

```bash
    npm install
    npm run start
```

## Como Funciona a Comunicação?
O backend (Spring Boot) mantém uma conexão aberta com o cliente usando Server-Sent Events (SSE).

O frontend (React) se conecta a esse endpoint através da API EventSource e recebe atualizações em tempo real.

Sempre que o backend gera um novo evento (ex: conclusão de uma etapa do relatório), ele envia esses eventos para o cliente, que pode atualizá-los dinamicamente na interface do usuário.

A conexão é mantida aberta enquanto o processo de geração do relatório ou outra tarefa continua. Se necessário, o servidor pode enviar eventos a cada intervalo de tempo ou em resposta a eventos específicos.


## Benefícios do SSE
Simplicidade: SSE é uma solução simples para comunicação unidirecional do servidor para o cliente.

Eficiência: Não há necessidade de polling constante. O servidor envia os eventos conforme necessário.

Desempenho: SSE usa HTTP padrão e não requer protocolos especiais como WebSockets, o que facilita a integração em servidores e firewalls.


## Conclusão
A comunicação via SSE é uma excelente escolha para aplicações que precisam de atualizações em tempo real do servidor para o cliente sem a sobrecarga de requisições contínuas. Este exemplo básico demonstra como configurar o SSE no Spring Boot e no React para criar uma comunicação eficiente e em tempo real.