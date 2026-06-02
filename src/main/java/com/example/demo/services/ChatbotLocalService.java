package com.example.demo.services;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Chatbot local baseado em regras e palavras-chave.
 * Não depende de nenhuma API externa ou chave de acesso.
 * Responde perguntas comuns de HelpDesk com respostas predefinidas.
 */
@Service
public class ChatbotLocalService {

    private final List<RegraResposta> regras;

    public ChatbotLocalService() {
        this.regras = inicializarRegras();
    }

    /**
     * Gera uma resposta para a pergunta do usuário.
     * Procura a melhor regra correspondente por palavras-chave.
     */
    public String responder(String pergunta) {
        if (pergunta == null || pergunta.isBlank()) {
            return null;
        }

        String normalizada = normalizar(pergunta);

        // Procura a regra com maior pontuação de correspondência
        RegraResposta melhorRegra = null;
        int melhorPontuacao = 0;

        for (RegraResposta regra : regras) {
            int pontuacao = regra.calcularPontuacao(normalizada);
            if (pontuacao > melhorPontuacao) {
                melhorPontuacao = pontuacao;
                melhorRegra = regra;
            }
        }

        if (melhorRegra != null && melhorPontuacao >= 1) {
            return melhorRegra.getResposta();
        }

        // Resposta padrão quando não encontra correspondência
        return "Não encontrei uma resposta específica para essa pergunta. "
                + "Aqui vão algumas dicas:\n\n"
                + "1. Tente reformular sua pergunta com mais detalhes.\n"
                + "2. Verifique se sua dúvida se encaixa em uma das nossas categorias.\n\n"
                + "**Posso te ajudar com:**\n"
                + "- **Suporte Técnico**: senha, internet, e-mail, impressora, VPN, software, hardware\n"
                + "- **Financeiro**: investimentos, salário, reembolso, impostos\n"
                + "- **RH**: férias, benefícios, atestado, ponto, admissão\n"
                + "- **Geral**: produtividade, Excel, reuniões, segurança, home office, cursos\n\n"
                + "Se precisar de ajuda especializada, abra um chamado no sistema.";
    }

    private String normalizar(String texto) {
        String semAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return semAcentos.toLowerCase().trim();
    }

