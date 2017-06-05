
-- INFO CLIENTE
SELECT * FROM tbl_client;

-- Obtener CTS
SELECT
  cts.ammount
FROM  tbl_account account
INNER JOIN tbl_account_cts cts ON (cts.account_id = account.id)
INNER JOIN tbl_client client ON (client.id = account.client_id)
WHERE account.type = 'CTS' AND client.code = :codigoCliente;

-- Obtener Listado de Tarjetas activas
SELECT
  card.number,
  card.balance_available,
  card.balance_used,
  card.type
FROM tbl_account_card card
INNER JOIN tbl_account account ON (card.account_id = account.id)
INNER JOIN tbl_client client ON (client.id = account.client_id)
WHERE client.code = :codigoCliente AND card.active = true;

-- Obtener Listado de transacciones por tarjeta/cuenta
SELECT
  tx.detail,
  tx.amount,
  tx.currency,
  txt.name,
  TO_CHAR(tx.date_transaction, 'YYYY-MM-dd hh:ii:ss') as date
FROM tbl_transaction tx
INNER JOIN  tbl_account account ON (account.id = tx.account_id)
INNER JOIN  tbl_transaction_type txt ON (txt.id = tx.transaction_type_id)
INNER JOIN  tbl_client client ON ( client.id = account.client_id )
WHERE account.client_id = 1
