package br.com.spring.application.contasapagar;

import br.com.spring.infrastructure.contasapagar.ContasAPagarEntity;
import org.springframework.stereotype.Component;

@Component
public class ContasAPagarMapper {

  public ContasAPagarResponse toResponse(ContasAPagarEntity entity) {
    if (entity == null) {
      return null;
    }

    return ContasAPagarResponse.builder()
            .id(entity.getId())
            .dataVencimento(entity.getDataVencimento())
            .dataPagamento(entity.getDataPagamento())
            .valor(entity.getValor())
            .descricao(entity.getDescricao())
            .situacao(entity.getSituacao())
            .build();
  }

  public ContasAPagarEntity toEntity(ContasAPagarRequest request) {
    if (request == null) {
      return null;
    }

    return ContasAPagarEntity.builder()
            .dataVencimento(request.getDataVencimento())
            .dataPagamento(request.getDataPagamento())
            .valor(request.getValor())
            .descricao(request.getDescricao())
            .situacao(request.getSituacao())
            .build();
  }

  public ContasAPagarEntity toEntity(ContasAPagarEntity existingEntity, ContasAPagarRequest request) {
    if (existingEntity == null || request == null) {
      return null;
    }

    return ContasAPagarEntity.builder()
            .id(existingEntity.getId())
            .dataVencimento(request.getDataVencimento())
            .dataPagamento(request.getDataPagamento())
            .valor(request.getValor())
            .descricao(request.getDescricao())
            .situacao(request.getSituacao())
            .build();
  }

  public ContasAPagarEntity toEntitySituacao(ContasAPagarEntity existingEntity, ContasAPagarRequest request) {
    if (existingEntity == null || request == null) {
      return null;
    }

    return ContasAPagarEntity.builder()
            .id(existingEntity.getId())
            .dataVencimento(existingEntity.getDataVencimento())
            .dataPagamento(existingEntity.getDataPagamento())
            .valor(existingEntity.getValor())
            .descricao(existingEntity.getDescricao())
            .situacao(request.getSituacao())
            .build();
  }
}
