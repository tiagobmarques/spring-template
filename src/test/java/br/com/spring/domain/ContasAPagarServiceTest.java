package br.com.spring.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.spring.domain.contasapagar.ContasAPagarEntity;
import br.com.spring.domain.contasapagar.ContasAPagarService;
import br.com.spring.infrastructure.contasapagar.ContasAPagarRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
class ContasAPagarServiceTest {

  @Mock
  private ContasAPagarRepository repository;

  @InjectMocks
  private ContasAPagarService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Deve salvar uma nova conta a pagar")
  void shouldSaveContasAPagar() {
    // Arrange
    ContasAPagarEntity entity = ContasAPagarEntity.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    given(repository.save(any(ContasAPagarEntity.class))).willReturn(entity);

    // Act
    ContasAPagarEntity result = service.save(entity);

    // Assert
    assertEquals(entity, result);
  }

  @Test
  @DisplayName("Deve retornar uma conta a pagar por ID")
  void shouldReturnContasAPagarById() {
    // Arrange
    UUID id = UUID.randomUUID();
    ContasAPagarEntity entity = ContasAPagarEntity.builder()
        .dataVencimento(LocalDate.of(2024, 1, 15))
        .dataPagamento(LocalDate.of(2024, 1, 10))
        .valor(new BigDecimal("1500.00"))
        .descricao("Compra de materiais")
        .situacao("Pago")
        .build();

    given(repository.findById(any())).willReturn(Optional.of(entity));

    // Act
    Optional<ContasAPagarEntity> result = service.getById(id);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(entity, result.get());
  }

  @Test
  @DisplayName("Deve retornar o valor total pago por período")
  void shouldReturnTotalPagoPorPeriodo() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 12, 31);
    List<ContasAPagarEntity> contas = Arrays.asList(
        ContasAPagarEntity.builder().valor(new BigDecimal("1500.00")).build(),
        ContasAPagarEntity.builder().valor(new BigDecimal("800.00")).build()
    );

    given(repository.findByDataPagamentoBetween(any(), any())).willReturn(contas);

    // Act
    BigDecimal result = service.getTotalPagoPorPeriodo(startDate, endDate);

    // Assert
    assertEquals(new BigDecimal("2300.00"), result);
  }

  @Test
  @DisplayName("Deve retornar contas paginadas por vencimento e descrição")
  void shouldReturnContasPaginadasPorVencimentoEDescricao() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 12, 31);
    String descricao = "materiais";
    Pageable pageable = Pageable.unpaged();
    List<ContasAPagarEntity> contas = Arrays.asList(
        ContasAPagarEntity.builder()
            .dataVencimento(LocalDate.of(2024, 1, 15))
            .descricao("Compra de materiais")
            .build()
    );
    Page<ContasAPagarEntity> page = new PageImpl<>(contas);

    given(repository.findByDataVencimentoBetweenAndDescricaoContaining(any(), any(), anyString(),
        any(Pageable.class)))
        .willReturn(page);

    // Act
    Page<ContasAPagarEntity> result = service.getContasPaginadasPorVencimentoEDescricao(startDate,
        endDate, descricao, pageable);

    // Assert
    assertEquals(page, result);
  }

  @Test
  @DisplayName("Deve importar contas a pagar de um arquivo CSV")
  void shouldImportContasAPagarFromCSV() throws Exception {
    // Arrange
    String csvContent = "dataVencimento,dataPagamento,valor,descricao,situacao\n" +
        "2024-01-15,2024-01-10,1500.00,Compra de materiais,Pago\n" +
        "2024-02-20,2024-01-10,1200.00,Serviço de limpeza,Pendente";

    MockMultipartFile file = new MockMultipartFile("file", "contas.csv", "text/csv",
        csvContent.getBytes());

    // Act
    service.importarCSV(file);

    // Assert
    verify(repository, times(1)).saveAll(any());
  }
}
