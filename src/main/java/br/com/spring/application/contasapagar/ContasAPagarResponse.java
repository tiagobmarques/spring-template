package br.com.spring.application.contasapagar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Resposta com os detalhes da conta a pagar")
public class ContasAPagarResponse {

  @ApiModelProperty(value = "Identificador único da conta a pagar", example = "b50b8e2a-54d8-4d4f-8462-2bde9f1e7d29", required = true)
  private UUID id;

  @ApiModelProperty(value = "Data de vencimento da conta", example = "2024-08-01", required = true)
  private LocalDate dataVencimento;

  @ApiModelProperty(value = "Data de pagamento da conta", example = "2024-07-25")
  private LocalDate dataPagamento;

  @ApiModelProperty(value = "Valor da conta a pagar", example = "150.75", required = true)
  private BigDecimal valor;

  @ApiModelProperty(value = "Descrição da conta a pagar", example = "Compra de material de escritório")
  private String descricao;

  @ApiModelProperty(value = "Situação da conta (ex.: pendente, paga, vencida)", example = "pendente", required = true)
  private String situacao;
}