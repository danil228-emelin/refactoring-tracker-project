CREATE TRIGGER update_cargo_status_time_trigger
    BEFORE UPDATE
    ON cargo_status
    FOR EACH ROW
EXECUTE FUNCTION update_cargo_status_time();