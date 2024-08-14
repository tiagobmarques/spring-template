package br.com.spring.application.contasapagar;

import br.com.spring.infrastructure.contasapagar.ContasAPagarEntity;
import br.com.spring.domain.contasapagar.ContasAPagarService;
import br.com.spring.infrastructure.contasapagar.ContasAPagarRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contasapagar")
@RequiredArgsConstructor
public class ContasAPagarController {

  private final ContasAPagarService service;
  private final ContasAPagarRepository repository;
  private final ContasAPagarMapper mapper;

  @ApiOperation(value = "Salva uma nova conta a pagar")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Conta a pagar salva com sucesso", response = ContasAPagarResponse.class),
      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @PostMapping
  public ContasAPagarResponse salvarContasAPagar(
      @RequestBody @ApiParam(value = "Dados da conta a pagar") ContasAPagarRequest contasAPagarRequest) {

    ContasAPagarEntity entity = mapper.toEntity(contasAPagarRequest);

    return mapper.toResponse(service.save(entity));
  }

  @ApiOperation(value = "Atualiza uma conta a pagar existente")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Conta a pagar atualizada com sucesso", response = ContasAPagarResponse.class),
      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
      @ApiResponse(code = 404, message = "Conta a pagar não encontrada"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @PutMapping("/{id}")
  public ContasAPagarResponse updateContasAPagar(
      @PathVariable @ApiParam(value = "ID da conta a pagar") UUID id,
      @RequestBody @ApiParam(value = "Dados da conta a pagar") ContasAPagarRequest request) {

    ContasAPagarEntity existingEntity = service.getById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Code not found"));

    ContasAPagarEntity entity = mapper.toEntity(existingEntity, request);
    return mapper.toResponse(service.save(entity));
  }

  @ApiOperation(value = "Atualiza a situação de uma conta a pagar")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Situação da conta a pagar atualizada com sucesso", response = ContasAPagarResponse.class),
      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
      @ApiResponse(code = 404, message = "Conta a pagar não encontrada"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @PutMapping("/{id}/situacao")
  public ContasAPagarResponse updateContasAPagarSituacao(
      @PathVariable @ApiParam(value = "ID da conta a pagar") UUID id,
      @RequestBody @ApiParam(value = "Dados da situação da conta a pagar") ContasAPagarRequest request) {

    ContasAPagarEntity existingEntity = service.getById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Code not found"));

    ContasAPagarEntity entity = mapper.toEntitySituacao(existingEntity, request);
    return mapper.toResponse(service.save(entity));
  }

  @ApiOperation(value = "Obtém uma conta a pagar pelo ID")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Conta a pagar encontrada", response = ContasAPagarResponse.class),
      @ApiResponse(code = 404, message = "Conta a pagar não encontrada"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @GetMapping("/{id}")
  public ContasAPagarResponse getContaAPagarById(
      @PathVariable @ApiParam(value = "ID da conta a pagar", required = true) UUID id) {

    ContasAPagarEntity existingEntity = service.getById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Code not found"));

    return mapper.toResponse(existingEntity);
  }

  @ApiOperation(value = "Obtém o valor total pago por período")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valor total pago obtido com sucesso", response = BigDecimal.class),
      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @GetMapping("/total-pago")
  public BigDecimal getTotalPagoPorPeriodo(
      @RequestParam @ApiParam(value = "Data inicial do período", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @ApiParam(value = "Data final do período", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    return service.getTotalPagoPorPeriodo(startDate, endDate);
  }

//  @ApiOperation(value = "Obtém contas a pagar paginadas por vencimento e descrição")
//  @ApiResponses(value = {
//      @ApiResponse(code = 200, message = "Contas pagas obtidas com sucesso", response = Page.class),
//      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
//      @ApiResponse(code = 500, message = "Erro interno do servidor")
//  })
//  @GetMapping
//  public Page<ContasAPagarResponse> getContasPaginadasPorVencimentoEDescricao(
//      @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//      @RequestParam(value = "descricao", required = false, defaultValue = "") String descricao,
//      @RequestParam(value = "page", defaultValue = "0") int page,
//      @RequestParam(value = "size", defaultValue = "10") int size) {
//
//    Pageable pageable = PageRequest.of(page, size);
//    Page<ContasAPagarEntity> contas = service.getContasPaginadasPorVencimentoEDescricao(startDate,
//        endDate, descricao, pageable);
//
//    return contas.map(ContasAPagarMapper.toResponse());
//  }

  @ApiOperation(value = "Importa contas a pagar de um arquivo CSV")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contas a pagar importadas com sucesso"),
      @ApiResponse(code = 400, message = "Dados inválidos fornecidos"),
      @ApiResponse(code = 500, message = "Erro interno do servidor")
  })
  @PostMapping("/importar")
  public void importarCSV(
      @RequestParam("file") MultipartFile file) throws IOException {
    service.importarCSV(file);
  }

}
