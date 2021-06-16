-- 통신판매사업자_원본 테이블 생성
create table onl_biz_mkt_raw (
     regno	varchar(30) 
    ,orgname	varchar(50)
    ,bizname	varchar(200)
    ,bizregno	varchar(20) 
    ,crp_prv_cd	varchar(2)
    ,ceoname	varchar(200)
    ,tel	varchar(40)
    ,email	varchar(150)
    ,regdate	char(8)
    ,address	varchar(250)
    ,address_road	varchar(250)
    ,status	varchar(20)
    ,orgtel	varchar(20)
    ,salestype	varchar(50)
    ,product	varchar(200)
    ,domain	varchar(400)
    ,hostlocation varchar(300)
);
COMMENT ON COLUMN onl_mkt_biz_raw.regno        IS '통신판매번호';  
COMMENT ON COLUMN onl_mkt_biz_raw.orgname      IS '신고기관명';    
COMMENT ON COLUMN onl_mkt_biz_raw.bizname      IS '상호';    
COMMENT ON COLUMN onl_mkt_biz_raw.bizregno     IS '사업자등록번호';     
COMMENT ON COLUMN onl_mkt_biz_raw.crp_prv_cd   IS '법인여부';       
COMMENT ON COLUMN onl_mkt_biz_raw.ceoname      IS '대표자명';    
COMMENT ON COLUMN onl_mkt_biz_raw.tel          IS '전화번호';
COMMENT ON COLUMN onl_mkt_biz_raw.email        IS '전자우편';  
COMMENT ON COLUMN onl_mkt_biz_raw.regdate      IS '신고일자';    
COMMENT ON COLUMN onl_mkt_biz_raw.address      IS '사업장소재지';    
COMMENT ON COLUMN onl_mkt_biz_raw.address_road IS '사업장소재지(도로명)';         
COMMENT ON COLUMN onl_mkt_biz_raw.status       IS '업소상태';   
COMMENT ON COLUMN onl_mkt_biz_raw.orgtel       IS '신고기관 대표연락처';   
COMMENT ON COLUMN onl_mkt_biz_raw.salestype    IS '판매방식';      
COMMENT ON COLUMN onl_mkt_biz_raw.product      IS '취급품목';    
COMMENT ON COLUMN onl_mkt_biz_raw.domain       IS '인터넷도메인';   
COMMENT ON COLUMN onl_mkt_biz_raw.hostlocation IS '호스트서버소재지';         


-- File To DB 로 데이터 저장 (in Java)

-- 전체건수 1,602,353 건
select count(1) from onl_mkt_biz_raw;

-- 사업자번호 기준 중복제거 1,482,034 건
select count(distinct bizregno) from onl_mkt_biz_raw;

-- 통신판매등록번호 기준 중복제거 1,502,414 건
select count(distinct regno) from onl_mkt_biz_raw;

-- 사업자번호 인덱스 생성
create index idx01 on onl_mkt_biz_raw (bizregno);

-- 비유효 사업자번호는 오류보관 테이블에 백업, 원본에서 데이터 삭제 49,181건
create table onl_mkt_biz_err as
select * from onl_mkt_biz_raw a
where a.bizregno not similar to '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
or a.bizregno in ('0000000000','1111111111','2222222222','3333333333','4444444444','5555555555','6666666666','7777777777','8888888888','9999999999');

delete from onl_mkt_biz_raw a
where a.bizregno not similar to '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
or a.bizregno in ('0000000000','1111111111','2222222222','3333333333','4444444444','5555555555','6666666666','7777777777','8888888888','9999999999');

-- 최종 원본데이터 1,553,172건
select count(1) from onl_mkt_biz_raw

