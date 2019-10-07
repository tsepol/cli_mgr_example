# cli_mgr_example

##Desenho Funcional

###Armazenamento de dados
O Sistema descrito armazena dados em tabelas, guardando sobre todos os registos a data de
criação, data da última atualização, bem como se este registo está ativo ou não.
De forma a evitar a remoção por engano ou propositada de dados críticos, nenhum dado será
removível pela aplicação, sendo estes dados removidos da vista da aplicação mas presentes
nas tabelas.
A remoção efetiva de dados será feita unicamente por acesso às tabelas.
####Cliente
São armazenados os seguintes dados de cada cliente:
* NIF - Número de identificação fiscal do cliente, número único, contendo exatamente 9
dígitos;
* Nome - Nome do cliente, podendo ser repetido noutros clientes, limitado a letras
(incluindo acentos) e espaços brancos;
* Telemóvel - Número do cliente, podendo ser repetido noutros clientes, limitado a 9
dígitos e começando por 9 ou 2;
* Morada - Morada do cliente, podendo ser repetida noutros clientes, limitada a letras
(incluindo acentos), alguns caracteres especiais como a vírgula, espaço e hífen, bem
como números;

###Aplicação

A aplicação descrita terá a seguintes funções abaixo descritas.
####Adicionar um novo cliente
A SiGO será capaz de, introduzindo os dados acima, adicionar o novo cliente ao sistema,
validando que os dados são válidos.
Esta introdução pode ser feita de uma de duas formas:
* Inserção nova apenas se não existir;
* Inserção ou atualização automática do cliente caso já exista;
####Atualizar um cliente
É possível atualizar um cliente, no entanto isto apenas é possível para os parâmetros Nome,
Morada e Telefone. O NIF sendo único não poderá ser alterado (podendo no entanto ser criado
um novo registo com o novo NIF).
####Remover um cliente
A SiGO será capaz de, introduzindo o NIF do cliente, removê-lo do sistema.
Esta remoção será apenas de visibilidade, tendo a empresa capacidade de recuperar a
informação caso a necessite.
####Listar todos os clientes
A SiGO será capaz de listar todos os clientes ativos.
###Obter informação de um cliente a partir do NIF
Recebendo um NIF, a aplicação devolverá os dados do cliente em questão.
####Obter os clientes a partir de um nome
Recebendo um nome, a SiGO é capaz de retornar os utilizadores que:
* Tenham o nome exatamente igual;
* Contenham o nome em questão algures no seu respectivo nome.

##Desenho Técnico
A SiGO tem o seguinte desenho técnico
###Base de Dados
A Base de dados atualmente apenas comporta uma tabela, de clientes, estando a aplicação
preparado para novas tabelas através do uso do modeloBase, desta forma mantendo a
capacidade de “soft-delete” bem como registos de criação e atualização mais recente.

clients Restrições / Default
nif varchar(255) Primary Key Regex [0-9]{9}
phone varchar(255) Regex [92][0-9]{8}
address varchar(255) Regex [a-zA-Z\u00C0-\u017F 0-9%,.\-]
name varchar(255) Regex [a-zA-Z\u00C0-\u017F ]
createdAt timestamp
updatedAt timestamp
is_active boolean Default true

###Repositório
O repositório implementado tem algumas alterações para adaptar à capacidade de “soft-delete”
do sistema (nomeadamente deletes).
De resto utiliza uma implementação JpaRepository regular.
###Controller
O controlador foi desenvolvido como um RPC para utilizar a subsecção /clients do sistema com
as funções abaixo descritas. Todas as operações apenas retornam clientes ativos. Este
sistema pode eventualmente ser desenvolvido numa API REST.

####Operações GET
#####/clients/list

Lista todos os clientes atualmente ativos. Não recebe parâmetros

#####/clients/search?name=XYZ&amp;exact=true|false
Recebe um nome como parâmetro (?name=XYZ) e um segundo parâmetro opcional exact,
ambos no header.
Em caso de omissão o exact é considerado falso.
No caso do exact ser falso procura por nomes de clientes que contenham o texto introduzido.
Caso contrário procura por nomes que correspondem a 100% ao nome introduzido.

#####/clients/get/{nif}
Recebe um NIF {nif} como Variável e retorna a informação do cliente.
Operações POST
/clients/add?upsert=true|false
Recebe no corpo da mensagem um cliente, bem como um parâmetro opcional no header.
Por defeito considera-se o upsert como falso. Neste caso a aplicação verificar primeiro de
utilizador já existe antes de inserir, reportando o erro caso exista.
Caso o upsert esteja a true o client é sempre inserido, atualizando caso já exista.

####Operações PUT
#####/clients/update
Recebe no corpo da mensagem um cliente, atualizando o cliente caso exista.
Caso não existe retorna erro.

####Operações Delete
#####/cliente/delete/{nif}
Recebe um NIF {nif} como Variável e remove o cliente do sistema.
Este remoção é “soft” ie. o utilizador não é verdadeiramente apagado, apenas escondido da
perspetiva da API.
