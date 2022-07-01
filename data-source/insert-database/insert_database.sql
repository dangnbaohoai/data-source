use school_connect;
insert into roles(codeRole,nameRole) values('ADMIN','Quản trị');
insert into roles(codeRole,nameRole)values('TEACHER','Giáo viên');
insert into roles(codeRole,nameRole)values('CENSOR','kiểm duyệt viên');
insert into roles(codeRole,nameRole)values('STUDENT','Học Sinh');

insert into users(maSo,username,passwords,full_name,status)
values(123,'admin','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYGusers','Nguyễn Thành Phú',1);
insert into users(maSo,username,passwords,full_name,status)
values(124,'Hoai','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','Nguyễn hoài',1);
insert into users(maSo,username,passwords,full_name,status)
values(125,'Phu','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','nguyễn Phu',1);
insert into users(maSo,username,passwords,full_name,status)
values(126,'Hieu','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','nguyễn Hieu',1);

INSERT INTO user_roles(userid,roleid) VALUES (123,1);
INSERT INTO user_roles(userid,roleid) VALUES (124,2);
INSERT INTO user_roles(userid,roleid) VALUES (125,3);
INSERT INTO user_roles(userid,roleid) VALUES (126,4);