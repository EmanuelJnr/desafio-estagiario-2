# desafio-estagiario-2
Resumo do problema: Precisamos manter um cadastro das lojas de nossos clientes para futuras consultas. Eles possuem lojas físicas e virtuais com informações diferentes entre elas. Quando precisamos de um contato de telefone ou endereço, temos que buscar nas pastas físicas de cadastros de clientes.

O que fazer para executar o projeto:
1-Primeiro, certificar que tenha instalado a ferramenta Spring Tools 4 na sua IDE seja ela Eclipse, Visual Studio Code, ou Theia, ou você também pode usar o Spring Tools Suite ou IntelliJ IDEA.

2-Baixar e instalar o programa pgAdmin 4, configurar um servidor local no seu computador, criar um dataBase com o nome de "Gerenciador-Lojas-db", um usuário com o nome de "postgres" e uma senha "150150".

3-Baixar o repositório, realizar o import do projeto, selecionando o arquivo "pom.xml".

4-Para executar o projeto você precisa inicializar a classe "GerenciadorLojasApplication" ou clikar no botão "Run" da sua IDE.

5-Baixe o programa Postman, feito isso, na tela inicial dele pressione as telcas Ctrl + O, clique em "files", depois selecione os arquivos "VirtualStore.postman_collection.json" e "PhysicalStore.postman_collection.json" que estão nesse repositório.

6-Com o Postman configurado, o banco de dados funcionando e o programa em execução na IDE, poderá realizar os devidos testes.

O que foi feito para este projeto:
Primeiramente, criei os arquivos iniciais do Spring no site "https://start.spring.io" com as devidas dependências: Spring Web, Spring Data JPA, PostgreSQL Driver e Validation.
Posteriormente, comecei a criar os pacotes (controllers, dtos, models, repositories) que conteriam as classes, criei o record PhysicalStoreRecordDTO para simplificar o POJO,
criei a classe ...Model que o objeto desta classe servirá para guardar no banco de dados, criei a interface de repositório extendendo JpaRepository para usar alguns métodos e que o Spring possa manipular os dados do BD,
feito isso, configurei o script application.properties para comunicação com o BD localhost, eu tinha criado a classe Service, porém como não haviam regras de negócio, achei melhor deletá-la,
logo apois isto, iniciei o método POST na classe ...Controller para ir testando se estava tudo "ok", criei o método GET ALL com paginação e o GET ID, depois o método DELETE e por fim da parte "Loja física" a adição de hiper links Hateoas.
Terminado a loja física, fiz os mesmos passos para a loja virtual, já que eram praticamente parecidas, ao final acrescentei os arquivos de configuração do Postman e comentários nas linhas dos códigos.
