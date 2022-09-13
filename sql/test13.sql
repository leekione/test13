drop table product;
create table product(
  pid number(3) ,
  pname varchar2(20),
  count number(3),
  price number
);

alter table product add constraint product_pid_pk primary key(pid);

drop sequence product_pid_seq;
create sequence product_pid_seq;

insert into product (pid,pname,count,price)
values (product_pid_seq.nextval,'��������ũ','10','5000');
commit;
select * from product;
delete from product;

select pid,pname,count,price
 from product
 where pid = 1;
 
update product
   set pname = '��������ũ',
       count = 5,
       price = 4000
 where pid = 1;
