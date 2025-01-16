CREATE TRIGGER set_delivery_date_trigger
    BEFORE INSERT ON orders
    FOR EACH ROW
    EXECUTE FUNCTION set_delivery_date();