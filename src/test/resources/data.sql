-- Seed data for time_deposits covering all business rules
INSERT INTO time_deposits (id, plan_type, days, balance) VALUES
(1, 'basic', 20, 1000.00),         -- basic <= 30 days: 0% interest
(2, 'basic', 45, 1234567.00),      -- basic > 30 days: 1% interest
(3, 'student', 25, 2000.00),       -- student <= 30 days: 0% interest
(4, 'student', 90, 5000.00),       -- student 31-365 days: 3% interest
(5, 'student', 400, 3000.00),      -- student > 365 days: 0% interest (after 1 year)
(6, 'premium', 35, 10000.00),      -- premium <= 45 days: 0% interest
(7, 'premium', 60, 50000.00)       -- premium > 45 days: 5% interest
ON CONFLICT (id) DO NOTHING;

-- Seed data for withdrawals
INSERT INTO withdrawals (id, time_deposit_id, amount, withdrawal_date) VALUES
(1, 2, 500.00, '2026-01-15T10:30:00Z'),
(2, 2, 1000.00, '2026-02-10T14:00:00Z'),
(3, 4, 250.00, '2026-03-01T09:15:00Z'),
(4, 7, 5000.00, '2026-03-15T16:45:00Z')
ON CONFLICT (id) DO NOTHING;

-- Align sequences with seeded IDs
SELECT setval(pg_get_serial_sequence('time_deposits', 'id'), coalesce(max(id), 1)) FROM time_deposits;
SELECT setval(pg_get_serial_sequence('withdrawals', 'id'), coalesce(max(id), 1)) FROM withdrawals;
