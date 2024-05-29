ALTER TABLE cart_item ADD COLUMN color_id bigint;

ALTER TABLE cart_item ADD CONSTRAINT color_fk FOREIGN KEY (color_id)
                                             REFERENCES public.color (id) MATCH SIMPLE
                                             ON UPDATE NO ACTION
                                             ON DELETE NO ACTION;