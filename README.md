# **Sistema de Locação Rota Segura**

`Nome`: Victor Hugo Ferreira Felix de Oliveira  
`RA`: 1301392611041

## **Arquitetura**

O projeto foi desenvolvido em Java, utilizando a biblioteca Swing para a interface gráfica e MariaDB/MySQL para o armazenamento dos dados.

- `Veiculo`: classe abstrata que define os atributos e comportamentos comuns dos veículos.
- `Popular`, `Sedan` e `Suv`: especializações da classe `Veiculo`, com características e cálculos próprios.
- `Cliente`: representa os clientes cadastrados no sistema.
- `Contrato`: representa uma locação e relaciona o cliente ao veículo, incluindo as datas e a situação do contrato.
- `Documento`: interface para padronizar a geração de documentos.
- `Recibo`: gera o comprovante da locação.
- `RelatorioFechamento`: gera o relatório de fechamento da locação.
- `SistemaLocacao`: camada de serviço responsável pelas operações de clientes, veículos e locações.
- `DAO`: interface que define as operações de acesso aos dados.
- `ClienteDAO`: realiza as operações de cadastro, consulta, atualização e exclusão de clientes.
- `VeiculoDAO`: realiza as operações de cadastro, consulta, atualização e exclusão de veículos.
- `ContratoDAO`: gerencia os contratos e as operações de registro e fechamento das locações.
- `GestorDeConexao`: gerencia a conexão com o banco de dados.
- `TelaPrincipal`: apresenta o menu principal do sistema.
- `TelaClientes`: permite cadastrar, consultar, atualizar e excluir clientes.
- `TelaVeiculos`: permite cadastrar, consultar, atualizar e excluir veículos.
- `TelaLocacoes`: permite registrar e consultar as locações.
- `TelaFechamento`: permite visualizar os valores e realizar o fechamento dos contratos.
- `Main`: inicia a aplicação e apresenta a interface gráfica.

## **Diagrama de Classe:**

[Visualizar Diagrama de Classe](https://github.com/VHFFDO/Locacao-Rota-Segura/blob/76ad327d611e6fb1221097759a918ba589a3949c/Diagrama%20de%20Classe/Diagrama%20Loca%C3%A7%C3%A3o%20Rota%20Segura.pdf)

## **Banco de Dados**

O sistema utiliza o banco de dados `rota_segura`, com três tabelas principais:

- `clientes`: armazena CPF, nome, telefone e e-mail dos clientes.
- `veiculos`: armazena placa, marca, modelo, ano, cor, categoria e disponibilidade dos veículos.
- `contratos`: armazena os dados das locações, relacionando clientes e veículos por meio de chaves estrangeiras.

O banco utiliza relacionamentos entre as tabelas para manter a integridade dos dados.

### **Configuração da conexão**

Para conectar o sistema ao banco de dados, é utilizado o arquivo `configs.txt`, com as seguintes configurações:

```properties
url=jdbc:mariadb://localhost:3306/rota_segura
usuario=root
senha=
```

É necessário ter o servidor MariaDB/MySQL em execução e o driver JDBC do MariaDB disponível na pasta `lib`.

**Observação:** o arquivo `configs.txt` contém configurações locais de conexão e não deve ser enviado ao GitHub.

## **Compilação e Execução**

Para executar o projeto, é necessário ter o JDK instalado, o servidor MariaDB/MySQL configurado e o banco de dados `rota_segura` criado.

1. Iniciar o servidor MariaDB/MySQL pelo XAMPP.
2. Criar ou selecionar o banco de dados `rota_segura`.
3. Criar as tabelas `clientes`, `veiculos` e `contratos`, caso ainda não existam.
4. Conferir as informações de conexão no arquivo `configs.txt`.
5. Abrir o projeto na IDE IntelliJ IDEA.
6. Verificar se o driver JDBC do MariaDB está configurado nas dependências do projeto.
7. Executar a classe `Main`.
8. Na tela principal, selecionar a operação desejada: clientes, veículos ou locações.

### **Funcionalidades do sistema**

- Cadastro, consulta, atualização e exclusão de clientes.
- Cadastro, consulta, atualização e exclusão de veículos.
- Registro e consulta de locações.
- Verificação da disponibilidade dos veículos.
- Controle da situação dos contratos.
- Cálculo dos valores de diárias, seguro e manutenção.
- Fechamento de contratos e devolução dos veículos.
- Geração de recibos e relatórios de fechamento.
- Armazenamento persistente dos dados no banco de dados.

### **Formato das datas**

As datas das locações devem ser informadas no formato brasileiro:

`dd/MM/aaaa`

Exemplo: `27/11/2026`.

## **Cenários de Teste**

### **1. Cadastro de cliente**

Foi realizado o cadastro de um cliente informando CPF, nome, telefone e e-mail.

**Resultado esperado:** o sistema deve validar os dados e armazenar o cliente no banco de dados.

### **2. Cadastro de veículo**

Foi realizado o cadastro de um veículo com placa, marca, modelo, ano, cor e categoria.

**Resultado esperado:** o veículo deve ser armazenado no banco de dados e ficar disponível para locação.

### **3. Locação válida**

Foi criada uma locação utilizando um cliente cadastrado, um veículo disponível e datas válidas.

**Resultado esperado:** o contrato deve ser registrado e o veículo deve ficar indisponível para outra locação.

### **4. Veículo indisponível**

Foi realizada uma tentativa de registrar uma nova locação utilizando um veículo que já está alugado.

**Resultado esperado:** o sistema deve impedir a operação e informar que o veículo não está disponível.

### **5. Datas inválidas**

Foi realizada uma tentativa de registrar uma locação com datas inválidas ou com a data final anterior à data inicial.

**Resultado esperado:** o sistema deve rejeitar a operação e apresentar uma mensagem de validação.

### **6. Fechamento de contrato**

Foi realizado o fechamento de uma locação ativa, com a visualização dos valores de diárias, seguro e manutenção.

**Resultado esperado:** o contrato deve ser encerrado, o recibo e o relatório devem ser gerados e o veículo deve voltar a ficar disponível.

### **7. Persistência dos dados**

Após realizar operações de cadastro e locação, os dados são armazenados no banco de dados MariaDB/MySQL.

**Resultado esperado:** os registros devem permanecer armazenados mesmo após o encerramento do sistema.