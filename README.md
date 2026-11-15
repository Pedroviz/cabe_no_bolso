# Cabe no Bolso - Planejador de Despesas Compartilhadas

Aplicação desenvolvida em Java com interface gráfica Swing para planejamento e rateio de despesas compartilhadas, aplicando o padrão de arquitetura MVC e Orientação a Objetos.

---

## 👥 Equipe de Desenvolvimento

- **Pedro Atila Freitas Costa** — Matrícula: 2640880
- **Carlos Alberto Holanda da Cruz Neto** — Matrícula: 2641734
- **Elvis Júnior Barros de Oliveira** — Matrícula: 2640898
- **Igor Farias Valentim** — Matrícula: 2640869
- **Naebia Costa de Oliveira** — Matrícula: 2640866
- **Jose Aedo Ferreira Freire** — Matrícula: 2640883

---

## 🛠️ Tecnologias e Arquitetura

- **Linguagem**: Java (JDK 17 ou superior)
- **Interface Gráfica**: Java Swing (`JFrame`, `JTabbedPane`, `JTable`, `JList`, `JPanel`)
- **Arquitetura**: Padrão MVC (`model`, `persistence`, `controller`, `view`)
- **Persistência de Dados**: Serialização de objetos Java (`.dat`)
- **Conceitos de POO Aplicados**:
  - **Abstração & Encapsulamento**: Atributos privados, validações defensivas e controle de acesso via getters e setters.
  - **Herança & Polimorfismo**: Classe abstrata `Despesa` especializada em `DespesaFixa` e `DespesaPorPessoa`, com sobrescrita polimórfica do método `calcularRateio()`.
  - **Tratamento de Exceções**: Validações de integridade contra duplicidade, exclusão de registros com dependência e entradas numéricas inválidas.

---

## 📂 Estrutura de Pacotes do Projeto

```text
cabe_no_bolso/
├── bin/                                    # Arquivos compilados (.class)
├── src/
│   ├── model/                              # Regras de negócio e entidades
│   │   ├── Despesa.java
│   │   ├── DespesaFixa.java
│   │   ├── DespesaPorPessoa.java
│   │   ├── Participante.java
│   │   └── Planejamento.java
│   ├── persistence/                        # Contrato e serialização de arquivos
│   │   ├── ArquivoPersistenciaService.java
│   │   └── PersistenciaService.java
│   ├── controller/                         # Intermediação entre visão e modelo
│   │   └── PlanejamentoController.java
│   └── view/                               # Interface gráfica Java Swing
│       ├── MainFrame.java
│       ├── PainelDespesas.java
│       ├── PainelParticipantes.java
│       └── PainelResumo.java
├── .gitignore
└── README.md
```

---

## 🚀 Como Compilar e Executar

### Via Terminal

1. Clone o repositório:

   ```bash
   git clone https://github.com/Pedroviz/cabe_no_bolso.git
   cd cabe_no_bolso
   ```

2. Compile o código-fonte:

   ```bash
   javac -d bin -sourcepath src src/view/MainFrame.java
   ```

3. Execute a aplicação:
   ```bash
   java -cp bin view.MainFrame
   ```

---

## 🧪 Como Reproduzir o Teste do Caso de Referência (Passeio da Turma)

1. Abra a aba **Participantes** e adicione:
   - Código `1`: Ana (Orçamento: R$ 70,00)
   - Código `2`: Bruno (Orçamento: R$ 60,00)
   - Código `3`: Carla (Orçamento: R$ 70,00)

2. Abra a aba **Despesas** e cadastre:
   - **Transporte** | Valor: R$ 90,00 | Tipo: Fixa | Participantes: Ana, Bruno, Carla
   - **Almoço** | Valor: R$ 20,00 | Tipo: Por Pessoa | Participantes: Ana, Bruno, Carla
   - **Atividade Recreativa** | Valor: R$ 30,00 | Tipo: Fixa | Participantes: Ana, Carla
   - **Sobremesa** | Valor: R$ 10,00 | Tipo: Por Pessoa | Participantes: Ana, Bruno, Carla

3. Acesse a aba **Resumo**:
   - Confira o Custo Total Geral: **R$ 210,00**
   - Verifique o gasto individual:
     - **Ana**: R$ 75,00 (Excede R$ 5,00)
     - **Bruno**: R$ 60,00 (Dentro do orçamento)
     - **Carla**: R$ 75,00 (Excede R$ 5,00)

---

## 📦 Passos para Criar o Repositório no GitHub e Subir o Código

1. **Criar o Repositório no GitHub**:
   - Acesse [GitHub](https://github.com) e faça login.
   - Clique no botão **"+"** no canto superior direito > **"New repository"**.
   - Nome do repositório: **`cabe-no-bolso`**.
   - Mantenha como **Público**.
   - **Não assinale** as opções de inicializar com README ou .gitignore.
   - Clique em **"Create repository"** e copie o link gerado.

2. **Executar os Comandos do Git no VS Code**:
   - Abra o terminal integrado no diretório do projeto e execute:
   ```bash
   git init
   git add .
   git commit -m "feat: estrutura inicial da aplicação MVC e documentacao README"
   git branch -M main
   git remote add origin <SEU_URL_DO_GITHUB>
   git push -u origin main
   ```
