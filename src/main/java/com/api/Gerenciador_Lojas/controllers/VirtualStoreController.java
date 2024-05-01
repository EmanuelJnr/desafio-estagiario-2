package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.VirtualStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.VirtualStoreModel;
import com.api.Gerenciador_Lojas.repositories.VirtualStoreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Anotação específica para uma API Rest
@RestController

//Anitação para permitir que seja acessado de qualquer fonte
@CrossOrigin(origins = "*", maxAge = 3600)

//Anotação responsável por definir a URI a nível de classe, poderia ser a nível de método
@RequestMapping("/virtual-store")

//Classe que recebe solicitações Get, Push, Delete e Post e aciona a classe de repositório para transações com o BD
public class VirtualStoreController {

    //Anotação para criar um ponto de injeção de dependência, poderia ser via construtor também
    @Autowired
    VirtualStoreRepository virtualStoreRepository;

    //Anotação para receber requisições do tipo POST via HTTP, sem URI definida pois já está a nivel de classe
    @PostMapping

    //Método público com retorno ResponseEntity para Status e corpo da resposta, e Object para diferentes tipos de retorno
    //Parâmetro de entrada com a anotação RequestBody para receber um JSON e transformar em objeto DTO
    //Anotação Valid para que as validações dos atributos da classe DTO sejam feitas (NotBlank)
    public ResponseEntity<Object> saveVirtualStore(@RequestBody @Valid VirtualStoreRecordDTO virtualStoreRecordDTO){
        //Uma condição para verificar se o nome passado já existe no banco de dados
        if(virtualStoreRepository.existsByName(virtualStoreRecordDTO.name())){
            //Retorna um Conflito para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Name is already in use!");
        }
        //Uma condição para verificar se o telefone passado já existe no banco de dados
        if(virtualStoreRepository.existsByTelephone(virtualStoreRecordDTO.telephone())){
            //Retorna um Conflito para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Telephone is already in use!");
        }
        //Uma condição para verificar se a URL passada já existe no banco de dados
        if(virtualStoreRepository.existsByURL(virtualStoreRecordDTO.URL())){
            //Retorna um Conflito para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: URL is already in use!");
        }
        //Cria-se um objeto Modelo vazio que possui um "id", para posteriormente guarda-lo no banco de dados
        var virtualStoreModel = new VirtualStoreModel();
        //Recurso do Spring que converte o objeto recebido de DTO para Model preenchendo os dados dele
        BeanUtils.copyProperties(virtualStoreRecordDTO, virtualStoreModel);
        //O retorno consiste em duas partes, o status se tudo está certo e o corpo que reflete o que foi salvo no BD
        //Com o ponto de injeção, podemos utilizar do método save da classe de repositório para guardar os dados no BD
        return ResponseEntity.status(HttpStatus.CREATED).body(virtualStoreRepository.save(virtualStoreModel));
    }

    //Anotação para receber requisições do tipo GET via HTTP
    @GetMapping

