package br.com.spring.application.contasapagar;

import br.com.spring.domain.contasapagar.ContasAPagarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
    mappingControl = DeepClone.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContasAPagarMapper {

  ContasAPagarMapper INSTANCE = Mappers.getMapper(ContasAPagarMapper.class);

  ContasAPagarResponse toResponse(ContasAPagarEntity contasAPagarEntity);

  ContasAPagarEntity toEntity(ContasAPagarRequest contasAPagarRequest);

  @Mapping(source = "contasAPagarEntity.id", target = "id")
  @Mapping(source = "contasAPagarRequest.dataVencimento", target = "dataVencimento")
  @Mapping(source = "contasAPagarRequest.dataPagamento", target = "dataPagamento")
  @Mapping(source = "contasAPagarRequest.valor", target = "valor")
  @Mapping(source = "contasAPagarRequest.descricao", target = "descricao")
  @Mapping(source = "contasAPagarRequest.situacao", target = "situacao")
  ContasAPagarEntity toEntity(ContasAPagarEntity contasAPagarEntity, ContasAPagarRequest contasAPagarRequest);

  @Mapping(source = "contasAPagarEntity.id", target = "id")
  @Mapping(source = "contasAPagarEntity.dataVencimento", target = "dataVencimento")
  @Mapping(source = "contasAPagarEntity.dataPagamento", target = "dataPagamento")
  @Mapping(source = "contasAPagarEntity.valor", target = "valor")
  @Mapping(source = "contasAPagarEntity.descricao", target = "descricao")
  @Mapping(source = "contasAPagarRequest.situacao", target = "situacao")
  ContasAPagarEntity toEntitySituacao(ContasAPagarEntity contasAPagarEntity, ContasAPagarRequest contasAPagarRequest);

}
