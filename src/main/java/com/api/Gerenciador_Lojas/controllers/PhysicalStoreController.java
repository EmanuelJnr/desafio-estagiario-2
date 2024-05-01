package com.api.Gerenciador_Lojas.controllers;

import com.api.Gerenciador_Lojas.dtos.PhysicalStoreRecordDTO;
import com.api.Gerenciador_Lojas.models.PhysicalStoreModel;
import com.api.Gerenciador_Lojas.repositories.PhysicalStoreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Optional;
import java.util.UUID;

//Anotação específica para uma API Rest
@RestController

//Anitação para permitir que seja acessado de qualquer fonte
@CrossOrigin(origins = "*", maxAge = 3600)

//Anotação responsável por definir a URI a nível de classe, poderia ser a nível de método
@RequestMapping("/physical-store")

//Classe que recebe solicitações Get, Push, Delete e Post e aciona a classe de repositório para transações com o BD
public class PhysicalStoreController {

    //Anotação para criar um ponto de injeção de dependência, poderia ser via construtor também
    @Autowired
    PhysicalStoreRepository physicalStoreRepository;

    //Anotação para receber requisições do tipo POST via HTTP, sem URI definida pois já está a nivel de classe
    @PostMapping

    //Método público com retorno ResponseEntity para Status e corpo da resposta, e Object para diferentes tipos de retorno
    //Parâmetro de entrada com a anotação RequestBody para receber um JSON e transformar em objeto DTO
    //Anotação Valid para que as validações dos atributos da classe DTO sejam feitas (NotBlank, NotNull)
    public ResponseEntity<Object> savePhysicalStore(@RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        //Uma condição para verificar se o telefone passado já existe no banco de dados
        if(physicalStoreRepository.existsByTelephone(physicalStoreRecordDTO.telephone())){
            //Retorna um Conflito para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Telephone is already in use!");
        }
        //Uma condição para verificar se o endereço passado já existe no banco de dados
        if(physicalStoreRepository.existsByPhysicalAddress(physicalStoreRecordDTO.physicalAddress())){
            //Retorna um Conflito para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Physical Address is already in use!");
        }
        //Cria-se um objeto Modelo vazio que possui um "id", para posteriormente guarda-lo no banco de dados
        var physicalStoreModel = new PhysicalStoreModel();
        //Recurso do Spring que converte o objeto recebido de DTO para Model preenchendo os dados dele
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        //O retorno consiste em duas partes, o status se tudo está certo e o corpo que reflete o que foi salvo no BD
        //Com o ponto de injeção, podemos utilizar do método save da classe de repositório para guardar os dados no BD
        return ResponseEntity.status(HttpStatus.CREATED).body(physicalStoreRepository.save(physicalStoreModel));
    }

    //Anotação para receber requisições do tipo GET via HTTP
    @GetMapping

