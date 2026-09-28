CREATE TABLE IF NOT EXISTS time_deposits (
    id SERIAL PRIMARY KEY,
    plan_type VARCHAR(50) NOT NULL,
    days INTEGER NOT NULL,
    balance NUMERIC(19, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS withdrawals (
    id SERIAL PRIMARY KEY,
    time_deposit_id INTEGER NOT NULL REFERENCES time_deposits(id) ON DELETE CASCADE,
    amount NUMERIC(19, 2) NOT NULL,
    date TIMESTAMPTZ NOT NULL
);