-- -- 통신판매사업자_마스터 테이블 생성 (사업자번호를 PK 로 정제한 서비스 테이블)
create table onl_mkt_biz_mst (
     bizregno	char(10) primary key
    ,regno	varchar(30) 
    ,orgname	varchar(50)
    ,bizname	varchar(200)
    ,crp_prv_cd	varchar(2)
    ,ceoname	varchar(200)
    ,tel	varchar(40)
    ,email	varchar(150)
    ,regdate	char(8)
    ,address	varchar(250)
    ,address_road	varchar(250)
    ,status	varchar(20)
    ,orgtel	varchar(20)
    ,salestype	varchar(50)
    ,product	varchar(200)
    ,domain	varchar(400)
    ,hostlocation varchar(300)
);
COMMENT ON COLUMN onl_mkt_biz_mst.bizregno     IS '사업자등록번호';     
COMMENT ON COLUMN onl_mkt_biz_mst.regno        IS '통신판매번호';  
COMMENT ON COLUMN onl_mkt_biz_mst.orgname      IS '신고기관명';    
COMMENT ON COLUMN onl_mkt_biz_mst.bizname      IS '상호';    
COMMENT ON COLUMN onl_mkt_biz_mst.crp_prv_cd   IS '법인여부';       
COMMENT ON COLUMN onl_mkt_biz_mst.ceoname      IS '대표자명';    
COMMENT ON COLUMN onl_mkt_biz_mst.tel          IS '전화번호';
COMMENT ON COLUMN onl_mkt_biz_mst.email        IS '전자우편';  
COMMENT ON COLUMN onl_mkt_biz_mst.regdate      IS '신고일자';    
COMMENT ON COLUMN onl_mkt_biz_mst.address      IS '사업장소재지';    
COMMENT ON COLUMN onl_mkt_biz_mst.address_road IS '사업장소재지(도로명)';         
COMMENT ON COLUMN onl_mkt_biz_mst.status       IS '업소상태';   
COMMENT ON COLUMN onl_mkt_biz_mst.orgtel       IS '신고기관 대표연락처';   
COMMENT ON COLUMN onl_mkt_biz_mst.salestype    IS '판매방식';      
COMMENT ON COLUMN onl_mkt_biz_mst.product      IS '취급품목';    
COMMENT ON COLUMN onl_mkt_biz_mst.domain       IS '인터넷도메인';   
COMMENT ON COLUMN onl_mkt_biz_mst.hostlocation IS '호스트서버소재지';   

-- 원본데이터에서 중복 존재하는 사업자번호 추출하여 템프테이블에 저장 65,686 건
create temp local table tmp_dup_bizregno as
select bizregno
from onl_mkt_biz_raw
group by bizregno
having count(1) > 1;

-- 1차로 중복없는 사업자번호를 서비스 테이블에 저장 1,414,977 건
insert into onl_mkt_biz_mst(bizregno,regno,orgname,bizname,crp_prv_cd,ceoname,tel,email,regdate,address,address_road,status,orgtel,salestype,product,domain,hostlocation)
select a.bizregno,regno,orgname,bizname,crp_prv_cd,ceoname,tel,email,regdate,address,address_road,status,orgtel,salestype,product,domain,hostlocation
from onl_mkt_biz_raw a
left outer join tmp_dup_bizregno b
on a.bizregno = b.bizregno
where b.bizregno is null;

-- 중복 사업자번호는 업무적인 기준을 정하고 단 1개의 Row만 골라서 서비스 테이블에 저장하기 위해
-- (사업자번호, 등록일자, 통신판매등록번호) 순으로 정렬하여 Rownum 을 부여한 템프테이블에 저장 (138,195건)
create local temp table tmp_dup_biz_raw as
select (ROW_NUMBER() OVER()) AS seq, b.*
from tmp_dup_bizregno a
join onl_mkt_biz_raw b
on a.bizregno = b.bizregno
order by b.bizregno,b.regdate,b.regno;

-- 중복 사업자번호를 필터링하여 서비스 테이블에 2차 저장 65688 건
insert into onl_mkt_biz_mst(bizregno,regno,orgname,bizname,crp_prv_cd,ceoname,tel,email,regdate,address,address_road,status,orgtel,salestype,product,domain,hostlocation)
select b.bizregno,b.regno,orgname,bizname,crp_prv_cd,ceoname,tel,email,b.regdate,address,address_road,status,orgtel,salestype,product,domain,hostlocation
from (
  select a.bizregno,max(a.seq) as max_seq -- 사업자번호 기준으로 가장 정렬순번이 높은 데이터 1건 식별
  from tmp_dup_biz_raw a
  group by a.bizregno
)a
join tmp_dup_biz_raw b
on a.bizregno = b.bizregno
and a.max_seq = b.seq; 

-- 최종 서비스 테이블 완성 1,480,665 건
select count(1) from onl_mkt_biz_mst;

-- 최종 커밋
commit;

-- 통계정보갱신
analyze onl_mkt_biz_mst;
