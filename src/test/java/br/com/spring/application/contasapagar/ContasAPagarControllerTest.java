package br.com.spring.application.contasapagar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.spring.infrastructure.contasapagar.ContasAPagarEntity;
import br.com.spring.domain.contasapagar.ContasAPagarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(ContasAPagarController.class)
class ContasAPagarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ContasAPagarService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao salvar uma nova conta a pagar")
  void shouldReturnHttpStatusWhenSavingNewContasAPagar() throws Exception {
    ContasAPagarRequest request = ContasAPagarRequest.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    ContasAPagarEntity entity = ContasAPagarEntity.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    when(service.save(any(ContasAPagarEntity.class))).thenReturn(entity);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .post("/contasapagar")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataVencimento").value("2024-01-15"))
        .andExpect(jsonPath("$.dataPagamento").value("2024-01-10"))
        .andExpect(jsonPath("$.valor").value("1500.0"))
        .andExpect(jsonPath("$.descricao").value("Compra de materiais"))
        .andExpect(jsonPath("$.situacao").value("Pago"));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao atualizar uma conta a pagar existente")
  void shouldReturnHttpStatusWhenUpdatingContasAPagar() throws Exception {
    UUID id = UUID.randomUUID();
    ContasAPagarRequest request = ContasAPagarRequest.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    ContasAPagarEntity existingEntity = ContasAPagarEntity.builder()
        .id(id)
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    ContasAPagarEntity updatedEntity = ContasAPagarEntity.builder()
        .id(id)
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    when(service.getById(id)).thenReturn(Optional.of(existingEntity));
    when(service.save(any())).thenReturn(updatedEntity);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .put("/contasapagar/{id}", id)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataVencimento").value("2024-01-15"))
        .andExpect(jsonPath("$.dataPagamento").value("2024-01-10"))
        .andExpect(jsonPath("$.valor").value("1500.0"))
        .andExpect(jsonPath("$.descricao").value("Compra de materiais"))
        .andExpect(jsonPath("$.situacao").value("Pago"));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao atualizar a situação de uma conta a pagar")
  void shouldReturnHttpStatusWhenUpdatingContasAPagarSituacao() throws Exception {
    UUID id = UUID.randomUUID();
    ContasAPagarRequest request = ContasAPagarRequest.builder()
        .situacao("Pendente")
        .build();

    ContasAPagarEntity existingEntity = ContasAPagarEntity.builder()
        .id(id)
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    ContasAPagarEntity updatedEntity = ContasAPagarEntity.builder()
        .id(id)
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pendente")
        .build();

    when(service.getById(id)).thenReturn(Optional.of(existingEntity));
    when(service.save(any())).thenReturn(updatedEntity);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .put("/contasapagar/{id}/situacao", id)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataVencimento").value("2024-01-15"))
        .andExpect(jsonPath("$.dataPagamento").value("2024-01-10"))
        .andExpect(jsonPath("$.valor").value("1500.0"))
        .andExpect(jsonPath("$.descricao").value("Compra de materiais"))
        .andExpect(jsonPath("$.situacao").value("Pendente"));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao obter uma conta a pagar pelo ID")
  void shouldReturnHttpStatusWhenGettingContaAPagarById() throws Exception {
    UUID id = UUID.randomUUID();
    ContasAPagarEntity entity = ContasAPagarEntity.builder()
        .id(id)
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    when(service.getById(id)).thenReturn(Optional.of(entity));

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .get("/contasapagar/{id}", id)
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataVencimento").value("2024-01-15"))
        .andExpect(jsonPath("$.dataPagamento").value("2024-01-10"))
        .andExpect(jsonPath("$.valor").value("1500.0"))
        .andExpect(jsonPath("$.descricao").value("Compra de materiais"))
        .andExpect(jsonPath("$.situacao").value("Pago"));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao obter o valor total pago por período")
  void shouldReturnHttpStatusWhenGettingTotalPagoPorPeriodo() throws Exception {
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 12, 31);
    BigDecimal totalPago = new BigDecimal("15000.00");

    when(service.getTotalPagoPorPeriodo(startDate, endDate)).thenReturn(totalPago);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .get("/contasapagar/total-pago")
        .param("startDate", startDate.toString())
        .param("endDate", endDate.toString())
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(totalPago.toString()));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao obter contas a pagar paginadas por vencimento e descrição")
  void shouldReturnHttpStatusWhenGettingContasPaginadasPorVencimentoEDescricao() throws Exception {
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 12, 31);
    String descricao = "Compra";

    ContasAPagarEntity entity = ContasAPagarEntity.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    Pageable pageable = PageRequest.of(0, 10);
    Page<ContasAPagarEntity> pagedResult = new PageImpl<>(Collections.singletonList(entity),
        pageable, 1);

    when(service.getContasPaginadasPorVencimentoEDescricao(startDate, endDate, descricao,
        pageable)).thenReturn(pagedResult);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .get("/contasapagar")
        .param("startDate", startDate.toString())
        .param("endDate", endDate.toString())
        .param("descricao", descricao)
        .param("page", "0")
        .param("size", "10")
        .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].dataVencimento").value("2024-01-15"))
        .andExpect(jsonPath("$.content[0].dataPagamento").value("2024-01-10"))
        .andExpect(jsonPath("$.content[0].valor").value("1500.0"))
        .andExpect(jsonPath("$.content[0].descricao").value("Compra de materiais"))
        .andExpect(jsonPath("$.content[0].situacao").value("Pago"))
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.number").value(0))
        .andExpect(jsonPath("$.size").value(10));
  }

  @Test
  @DisplayName("Deve retornar HTTP Status Code 200 ao importar contas a pagar de um arquivo CSV")
  void shouldReturnHttpStatusWhenImportingContasAPagarFromCSV() throws Exception {
    String csvContent = "dataVencimento,dataPagamento,valor,descricao,situacao\n" +
        "2024-01-15,2024-01-10,1500.00,Compra de materiais,Pago\n" +
        "2024-02-20,,1200.00,Serviço de limpeza,Pendente";

    MockMultipartFile file = new MockMultipartFile(
        "file",
        "contas.csv",
        MediaType.TEXT_PLAIN_VALUE,
        csvContent.getBytes()
    );

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/contasapagar/importar")
            .file(file)
            .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk());

    verify(service, times(1)).importarCSV(any(MultipartFile.class));
  }
}
