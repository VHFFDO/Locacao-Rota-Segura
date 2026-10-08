# **Sistema de Locação Rota Segura**
`Nome`: Victor Hugo Ferreira Felix de Oliveira  
`RA`: 1301392611041

## **Arquitetura**
- `Veiculo`: classe abstrata que define os atributos e comportamentos comuns dos veículos.
- `Popular`, `Sedan` e `SUV`: especializações da classe `Veiculo`.
- `Cliente`: representa os clientes cadastrados.
- `Contrato`: representa uma locação e relaciona o cliente ao veículo.
- `Documento`: interface para padronizar a geração de documentos.
- `Recibo`: gera o comprovante da locação.
- `RelatorioFechamento`: gera o relatório de fechamento.
- `SistemaLocacao`: gerencia veículos, clientes e locações.
- `ArquivoLocacoes`: realiza a persistência do histórico em arquivo `.txt`.
- `Entrada`: realiza a leitura e validação dos dados.
- `Main`: executa o fluxo principal da aplicação.

## **Diagrama de Classe:**
[Visualizar Diagrama de Classe](https://github.com/VHFFDO/Locacao-Rota-Segura/blob/76ad327d611e6fb1221097759a918ba589a3949c/Diagrama%20de%20Classe/Diagrama%20Loca%C3%A7%C3%A3o%20Rota%20Segura.pdf)

## **Compilação e Execução**
O fluxo principal da aplicação é iniciado pela classe `Main`.

1. O sistema cria uma instância de `SistemaLocacao`.
2. São cadastrados veículos das categorias Popular, Sedan e SUV.
3. Um cliente é cadastrado no sistema.
4. É criada uma nova locação informando o cliente, o veículo e as datas.
5. O `Contrato` valida os dados e verifica se o veículo está disponível.
6. O veículo é marcado como indisponível durante a locação.
7. A locação é registrada no histórico e salva no arquivo `historico_locacoes.txt`.
8. O contrato e os valores da locação são exibidos.
9. A locação é encerrada e o veículo volta a ficar disponível.
10. É gerado e impresso o recibo da locação.

## **Cenários de Teste**

### **1. Locação válida**
Foi criado um contrato utilizando um cliente, um veículo disponível e datas válidas.

**Resultado:** a locação foi realizada e o veículo ficou indisponível.

### **2. Veículo ocupado**
Foi realizada uma tentativa de criar uma segunda locação utilizando um veículo que já estava alugado.

**Resultado:** o sistema impediu a locação e apresentou uma exceção informando que o veículo estava indisponível.

### **3. Data inválida**
Foi criada uma tentativa de locação com a data final anterior à data inicial.

**Resultado:** o sistema rejeitou o contrato e apresentou uma exceção informando que as datas eram inválidas.

### **4. Cliente inválido**
Foi realizada uma tentativa de cadastro utilizando um nome vazio.

**Resultado:** o sistema rejeitou o cadastro e apresentou uma exceção de validação.

### **5. Devolução**
Após a finalização de uma locação, o veículo foi devolvido.

**Resultado:** o veículo voltou a ficar disponível.

### **6. Persistência**
Após registrar uma locação, o sistema salvou seus dados no arquivo `historico_locacoes.txt`.

**Resultado:** o histórico da locação foi armazenado no arquivo.
