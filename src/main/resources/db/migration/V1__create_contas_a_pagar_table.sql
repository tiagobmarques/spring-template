--------------------------------------------------------
-- Create generate uuid function
--------------------------------------------------------
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
------------------------------------------------------------------
-- Create contas_a_pagar table
------------------------------------------------------------------
DO
$$
  BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'contas_a_pagar') THEN
      CREATE TABLE contas_a_pagar (
        id UUID DEFAULT gen_random_uuid(),
        data_vencimento DATE NOT NULL,
        data_pagamento DATE,
        valor NUMERIC(15, 2) NOT NULL,
        descricao VARCHAR(255),
        situacao VARCHAR(50) NOT NULL,
        PRIMARY KEY (id)
      );
      COMMENT ON COLUMN contas_a_pagar.id IS 'Identificador único da conta a pagar';
      COMMENT ON COLUMN contas_a_pagar.data_vencimento IS 'Data de vencimento da conta';
      COMMENT ON COLUMN contas_a_pagar.data_pagamento IS 'Data de pagamento da conta';
      COMMENT ON COLUMN contas_a_pagar.valor IS 'Valor da conta a pagar';
      COMMENT ON COLUMN contas_a_pagar.descricao IS 'Descrição da conta a pagar';
      COMMENT ON COLUMN contas_a_pagar.situacao IS 'Situação da conta (ex.: pendente, paga, vencida)';
      RAISE INFO 'Tabela contas_a_pagar criada com sucesso';
    ELSE
      RAISE INFO 'Tabela contas_a_pagar já existe';
    END IF;
  END
$$;