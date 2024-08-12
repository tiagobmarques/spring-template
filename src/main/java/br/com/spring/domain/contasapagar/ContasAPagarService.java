package br.com.spring.domain.contasapagar;

import br.com.spring.infrastructure.contasapagar.ContasAPagarRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContasAPagarService {

  @Autowired
  private ContasAPagarRepository repository;

  public ContasAPagarEntity save(final ContasAPagarEntity contasAPagarEntity) {
    return repository.save(contasAPagarEntity);
  }

  public Optional<ContasAPagarEntity> getById(final UUID id) {
    return repository.findById(id);
  }

  public BigDecimal getTotalPagoPorPeriodo(final LocalDate startDate, final LocalDate endDate) {
    List<ContasAPagarEntity> contas = repository.findByDataPagamentoBetween(startDate, endDate);

    return contas.stream()
        .map(ContasAPagarEntity::getValor)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Page<ContasAPagarEntity> getContasPaginadasPorVencimentoEDescricao(
      final LocalDate startDate,
      final LocalDate endDate, final String descricao, final Pageable pageable) {
    return repository.findByDataVencimentoBetweenAndDescricaoContaining(startDate, endDate,
        descricao, pageable);
  }

  public void importarCSV(MultipartFile file) throws IOException {
    List<ContasAPagarEntity> entities = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      String line;
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");

        ContasAPagarEntity entity = ContasAPagarEntity.builder()
            .dataVencimento(LocalDate.parse(fields[0]))
            .dataPagamento(LocalDate.parse(fields[1]))
            .valor(new BigDecimal(fields[2]))
            .descricao(fields[3])
            .situacao(fields[4])
            .build();

        entities.add(entity);
      }
    }

    repository.saveAll(entities);
  }
}
