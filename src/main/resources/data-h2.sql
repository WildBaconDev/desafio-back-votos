insert into pauta (id, assunto, corpo, dh_criacao, st_contabilizacao) values (1, 'assunto', 'corpo', SYSDATE(), 'NAO_CONTABILIZADO');
insert into pauta (id, assunto, corpo, dh_criacao, st_contabilizacao) values (2, 'Pauta sem sessao', 'corpo', SYSDATE(), 'NAO_CONTABILIZADO');
--insert into sessao values (1, SYSDATE(), SYSDATE() + interval '30' minute, 1);
insert into sessao (id, dh_criacao, dh_fechamento, id_pauta) values (1, SYSDATE(), DATEADD(MINUTE, 20, LOCALTIMESTAMP), 1);
insert into voto values (1, SYSDATE(), 1, 1, 1);