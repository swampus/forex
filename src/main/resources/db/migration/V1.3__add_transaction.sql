INSERT INTO transaction (block_identifier, account_id, amount, transaction_date, currency, status, type, executed, exchange_rate)
VALUES
  ('a1e73b11-35c0-4f7b-9b23-d6a46e61bd01', 1, 100.00, '2023-12-17 12:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', true, 1.0),
  ('c3d87f6d-2a52-4e6b-9a02-7c48b5b4ac11', 1, 150.00, '2023-12-17 12:30:00', 'EUR', 'DONE', 'CURRENCY_EXCHANGE', false, 0.95),
  ('d32bbaf7-139a-4d4a-b482-d3aa161e4d44', 1, 200.00, '2023-12-17 13:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', true, 1.1),
  ('f825688b-2bfc-45d4-b059-d442398d36d2', 1, 120.00, '2023-12-17 13:30:00', 'GBP', 'DONE', 'CURRENCY_EXCHANGE', true, 0.9),
  ('f4db127d-4aae-464b-bbc2-3b0f7a06c9df', 1, 180.00, '2023-12-17 14:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', false, 1.05),
  ('b44c9fe1-d37e-4fcd-afbf-b25e130c63b5', 1, 250.00, '2023-12-17 14:30:00', 'EUR', 'DONE', 'CURRENCY_EXCHANGE', true, 1.15),
  ('e1b35662-9977-42ab-aa1d-64ed6bb5fb6e', 1, 300.00, '2023-12-17 15:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', false, 1.2),
  ('b3f6428e-8d7f-4fe6-a700-9f6b2b4e450c', 1, 180.00, '2023-12-17 15:30:00', 'GBP', 'DONE', 'CURRENCY_EXCHANGE', true, 0.85),
  ('c8e7d190-9c06-4281-b456-1982d4b81abf', 1, 220.00, '2023-12-17 16:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', true, 1.25),
  ('f4e94f5f-b546-4dce-a82b-2bca2f6c59e4', 1, 130.00, '2023-12-17 16:30:00', 'EUR', 'DONE', 'CURRENCY_EXCHANGE', false, 0.98),
  ('a2f55e3e-2b63-40ee-a36d-83a9c3717e9b', 1, 180.00, '2023-12-17 17:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', true, 1.15),
  ('a395e3b9-3cfe-4702-af06-0d34de06d3e4', 1, 250.00, '2023-12-17 17:30:00', 'GBP', 'DONE', 'CURRENCY_EXCHANGE', true, 0.9),
  ('c0c071b7-7a2d-4e20-97a7-5a2b03d02f8d', 1, 300.00, '2023-12-17 18:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', false, 1.2),
  ('b5c56f69-54e2-4019-95a1-618b30ab6dcb', 1, 200.00, '2023-12-17 18:30:00', 'EUR', 'DONE', 'CURRENCY_EXCHANGE', true, 1.05),
  ('e5c6a70e-8f95-4f4b-b858-8fe17685bb17', 1, 150.00, '2023-12-17 19:00:00', 'USD', 'DONE', 'CURRENCY_EXCHANGE', true, 1.15),
  ('d8f37158-6f94-4a78-8f13-001422e96bf5', 1, 120.00, '2023-12-17 19:30:00', 'GBP', 'DONE', 'CURRENCY_EXCHANGE', false, 0.98);