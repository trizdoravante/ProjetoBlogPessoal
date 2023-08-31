USE db_blogpessoal;

INSERT INTO tb_postagens (data, texto, titulo)
VALUES (current_timestamp(), 'Texto da Postagem 01', 'Postagem 01');
INSERT INTO tb_postagens (data,texto,titulo)
VALUES (current_timestamp(), 'Texto da Postagem 02', 'Postagem 02');

SELECT * FROM tb_postagens;