    //Método público com retorno ResponseEntity já explicado acima, e Page para uma lista paginada
    //Parâmetro de entrada Pageable do pacote Spring Data, para receber um JSON e transformar em uma lista paginada
    public ResponseEntity<Page<PhysicalStoreModel>> getAllPhysicalStores(Pageable pageable){
        //Cria-se um objeto lista paginada, com os dados de todas as lojas físicas cadastradas
        Page<PhysicalStoreModel> physicalStoresList = physicalStoreRepository.findAll(pageable);
        //Uma condição para verificar se a lista criada acima não está vazia
        if(!physicalStoresList.isEmpty()) {
            //Laço de repetição para percorrer a lista criada
            for(PhysicalStoreModel physicalStoreModel : physicalStoresList) {
                //Cria-se uma variável para receber o "id" de cada elemento da lista
                UUID id = physicalStoreModel.getIdPhysicalStore();
                //Método add para construir o link, linkTo para direcionar o cliente para o endpoint da loja em questão
                //Método methodOn para indicar o método do redirecionamento, que será o getOnePhysicalStore desta mesma classe
                //Método withSelfRel que redireciona para cada um de seus produtos em si
                physicalStoreModel.add(linkTo(methodOn(PhysicalStoreController.class).getOnePhysicalStore(id, pageable)).withSelfRel());
            }
        }
        //O retorno tem duas partes, o status e o corpo que é uma lista paginada
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoresList);
    }

    //Anotação para receber requisições do tipo GET via HTTP, com URI definida para receber um "id"
    @GetMapping("/{idPhysicalStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idPhysicalStore
    //E outro parâmetro de entrada do tipo Pageable, para receber um JSON e transformar em uma lista paginada
    public ResponseEntity<Object> getOnePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore, Pageable pageable){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        //Uma condição para verificar se o objeto (loja física) criada acima não está presente no Banco de dados
        if (!physicalStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        //Cria-se um objeto lista paginada, com os dados de todas as lojas físicas cadastradas
        Page<PhysicalStoreModel> physicalStoresList = physicalStoreRepository.findAll(pageable);
        //Método methodOn para indicar o método do redirecionamento, que será o getAllPhysicalStores desta mesma classe
        //Método withSelfRel com uma mensagem
        physicalStoreModelOptional.get().add(linkTo(methodOn(PhysicalStoreController.class).getAllPhysicalStores(pageable)).withRel("Physical Stores List"));
        //O retorno tem duas partes, o status e o corpo que é um Objeto do tipo Optional
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoreModelOptional.get());
    }

    //Anotação para receber requisições do tipo DELETE via HTTP, com URI definida para receber um "id"
    @DeleteMapping("/{idPhysicalStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idPhysicalStore
    public ResponseEntity<Object> deletePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        //Uma condição para verificar se o objeto (loja física) criada acima não está presente no Banco de dados
        if (!physicalStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        //Chamada de método que deleta do banco de dados o cadastro da loja física
        physicalStoreRepository.delete(physicalStoreModelOptional.get());
        //Retorna o status de OK para o navegador web com uma mensagem
        return ResponseEntity.status(HttpStatus.OK).body(("Physical Store deleted successfully."));
    }

    //Anotação para receber requisições do tipo PUT via HTTP, com URI definida para receber um "id"
    @PutMapping("/{idPhysicalStore}")

    //Método público com retorno ResponseEntity, e Object para retornos distintos
    //Parâmetro de entrada com a anotação PathVariable, para receber o "id" passado na requisição HTTP que será atribuído na variável idPhysicalStore
    //E outro parâmetro de entrada com a anotação RequestBody para receber um JSON e transformar em objeto DTO
    //Anotação Valid para que as validações dos atributos da classe DTO sejam feitas
    public ResponseEntity<Object> updatePhysicalStore(@PathVariable(value = "idPhysicalStore") UUID idPhysicalStore,
                                                      @RequestBody @Valid PhysicalStoreRecordDTO physicalStoreRecordDTO){
        //Cria-se uma variável do tipo Optional para receber os dados correspondentes ao "id" passado e recuperados do BD
        Optional<PhysicalStoreModel> physicalStoreModelOptional = physicalStoreRepository.findById(idPhysicalStore);
        //Uma condição para verificar se o objeto (loja física) criada acima não está presente no Banco de dados
        if (!physicalStoreModelOptional.isPresent()) {
            //Retorna um status de não encontrado para o navegador web com uma mensagem
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Physical Store not found!");
        }
        //Cria-se um objeto Modelo vazio que possui um "id", para posteriormente guarda-lo no banco de dados
        var physicalStoreModel = new PhysicalStoreModel();
        //Recurso do Spring que converte o objeto recebido de DTO para Model preenchendo os dados dele
        BeanUtils.copyProperties(physicalStoreRecordDTO, physicalStoreModel);
        //Muda-se o parâmetro do atributo "idPhysicalStore" do novo objeto para o objeto recuperado do banco de dados
        physicalStoreModel.setIdPhysicalStore(physicalStoreModelOptional.get().getIdPhysicalStore());
        //O retorno consiste em duas partes, o status se tudo está certo e o corpo que reflete o que foi salvo no BD
        //Com o ponto de injeção, podemos utilizar do método save da classe de repositório para guardar os dados no BD
        return ResponseEntity.status(HttpStatus.OK).body(physicalStoreRepository.save(physicalStoreModel));
    }
}