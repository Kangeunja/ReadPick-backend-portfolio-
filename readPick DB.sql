drop view  if exists fullCategoryView;
drop table if exists rec;
drop table if exists searchKeyword;
drop table if exists review;
drop table if exists bookmark;
drop table if exists userPickCount;
drop table if exists userPick;
drop table if exists users;
drop table if exists bookImage;
drop table if exists book; 
drop table if exists isbn;
drop table if exists bookDetailCategory;
drop table if exists bookSubsubCategory;
drop table if exists bookSubCategory;
drop table if exists bookMainCategory;



create table bookMainCategory (
	bmIdx int primary key auto_increment,
    bmName varchar(48) not null unique
);

create table bookSubCategory (
	bsIdx int primary key auto_increment,
    bmIdx int not null,
	bsName varchar(48) not null unique,
    foreign key (bmIdx) references bookMainCategory (bmIdx) on delete cascade
);

create table bookSubSubCategory (
	bssIdx int primary key auto_increment,
    bsIdx int not null,
	bssName varchar(48) not null unique,
    foreign key (bsIdx) references bookSubCategory (bsIdx) on delete cascade
);

create table bookDetailCategory (
	bsssIdx int primary key auto_increment,
    bssIdx int not null,
	bsssName varchar(48) not null unique,
    foreign key (bssIdx) references bookSubsubCategory (bssIdx) on delete cascade
);

-- 검색 isbn 저장용
create table isbn(
	isbnIdx int primary key auto_increment,
    bsssIdx int not null,
    isbn	varchar(13) unique,
    foreign key (bsssIdx) references bookDetailCategory (bsssIdx) on delete cascade
);


create table book (
	bookIdx int primary key auto_increment,
    bmIdx int not null,
    bsIdx int not null,
    bssIdx int not null,
    bsssIdx int not null,
    isbn varchar(13),
    bName varchar(395) not null,
    author varchar(300) not null,
    bContent longtext not null,
    link longtext not null,
    publisher varchar(60) not null,
    pubDate varchar(20) not null,
    foreign key (bmIdx) references bookMainCategory (bmIdx) on delete cascade,
    foreign key (bsIdx) references bookSubCategory (bsIdx) on delete cascade,
    foreign key (bssIdx) references bookSubsubCategory (bssIdx) on delete cascade,
    foreign key (bsssIdx) references bookDetailCategory (bsssIdx) on delete cascade,
    foreign key (isbn) references isbn (isbn) on delete cascade
);

create table bookImage(
	fileIdx	int primary key auto_increment,
	bookIdx int not null,
    fileName varchar(150) not null,
	fileTypeURL char(1) default'N' not null, 
    FOREIGN KEY (bookIdx) REFERENCES book (bookIdx) ON DELETE CASCADE
);

create table users(
	userIdx	int primary key auto_increment,
    userName varchar(52) not null,
    nickName varchar(30) not null,
    Id		 varchar(20) not null,
    pw 		 varchar(20) not null, 
    email	 varchar(40) not null,
	adminAt  char(1) default 'N' not null,
    firstAt char(1) default 'Y' not null
);

insert into users value(null, "관리자","관리자", "admin", "admin", "admin123@gmail.com","Y","Y"); 

-- 가입 시 입력한 유저의 관심분야
create table userPick(
	userIdx	int not null,
    bmIdx int not null,
    bsIdx int not null,
    bssIdx int not null,
    FOREIGN KEY (userIdx) REFERENCES users (userIdx) ON DELETE CASCADE,
    foreign key (bmIdx) references bookMainCategory (bmIdx) on delete cascade,
    foreign key (bsIdx) references bookSubCategory (bsIdx) on delete cascade,
    foreign key (bssIdx) references booksubsubcategory (bssIdx) on delete cascade,
    primary key(userIdx, bmIdx, bsIdx, bssIdx)
);

-- 유저가 검색할 때 중복체크를 위한 테이블
create table userPickCount(  
	userIdx	int not null,
    bmIdx int,
    bsIdx int,
    bssIdx int,
    bsssIdx int,
    FOREIGN KEY (userIdx) REFERENCES users (userIdx) ON DELETE CASCADE,
    foreign key (bmIdx) references bookMainCategory (bmIdx) on delete cascade,
    foreign key (bsIdx) references bookSubCategory (bsIdx) on delete cascade,
    foreign key (bssIdx) references bookSubsubCategory (bssIdx) on delete cascade,
    foreign key (bsssIdx) references bookDetailCategory (bsssIdx) on delete cascade,
    primary key(userIdx, bmIdx, bsIdx, bssIdx, bsssIdx)
);

create table review(
	userIdx	int not null,
    bookIdx	int not null,
    content varchar(600) not null,
    reviewAt char(1) default 'N' not null,
    regDate datetime DEFAULT now(),
    FOREIGN KEY (userIdx) REFERENCES users (userIdx) ON DELETE CASCADE,
    FOREIGN KEY (bookIdx) REFERENCES book (bookIdx) ON DELETE CASCADE,
    primary key (userIdx, bookIdx)
);

-- 찜 목록 저장
create table bookmark (
    userIdx int not null,
    bookIdx int not null,
    bookmarkAt int auto_increment unique,
    isBookmarked char(1) default 'N',
    foreign key (userIdx) references users (userIdx) on delete cascade,
    foreign key (bookIdx) references book (bookIdx) on delete cascade,
    primary key(userIdx, bookIdx)
);

create table rec (
    userIdx int not null,
    bookIdx int not null,
    recAt int auto_increment unique,
    isRecommended char(1) default 'N',
    foreign key (userIdx) references users (userIdx) on delete cascade,
    foreign key (bookIdx) references book (bookIdx) on delete cascade,
    primary key(userIdx, bookIdx)
);

-- 검색할때만 쓰일 테이블
create table searchKeyword(
	keywordIdx int primary key auto_increment,
    keywordName varchar(23) not null
);



insert into searchKeyword (keywordName) values
('문학/소설'),
('역사/문화'),
('철학/종교'),
('인문학'),
('과학'),
('경제/경영'),
('자기계발'),
('예술/디자인'),
('취미/여가'),
('실용/생활'),
('기술/컴퓨터'),
('아동/청소년'),
('학문/전문서적'),
('만화/그래픽노블'),
('외국어'),
('종교/명상'),
('전문/기술');

create or replace view fullCategoryView as
select 
    bs.bmIdx,
    bm.bmName,
    bss.bsIdx,
    bs.bsName,
    bsss.bssIdx,
    bss.bssName,
    bsss.bsssName,
    i.bsssIdx,
	i.isbn
from
	bookMainCategory bm
left join 
    bookSubCategory bs on bm.bmIdx = bs.bmIdx
left join 
    bookSubSubCategory bss on bs.bsIdx = bss.bsIdx
left join 
    bookDetailCategory bsss on bss.bssIdx = bsss.bssIdx
inner join 
	isbn i on bsss.bsssIdx = i.bsssIdx
group by i.isbn, bs.bmIdx, bm.bmName, bss.bsIdx, bs.bsName, bsss.bssIdx, bss.bssName, bsss.bsssName, i.bsssIdx;

create or replace view bookAndImageView as
select 
    b.bookIdx,
    bi.fileName
from
	book b
left join 
    bookImage bi on b.bookIdx = bi.bookIdx
group by b.bookIdx, bi.fileName;

select * from userPick;
