package br.com.spring.infrastructure.contasapagar;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contas_a_pagar")
public class ContasAPagarEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "data_vencimento", nullable = false)
  private LocalDate dataVencimento;

  @Column(name = "data_pagamento")
  private LocalDate dataPagamento;

  @Column(name = "valor", nullable = false)
  private BigDecimal valor;

  @Column(name = "descricao")
  private String descricao;

  @Column(name = "situacao", nullable = false)
  private String situacao;
}