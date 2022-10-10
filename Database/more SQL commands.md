## **CASE**

if문 처럼 사용할 수 있는 명령어

```sql
SELECT CASE
	WHEN CustomerId <= 25 THEN 'GROUP 1'
	WHEN CustomerId <= 50 THEN 'GROUP 2'
	ELSE 'GROUP 3'
END
FROM customers
```

CustomerID 필드 값에 따라 GROUP을 3개로 나뉜다.

## **SUBQUERY**

쿼리문 내부에 다른 쿼리문을 포함할 수 있다. 이 때 포함되는 쿼리문을 SUBQUERY라고 한다. 서브 쿼리는 소괄호 () 로 감싸야 하며, 서브 쿼리의 결과는 개별 값 또는 레코드 리스트이며, 하나의 칼럼으로 사용할 수 있다.

```sql
SELECT CustomerId, CustomerId = (SELECT CustomerId FROM customers WHERE CustomerId = 2)
FROM customers
WHERE CustomerId < 6
```

>  FROM 내에서도 서브쿼리를 사용할 수 있다.

## **IN, NOT IN**

특정한 값이 목록, 또는 서브쿼리 내에 있는지 확인할 수 있는 명령어.

```sql
SELECT *
FROM customers
WHERE CustomerCity IN ('Seoul', 'Busan');
```

customers 테이블에서 CustomerCity가 Seoul 또는 Busan인 경우를 가져오는 쿼리문

```sql
SELECT * 
FROM customers
WHERE Country IN (SELECT Country FROM Europe);
```

customers 테이블에서 Country가 Europe에 속해있는 경우를 가져오는 쿼리문

## **EXISTS**

돌려받은 서브쿼리에 존재하는 레코드를 확인한다.

조회하려는 레코드가 존재하면 true, 그렇지 않은 경우 false를 리턴한다.

```sql
SELECT SupplierName
FROM Suppliers
WHERE EXISTS (
	SELECT ProductName 
	FROM Products 
        WHERE Products.SupplierID = Suppliers.supplierID AND Price < 20
);
```

위의 SQL 문은 TRUE를 반환하고, Price가 20 미만인 SupplierName를 나열한다.
