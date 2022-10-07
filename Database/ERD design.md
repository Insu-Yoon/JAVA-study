# Schema 구현하기 with SQL

어제 만든 스키마를 SQL로 작성해보았다.

Schema Link : https://github.com/Insu-Yoon/TIL/blob/main/Database/Exercise_Schema%20design.md


```SQL
CREATE TABLE post (
id int PRIMARY KEY AUTO_INCREMENT,
maintext varchar(255),
uid int
);

ALTER TABLE post ADD FOREIGN KEY (uid) REFERENCES users (id);

CREATE TABLE users (
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(255)
);

CREATE TABLE image (
id int PRIMARY KEY AUTO_INCREMENT,
filename varchar(255),
pid int
);

ALTER TABLE image ADD FOREIGN KEY (pid) REFERENCES post (id);

CREATE TABLE followTable (
id int PRIMARY KEY AUTO_INCREMENT,
followingID int,
followerID int
);

ALTER TABLE followTable ADD FOREIGN KEY (followerID) REFERENCES users (id);
ALTER TABLE followTable ADD FOREIGN KEY (followingID) REFERENCES users (id);

CREATE TABLE comment (
id int PRIMARY KEY AUTO_INCREMENT,
comment varchar(255),
uid int,
pid int
);
ALTER TABLE comment ADD FOREIGN KEY (pid) REFERENCES post (id);
ALTER TABLE comment ADD FOREIGN KEY (uid) REFERENCES users (id);


CREATE TABLE likeTable (
id int PRIMARY KEY AUTO_INCREMENT,
uid int,
pid int
);

ALTER TABLE likeTable ADD FOREIGN KEY (pid) REFERENCES post (id);
ALTER TABLE likeTable ADD FOREIGN KEY (uid) REFERENCES users (id);

CREATE TABLE hashtag (
id int PRIMARY KEY AUTO_INCREMENT,
tagtext varchar(255)
);

CREATE TABLE hashtagTable (
id int PRIMARY KEY AUTO_INCREMENT,
hid int,
pid int
);

ALTER TABLE hashtagTable ADD FOREIGN KEY (hid) REFERENCES hashtag (id);
ALTER TABLE hashtagTable ADD FOREIGN KEY (pid) REFERENCES post (id);
```