    //Método público com retorno ResponseEntity já explicado acima, e Page para uma lista paginada
    //Parâmetro de entrada Pageable do pacote Spring Data, para receber um JSON e transformar em uma lista paginada
    public ResponseEntity<Page<VirtualStoreModel>> getAllVirtualStores(Pageable pageable){
        //Cria-se um objeto lista paginada, com os dados de todas as lojas virtuais cadastradas
        Page<VirtualStoreModel> virtualStoresList = virtualStoreRepository.findAll(pageable);
        //Uma condição para verificar se a lista criada acima não está vazia
        if(!virtualStoresList.isEmpty()) {
            //Laço de repetição para percorrer a lista criada
            for(VirtualStoreModel virtualStoreModel : virtualStoresList) {
                //Cria-se uma variável para receber o "id" de cada elemento da lista
                UUID id = virtualStoreModel.getIdVirtualStore();
                //Método add para construir o link, linkTo para direcionar o cliente para o endpoint da loja em questão
                //Método methodOn para indicar o método do redirecionamento, que será o getOneVirtualStore desta mesma classe
                //Método withSelfRel que redireciona para cada um de seus produtos em si
                virtualStoreModel.add(linkTo(methodOn(VirtualStoreController.class).getOneVirtualStore(id, pageable)).withSelfRel());
            }
        }
        //O retorno tem duas partes, o status e o corpo que é uma lista paginada
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoresList);
    }

    //Anotação para receber requisições do tipo GET via HTTP, com URI definida para receber um "id"
    @GetMapping("/{idVirtualStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idVirtualStore
    //E outro parâmetro de entrada do tipo Pageable, para receber um JSON e transformar em uma lista paginada
    public ResponseEntity<Object> getOneVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore, Pageable pageable){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        //Uma condição para verificar se o objeto (loja virtual) criada acima não está presente no Banco de dados
        if (!virtualStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        //Cria-se um objeto lista paginada, com os dados de todas as lojas virtuais cadastradas
        Page<VirtualStoreModel> virtualStoresList = virtualStoreRepository.findAll(pageable);
        //Método methodOn para indicar o método do redirecionamento, que será o getAllVirtualStores desta mesma classe
        //Método withSelfRel com uma mensagem
        virtualStoreModelOptional.get().add(linkTo(methodOn(VirtualStoreController.class).getAllVirtualStores(pageable)).withRel("Virtual Stores List"));
        //O retorno tem duas partes, o status e o corpo que é um Objeto do tipo Optional
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoreModelOptional.get());
    }

    //Anotação para receber requisições do tipo DELETE via HTTP, com URI definida para receber um "id"
    @DeleteMapping("/{idVirtualStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idVirtualStore
    public ResponseEntity<Object> deleteVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        //Uma condição para verificar se o objeto (loja virtual) criada acima não está presente no Banco de dados
        if (!virtualStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        //Chamada de método que deleta do banco de dados o cadastro da loja virtual
        virtualStoreRepository.delete(virtualStoreModelOptional.get());
        //Retorna o status de OK para o navegador web com uma mensagem
        return ResponseEntity.status(HttpStatus.OK).body(("Virtual Store deleted successfully."));
    }

    //Anotação para receber requisições do tipo PUT via HTTP, com URI definida para receber um "id"
    @PutMapping("/{idVirtualStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idVirtualStore
    //E outro parâmetro de entrada com a anotação RequestBody para receber um JSON e transformar em objeto DTO
    //Anotação Valid para que as validações dos atributos da classe DTO sejam feitas
    public ResponseEntity<Object> updateVirtualStore(@PathVariable(value = "idVirtualStore") UUID idVirtualStore,
                                                      @RequestBody @Valid VirtualStoreRecordDTO virtualStoreRecordDTO){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<VirtualStoreModel> virtualStoreModelOptional = virtualStoreRepository.findById(idVirtualStore);
        //Uma condição para verificar se o objeto (loja virtual) criada acima não está presente no Banco de dados
        if (!virtualStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Store not found!");
        }
        //Cria-se um objeto Modelo vazio que possui um "id", para posteriormente guarda-lo no banco de dados
        var virtualStoreModel = new VirtualStoreModel();
        //Recurso do Spring que converte o objeto recebido de DTO para Model preenchendo os dados dele
        BeanUtils.copyProperties(virtualStoreRecordDTO, virtualStoreModel);
        //Muda-se o parâmetro do atributo "idVirtualStore" do novo objeto para o objeto recuperado do banco de dados
        virtualStoreModel.setIdVirtualStore(virtualStoreModelOptional.get().getIdVirtualStore());
        //O retorno consiste em duas partes, o status se tudo está certo e o corpo que reflete o que foi salvo no BD
        //Com o ponto de injeção, podemos utilizar do método save da classe de repositório para guardar os dados no BD
        return ResponseEntity.status(HttpStatus.OK).body(virtualStoreRepository.save(virtualStoreModel));
    }
}