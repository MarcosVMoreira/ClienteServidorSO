# Sockets e multithread

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estudo dirigido feito em `Java` na disciplina de **Sistemas Operacionais II** do curso de [Engenharia de Computação](https://pcs.ifsuldeminas.edu.br/index.php?option=com_content&view=article&id=639&Itemid=267) do [IFSULDEMINAS - Campus Poços de Caldas](https://pcs.ifsuldeminas.edu.br/). <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Utilizando *sockets* e *multithread*, assim como interface gráfica, o objetivo do projeto foi desenvolver um *software* similar ao encontrado no jogo **Gartic**, onde um usuário pode realizar desenhos na sua própria tela e os outros usuários conectados ao mesmo servidor conseguem visualizar tal desenho em tempo real. Foi utilizada a biblioteca Graphics2D para desenvolvimento da parte gráfica.

# Utilização
1. Execute o arquivo **DeitelMessengerServerTest.java**. Tal arquivo tem a finalidade de subir o servidor que escutará os usuários.
2. Execute o arquivo **DeitelMessenger.java** quantas vezes achar necessário para criar clientes. Utilize a aba File>Conectar.
3. Faça os desenhos que preferir em um cliente e clique em enviar. Também é possível utilizar borracha para apagar os desenhos, além de mudar a cor do pincel.
4. Perceba que o desenho foi replicado nos outros clientes.

# Imagens do Software

<p align = "center">
  <img src="https://raw.githubusercontent.com/MarcosVMoreira/SocketsMultithreadSO/master/Imagens%20do%20sistema/Sistema.jpeg" alt="Sistema" width="800"/>
</p>      <br>
<p align = "center">
  <img src="https://raw.githubusercontent.com/MarcosVMoreira/SocketsMultithreadSO/master/Imagens%20do%20sistema/Estrutura.png" alt="Estrutura do sistema" width="500"/>
</p>     <br> 


# Pré-Requisitos

- Java 8 ou superior.
- JDK 10.0 ou superior.

# Dependências

- Graphics2D

# Instalação

Basta clonar/baixar os arquivos do repositório e abri-los utilizando NetBeans IDE.

# Equipe

* **Marcos Vinícius Moreira** - *Desenvolvedor do Software* - [MarcosVMoreira](https://github.com/MarcosVMoreira)
* **Otávio Palma** - *Desenvolvedor do Software* - [OtavioPalma](https://github.com/OtavioPalma)
