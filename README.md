<h1>Visão Geral</h1>
O projeto é uma aplicação modularizada, desenvolvida para ganhar escalabilidade e facilitar a manutenção do código. Ele é dividido em três módulos principais: app, design system e data-core. O projeto utiliza tecnologia de persistência com Room, integrado com o Paging 3 para garantir uma única fonte de verdade (single source of truth) em relação aos dados. A arquitetura utilizada é o MVVM (Model-View-ViewModel), que separa claramente as responsabilidades e promove uma melhor organização do código. Além disso, o projeto inclui testes unitários para garantir a qualidade do código.

<h3>Premissas e considerações</h3>

Para contextualizar algumas decisões tomadas durante o desenvolvimento, vou expor o processo do meu pensamento:

  1. Em vez de criar uma tela exclusiva para detalhes, optei por criar um CardView que se expande ao clicar. Como havia poucas informações a serem exibidas, acredito que essa solução traz uma boa experiência para o usuário. No entanto, caso seja necessário exibir mais informações na tela, idealmente criaria uma tela de detalhes que seria acessada ao clicar no card.
 
  2. Decidi utilizar o conceito de Single Source of Truth no aplicativo. Com uma única fonte de verdade, consigo manter a consistência dos meus dados e proporcionar uma experiência de sistema de persistência mais fluida. Para alcançar isso, utilizei a biblioteca Room e o Paging 3. Com essa implementação, a tela busca os próximos dados paginados no banco, e quando não há mais dados novos no banco, ele busca na API e os persiste.



<h1>Módulos</h1>
<h3>1. App</h3>
   Este módulo contém a lógica principal da aplicação, incluindo a interface do usuário, a implementação da arquitetura MVVM e a lógica de apresentação. Ele se comunica com os outros módulos para acessar dados e componentes de design.

<h3>2. Design System</h3>
   O módulo design system contém componentes de design reutilizáveis, estilos e temas que são compartilhados em toda a aplicação. Ele promove a consistência visual e facilita a manutenção da interface do usuário.

<h3>3. Data-Core</h3>
   O módulo data-core é responsável pelo acesso aos dados e pela lógica de negócios relacionada aos dados. Ele utiliza o Room para persistência local e o Paging 3 para fornecer uma experiência de rolagem eficiente e uma única fonte de verdade para os dados da aplicação.
   
<h1>Tecnologias Principais</h1>
<h3>Room:</h3> Biblioteca de persistência de dados para armazenamento local.
<h3>Paging 3:</h3> Biblioteca para carregamento eficiente de grandes conjuntos de dados.
<h3>Koin:</h3> Biblioteca para a injeção de dependências com sintáxe fácil
<h3>Mockk:</h3> Biblioteca mara geração de mockk's
<h3>Robolectric:</h3> Biblioteca para emular o sistema android na JVM
<h3>Arquitetura MVVM:</h3> Separação clara entre a lógica de negócios e a interface do usuário.

<h1>Executando o Projeto</h1>
Como o projeto se utiliza de chaves de desenvolvedor MARVEL para conseguir fazer comunicação com as API's, é necessário criar um arquivo 'keys.properties' no root do projeto:

```
PUBLIC_KEY="<sua chave publica>"
PRIVATE_KEY="<sua chave privada>"
```

<h1>Executando os Testes</h1>
Esse projeto conta com testes unitários para a garantia de regras e implementações. Para rodar os testes basta utilizar o seguinte comando:

```
./gradlew test --parallel
```