    private List<RegraResposta> inicializarRegras() {
        List<RegraResposta> lista = new ArrayList<>();

        // --- Senha e Login ---
        lista.add(new RegraResposta(
                List.of("senha", "password", "trocar senha", "alterar senha", "resetar senha",
                        "esqueci senha", "esqueci a senha", "redefinir senha", "mudar senha"),
                "Para problemas com senha, siga estes passos:\n\n"
                        + "1. Acesse a tela de login e clique em \"Esqueci minha senha\".\n"
                        + "2. Informe seu e-mail corporativo cadastrado.\n"
                        + "3. Verifique sua caixa de entrada (e spam) para o link de redefinição.\n"
                        + "4. Crie uma nova senha com no mínimo 8 caracteres, incluindo letras maiúsculas, "
                        + "minúsculas, números e caracteres especiais.\n\n"
                        + "Se o link não chegar em 5 minutos, tente novamente ou abra um chamado para o suporte."
        ));

        lista.add(new RegraResposta(
                List.of("login", "logar", "entrar", "acessar", "nao consigo entrar",
                        "nao consigo acessar", "acesso negado", "usuario bloqueado", "conta bloqueada"),
                "Se você não está conseguindo fazer login:\n\n"
                        + "1. Verifique se o Caps Lock está desativado.\n"
                        + "2. Confirme que está usando o e-mail correto (corporativo).\n"
                        + "3. Tente redefinir sua senha pelo link \"Esqueci minha senha\".\n"
                        + "4. Se sua conta foi bloqueada após muitas tentativas, aguarde 15 minutos "
                        + "e tente novamente.\n"
                        + "5. Caso o problema persista, abra um chamado informando seu nome completo e e-mail."
        ));

        // --- Internet e Rede ---
        lista.add(new RegraResposta(
                List.of("internet", "rede", "wifi", "wi-fi", "sem conexao", "conexao",
                        "nao conecta", "sem internet", "lento", "lenta", "velocidade"),
                "Para problemas de internet/rede:\n\n"
                        + "1. Verifique se o cabo de rede está conectado (se for conexão cabeada).\n"
                        + "2. Tente desconectar e reconectar o Wi-Fi.\n"
                        + "3. Reinicie seu computador.\n"
                        + "4. Teste acessar um site diferente para confirmar se é problema geral.\n"
                        + "5. Se estiver lento, feche programas que consomem banda (downloads, streaming).\n\n"
                        + "Se o problema persistir, informe ao suporte a localização da sua estação de trabalho."
        ));

        // --- E-mail ---
        lista.add(new RegraResposta(
                List.of("email", "e-mail", "outlook", "correio", "enviar email",
                        "receber email", "nao recebo email", "caixa cheia", "spam"),
                "Para problemas com e-mail:\n\n"
                        + "1. Verifique sua conexão com a internet.\n"
                        + "2. Confirme se não está com a caixa cheia — limpe e-mails antigos.\n"
                        + "3. Verifique a pasta de spam/lixo eletrônico.\n"
                        + "4. Se usa Outlook, tente fechar e abrir novamente.\n"
                        + "5. Limpe o cache do Outlook: Arquivo > Opções > Avançado.\n\n"
                        + "Se nada funcionar, abra um chamado informando o erro exato que aparece."
        ));

        // --- Impressora ---
        lista.add(new RegraResposta(
                List.of("impressora", "imprimir", "impressao", "nao imprime", "papel",
                        "toner", "scanner", "escanear", "digitalizar"),
                "Para problemas com impressora:\n\n"
                        + "1. Verifique se a impressora está ligada e com papel.\n"
                        + "2. Confirme se está selecionando a impressora correta ao imprimir.\n"
                        + "3. Reinicie a impressora (desligue, aguarde 10 segundos, ligue).\n"
                        + "4. No Windows: vá em Configurações > Impressoras e verifique se não há "
                        + "documentos na fila travados.\n"
                        + "5. Se aparece erro de toner/cartucho, informe ao suporte para substituição.\n\n"
                        + "Para scanner: use o software de digitalização padrão instalado no computador."
        ));

        // --- VPN ---
        lista.add(new RegraResposta(
                List.of("vpn", "acesso remoto", "trabalho remoto", "home office",
                        "conectar vpn", "vpn nao conecta"),
                "Para problemas com VPN/acesso remoto:\n\n"
                        + "1. Verifique se sua internet está funcionando normalmente.\n"
                        + "2. Feche e abra novamente o cliente VPN.\n"
                        + "3. Confirme que suas credenciais estão corretas.\n"
                        + "4. Tente se desconectar de outras VPNs que possam conflitar.\n"
                        + "5. Reinicie o computador e tente novamente.\n\n"
                        + "Se ainda não funcionar, verifique se seu acesso VPN foi liberado pelo gestor. "
                        + "Caso não tenha acesso, solicite ao seu gerente que abra um chamado de liberação."
        ));

        // --- Software e Instalação ---
        lista.add(new RegraResposta(
                List.of("instalar", "instalacao", "programa", "software", "aplicativo",
                        "atualizar", "atualizacao", "licenca", "ativar"),
                "Sobre instalação de software:\n\n"
                        + "1. Verifique se o software desejado está na lista de aplicativos aprovados.\n"
                        + "2. Para instalações autorizadas, use a Central de Software da empresa.\n"
                        + "3. Se precisar de um software que não está disponível, abra um chamado "
                        + "informando o nome do programa e a justificativa.\n"
                        + "4. Atualizações do sistema são feitas automaticamente — não adie as reinicializações.\n\n"
                        + "Importante: não instale softwares de fontes desconhecidas por conta própria."
        ));

        // --- Hardware e Equipamento ---
        lista.add(new RegraResposta(
                List.of("computador", "notebook", "tela", "monitor", "mouse", "teclado",
                        "nao liga", "travando", "lento", "desligando", "reiniciando",
                        "azul", "tela azul", "hardware", "equipamento", "headset", "fone"),
                "Para problemas de hardware:\n\n"
                        + "1. Se o computador não liga: verifique se está conectado na tomada e se o "
                        + "estabilizador está ligado.\n"
                        + "2. Se está travando/lento: feche programas desnecessários e reinicie.\n"
                        + "3. Tela azul: anote a mensagem de erro e reinicie. Se repetir, abra um chamado.\n"
                        + "4. Monitor sem imagem: verifique os cabos e tente outro ponto de energia.\n"
                        + "5. Mouse/teclado sem funcionar: troque as pilhas ou reconecte o USB.\n\n"
                        + "Para troca de equipamento defeituoso, abra um chamado com a descrição do problema."
        ));

        // --- Chamado / Ticket ---
        lista.add(new RegraResposta(
                List.of("chamado", "ticket", "abrir chamado", "abrir ticket", "criar chamado",
                        "status chamado", "andamento", "meu chamado", "acompanhar"),
                "Sobre chamados/tickets:\n\n"
                        + "1. Para abrir um novo chamado: acesse o sistema HelpDesk e clique em "
                        + "\"Nova Pergunta\" ou \"Novo Chamado\".\n"
                        + "2. Descreva o problema com o máximo de detalhes possível.\n"
                        + "3. Inclua: o que aconteceu, quando começou, mensagens de erro (se houver).\n"
                        + "4. Para acompanhar: acesse \"Minhas Perguntas\" no sistema.\n"
                        + "5. Prioridades: chamados são atendidos por ordem de criticidade.\n\n"
                        + "Tempo médio de resposta: até 24h para prioridade normal."
        ));

        // --- Celular / Dispositivo Móvel ---
        lista.add(new RegraResposta(
                List.of("celular", "smartphone", "aplicativo celular", "mobile",
                        "android", "iphone", "ios", "configurar celular", "email celular"),
                "Para configuração de dispositivos móveis:\n\n"
                        + "1. E-mail no celular: use as configurações IMAP/SMTP fornecidas pelo TI.\n"
                        + "2. Para acesso corporativo: instale o app de gerenciamento da empresa.\n"
                        + "3. Wi-Fi corporativo no celular: use suas credenciais de rede.\n"
                        + "4. Se precisa de suporte para configuração, abra um chamado informando "
                        + "o modelo do aparelho e o que deseja configurar.\n\n"
                        + "Lembre-se: não armazene dados corporativos sensíveis em dispositivos pessoais."
        ));

        // --- Saudações ---
        lista.add(new RegraResposta(
                List.of("oi", "ola", "bom dia", "boa tarde", "boa noite", "hey", "hi",
                        "hello", "e ai", "fala", "salve"),
                "Olá! Sou o assistente virtual do HelpDesk. "
                        + "Estou aqui para te ajudar com dúvidas sobre:\n\n"
                        + "- Senha e login\n"
                        + "- Internet e rede\n"
                        + "- E-mail\n"
                        + "- Impressora\n"
                        + "- VPN e acesso remoto\n"
                        + "- Instalação de software\n"
                        + "- Problemas de hardware\n"
                        + "- Chamados e tickets\n\n"
                        + "Como posso te ajudar?"
        ));

        // --- Agradecimento ---
        lista.add(new RegraResposta(
                List.of("obrigado", "obrigada", "valeu", "thanks", "agradeco",
                        "muito obrigado", "brigado"),
                "De nada! Fico feliz em ajudar. "
                        + "Se tiver mais alguma dúvida, é só perguntar. Estou à disposição!"
        ));

        // --- Ajuda / O que você faz ---
        lista.add(new RegraResposta(
                List.of("ajuda", "help", "o que voce faz", "como funciona",
                        "o que pode fazer", "quais perguntas"),
                "Sou o assistente virtual do HelpDesk e posso te ajudar com:\n\n"
                        + "- **Suporte Técnico**: senha, internet, e-mail, impressora, VPN, software, hardware\n"
                        + "- **Financeiro**: investimentos, salário, reembolso, nota fiscal\n"
                        + "- **RH**: férias, benefícios, atestado, ponto, contratação\n"
                        + "- **Geral**: dúvidas diversas do dia a dia\n\n"
                        + "Faça sua pergunta e vou tentar te ajudar!"
        ));

        // ====================================================================
        // CATEGORIA: FINANCEIRO
        // ====================================================================

        // --- Investimentos ---
        lista.add(new RegraResposta(
                List.of("investimento", "investir", "melhor investimento", "renda fixa",
                        "renda variavel", "tesouro direto", "poupanca", "acoes", "fundos",
                        "cdb", "lci", "lca", "onde investir", "aplicacao financeira"),
                "Sobre investimentos, aqui vão algumas orientações gerais:\n\n"
                        + "**Renda Fixa (menor risco):**\n"
                        + "- Tesouro Direto (Selic, IPCA+, Prefixado)\n"
                        + "- CDB de bancos sólidos\n"
                        + "- LCI/LCA (isentos de IR para pessoa física)\n\n"
                        + "**Renda Variável (maior risco, maior potencial):**\n"
                        + "- Ações de empresas listadas na bolsa\n"
                        + "- Fundos imobiliários (FIIs)\n"
                        + "- ETFs (fundos de índice)\n\n"
                        + "**Dicas importantes:**\n"
                        + "- Defina seu perfil (conservador, moderado, arrojado)\n"
                        + "- Diversifique seus investimentos\n"
                        + "- Monte uma reserva de emergência antes de investir em renda variável\n"
                        + "- A poupança rende pouco — considere alternativas como Tesouro Selic\n\n"
                        + "Importante: isso não é recomendação financeira. Consulte um profissional certificado."
        ));

        // --- Salário e Holerite ---
        lista.add(new RegraResposta(
                List.of("salario", "holerite", "contracheque", "pagamento", "data pagamento",
                        "quando recebo", "adiantamento", "vale", "decimo terceiro", "13o"),
                "Sobre salário e pagamentos:\n\n"
                        + "- **Data de pagamento**: geralmente até o 5º dia útil do mês.\n"
                        + "- **Holerite/Contracheque**: disponível no portal do colaborador ou RH.\n"
                        + "- **Adiantamento**: consulte a política da empresa com o RH.\n"
                        + "- **13º salário**: 1ª parcela até 30/nov, 2ª parcela até 20/dez.\n"
                        + "- **Vale-transporte e vale-refeição**: creditados no início do mês.\n\n"
                        + "Para divergências no valor, abra um chamado na categoria Financeiro com "
                        + "detalhes do que está incorreto."
        ));

        // --- Reembolso ---
        lista.add(new RegraResposta(
                List.of("reembolso", "reembolsar", "nota fiscal", "recibo", "despesa",
                        "prestacao de contas", "ressarcimento", "km rodado", "quilometragem"),
                "Para solicitar reembolso:\n\n"
                        + "1. Reúna os comprovantes (nota fiscal ou recibo com CNPJ).\n"
                        + "2. Preencha o formulário de reembolso no sistema ou com o financeiro.\n"
                        + "3. Anexe as notas fiscais digitalizadas.\n"
                        + "4. Aguarde a aprovação do gestor.\n"
                        + "5. O reembolso é creditado junto com o próximo pagamento.\n\n"
                        + "**Prazo para solicitar**: até 30 dias após a despesa.\n"
                        + "**Itens reembolsáveis**: transporte, alimentação em viagem, material de trabalho.\n\n"
                        + "Dúvidas específicas? Abra um chamado na categoria Financeiro."
        ));

        // --- Impostos e IR ---
        lista.add(new RegraResposta(
                List.of("imposto", "imposto de renda", "ir", "declaracao", "informe de rendimento",
                        "informe rendimentos", "receita federal", "irpf", "deducao"),
                "Sobre Imposto de Renda:\n\n"
                        + "- **Informe de rendimentos**: disponibilizado pela empresa até o final de fevereiro.\n"
                        + "- **Prazo de declaração**: geralmente de março a maio (consulte a Receita Federal).\n"
                        + "- **Deduções comuns**: saúde, educação, previdência, dependentes.\n"
                        + "- **Restituição**: paga em lotes ao longo do ano.\n\n"
                        + "Para solicitar o informe de rendimentos, procure o RH ou o departamento financeiro."
        ));

        // ====================================================================
        // CATEGORIA: RH (Recursos Humanos)
        // ====================================================================

        // --- Férias ---
        lista.add(new RegraResposta(
                List.of("ferias", "marcar ferias", "agendar ferias", "periodo aquisitivo",
                        "vender ferias", "abono pecuniario", "saldo ferias"),
                "Sobre férias:\n\n"
                        + "- **Direito**: 30 dias após 12 meses de trabalho (período aquisitivo).\n"
                        + "- **Agendamento**: combine com seu gestor e solicite ao RH com antecedência mínima de 30 dias.\n"
                        + "- **Fracionamento**: pode dividir em até 3 períodos (mínimo de 14 dias em um deles).\n"
                        + "- **Abono pecuniário**: você pode vender até 1/3 das férias (10 dias).\n"
                        + "- **Pagamento**: deve ser feito até 2 dias antes do início das férias.\n\n"
                        + "Para agendar, abra um chamado na categoria RH ou fale diretamente com o departamento."
        ));

        // --- Benefícios ---
        lista.add(new RegraResposta(
                List.of("beneficio", "beneficios", "plano de saude", "plano saude",
                        "convenio", "odontologico", "seguro de vida", "vale alimentacao",
                        "vale refeicao", "gympass", "totalpass"),
                "Sobre benefícios da empresa:\n\n"
                        + "- **Plano de saúde**: consulte a cobertura e rede credenciada com o RH.\n"
                        + "- **Plano odontológico**: geralmente incluso — verifique elegibilidade.\n"
                        + "- **Vale-alimentação/refeição**: valor e regras variam por cargo/região.\n"
                        + "- **Seguro de vida**: cobertura automática para CLT.\n"
                        + "- **Inclusão de dependentes**: solicite ao RH com documentação.\n\n"
                        + "Para alterações nos benefícios (inclusão/exclusão de dependentes, troca de plano), "
                        + "abra um chamado na categoria RH."
        ));

        // --- Atestado médico ---
        lista.add(new RegraResposta(
                List.of("atestado", "atestado medico", "doente", "afastamento",
                        "licenca medica", "cid", "falta justificada"),
                "Sobre atestados médicos:\n\n"
                        + "1. Apresente o atestado ao RH em até 48 horas.\n"
                        + "2. O atestado deve conter: nome do médico, CRM, data, período de afastamento e CID (opcional).\n"
                        + "3. Atestados de até 15 dias: a empresa abona normalmente.\n"
                        + "4. Acima de 15 dias: encaminhamento ao INSS pode ser necessário.\n\n"
                        + "Envie digitalizado por e-mail ao RH ou entregue fisicamente."
        ));

        // --- Ponto e jornada ---
        lista.add(new RegraResposta(
                List.of("ponto", "bater ponto", "registro ponto", "hora extra",
                        "banco de horas", "jornada", "horario trabalho", "atraso"),
                "Sobre ponto e jornada de trabalho:\n\n"
                        + "- **Registro**: use o sistema de ponto eletrônico da empresa.\n"
                        + "- **Esqueceu de bater**: solicite ajuste ao gestor/RH com justificativa.\n"
                        + "- **Hora extra**: precisa de aprovação prévia do gestor.\n"
                        + "- **Banco de horas**: consulte seu saldo no portal do colaborador.\n"
                        + "- **Atrasos**: tolerância geralmente de 5-10 min. Acima disso, desconto ou justificativa.\n\n"
                        + "Problemas com o sistema de ponto? Abra um chamado na categoria RH."
        ));

        // --- Contratação e documentos ---
        lista.add(new RegraResposta(
                List.of("admissao", "contratacao", "documentos admissao", "ctps",
                        "carteira trabalho", "contrato", "experiencia", "efetivacao",
                        "registro", "demissao", "rescisao", "aviso previo"),
                "Sobre admissão e documentos:\n\n"
                        + "**Para admissão, geralmente são necessários:**\n"
                        + "- RG e CPF\n"
                        + "- CTPS (carteira de trabalho — hoje pode ser digital)\n"
                        + "- Comprovante de residência\n"
                        + "- Certidão de nascimento/casamento\n"
                        + "- Dados bancários\n"
                        + "- Foto 3x4\n\n"
                        + "**Período de experiência**: geralmente 45+45 dias (90 dias no total).\n\n"
                        + "Para dúvidas sobre rescisão ou aviso prévio, procure o RH diretamente."
        ));

        // ====================================================================
        // CATEGORIA: GERAL
        // ====================================================================

        // --- Dicas de produtividade ---
        lista.add(new RegraResposta(
                List.of("produtividade", "produtivo", "organizar", "organizacao",
                        "gerenciar tempo", "gestao tempo", "foco", "concentracao", "dica"),
                "Dicas de produtividade no trabalho:\n\n"
                        + "1. **Técnica Pomodoro**: trabalhe 25 min focado, descanse 5 min.\n"
                        + "2. **Priorize tarefas**: use a matriz de Eisenhower (urgente vs importante).\n"
                        + "3. **Evite multitarefa**: foque em uma coisa por vez.\n"
                        + "4. **Organize o ambiente**: mesa limpa = mente limpa.\n"
                        + "5. **Bloqueie distrações**: silencie notificações durante tarefas importantes.\n"
                        + "6. **Defina metas diárias**: liste 3 tarefas prioritárias todo dia.\n\n"
                        + "Ferramentas úteis: Trello, Notion, Google Calendar, Todoist."
        ));

        // --- Excel e planilhas ---
        lista.add(new RegraResposta(
                List.of("excel", "planilha", "formula", "tabela dinamica", "procv",
                        "somase", "grafico", "csv", "google sheets"),
                "Dicas para Excel/planilhas:\n\n"
                        + "**Fórmulas úteis:**\n"
                        + "- PROCV/VLOOKUP: buscar valores em outra tabela\n"
                        + "- SOMASE/SUMIF: somar com condição\n"
                        + "- SE/IF: lógica condicional\n"
                        + "- CONT.SE/COUNTIF: contar com critério\n\n"
                        + "**Dicas:**\n"
                        + "- Use Ctrl+T para criar tabelas formatadas\n"
                        + "- Tabelas dinâmicas são ótimas para resumir dados grandes\n"
                        + "- Ctrl+; insere a data atual\n"
                        + "- Alt+Enter para quebrar linha dentro da célula\n\n"
                        + "Para treinamentos específicos, consulte o RH sobre cursos disponíveis."
        ));

        // --- Reuniões e comunicação ---
        lista.add(new RegraResposta(
                List.of("reuniao", "meeting", "teams", "zoom", "google meet",
                        "videoconferencia", "agendar reuniao", "sala reuniao"),
                "Sobre reuniões e comunicação:\n\n"
                        + "- **Agendar**: use o calendário corporativo (Outlook/Google Calendar).\n"
                        + "- **Salas de reunião**: reserve pelo sistema de agendamento.\n"
                        + "- **Videoconferência**: use a ferramenta padrão da empresa (Teams/Meet/Zoom).\n"
                        + "- **Boas práticas**: envie pauta antes, seja pontual, respeite o tempo.\n"
                        + "- **Gravar reunião**: peça permissão aos participantes antes.\n\n"
                        + "Problemas técnicos com áudio/vídeo? Verifique microfone e câmera nas configurações."
        ));

        // --- Segurança da informação ---
        lista.add(new RegraResposta(
                List.of("seguranca", "phishing", "golpe", "virus", "malware",
                        "antivirus", "email suspeito", "link suspeito", "vazamento dados"),
                "Dicas de segurança da informação:\n\n"
                        + "1. **Nunca clique** em links de e-mails suspeitos.\n"
                        + "2. **Verifique o remetente**: golpistas usam endereços parecidos.\n"
                        + "3. **Não compartilhe senhas** com ninguém, nem por e-mail.\n"
                        + "4. **Use senhas fortes**: mínimo 8 caracteres com letras, números e símbolos.\n"
                        + "5. **Bloqueie o computador** ao sair da mesa (Win+L).\n"
                        + "6. **Reporte incidentes**: se receber algo suspeito, avise o TI imediatamente.\n\n"
                        + "Em caso de incidente de segurança, abra um chamado URGENTE na categoria Suporte Técnico."
        ));

        // --- Trabalho remoto / Home Office ---
        lista.add(new RegraResposta(
                List.of("home office", "trabalho remoto", "remoto", "hibrido",
                        "trabalhar de casa", "teletrabalho"),
                "Sobre trabalho remoto/home office:\n\n"
                        + "- **Equipamento**: verifique com o TI o que a empresa fornece.\n"
                        + "- **Internet**: garanta uma conexão estável (mínimo 25 Mbps recomendado).\n"
                        + "- **VPN**: necessária para acessar sistemas internos (veja instruções de VPN).\n"
                        + "- **Ergonomia**: ajuste cadeira, monitor na altura dos olhos, pausas regulares.\n"
                        + "- **Comunicação**: mantenha-se disponível no chat/Teams durante o expediente.\n"
                        + "- **Ponto**: registre normalmente pelo sistema eletrônico.\n\n"
                        + "Dúvidas sobre política de home office? Consulte seu gestor ou o RH."
        ));

        // --- Cursos e treinamentos ---
        lista.add(new RegraResposta(
                List.of("curso", "treinamento", "capacitacao", "certificacao",
                        "aprender", "desenvolvimento profissional", "carreira"),
                "Sobre cursos e desenvolvimento profissional:\n\n"
                        + "- **Plataformas**: consulte se a empresa oferece acesso a Alura, Udemy, Coursera, etc.\n"
                        + "- **Reembolso de certificação**: algumas empresas custeiam — consulte o RH.\n"
                        + "- **Treinamentos internos**: fique atento aos comunicados e agenda de capacitações.\n"
                        + "- **PDI (Plano de Desenvolvimento Individual)**: converse com seu gestor.\n\n"
                        + "Áreas em alta: cloud computing, análise de dados, metodologias ágeis, IA.\n\n"
                        + "Para solicitar um treinamento específico, abra um chamado na categoria RH."
        ));

        return lista;
    }

    // --- Classe interna para regras ---
    private static class RegraResposta {
        private final List<String> palavrasChave;
        private final String resposta;

        public RegraResposta(List<String> palavrasChave, String resposta) {
            this.palavrasChave = palavrasChave;
            this.resposta = resposta;
        }

        /**
         * Calcula a pontuação de correspondência entre o texto e as palavras-chave.
         * Quanto mais palavras-chave encontradas, maior a pontuação.
         */
        public int calcularPontuacao(String textoNormalizado) {
            int pontuacao = 0;
            for (String chave : palavrasChave) {
                if (textoNormalizado.contains(chave)) {
                    // Frases mais longas ganham peso maior
                    pontuacao += chave.split("\\s+").length;
                }
            }
            return pontuacao;
        }

        public String getResposta() {
            return resposta;
        }
    }
}
