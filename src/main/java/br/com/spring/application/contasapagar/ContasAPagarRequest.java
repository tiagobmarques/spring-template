package br.com.spring.application.contasapagar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Dados da conta a pagar")
public class ContasAPagarRequest {

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