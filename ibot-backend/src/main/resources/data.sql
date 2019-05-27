DROP TABLE BikeInsuranceData;
CREATE TABLE BikeInsuranceData
AS 
SELECT * 
FROM CSVREAD('bikeinsurances.csv');
INSERT INTO Bike_Insurance_Data (id, bike_type, insurant_type, premium_per_month, company)
SELECT id, biketype, insuranttype, premium_per_month, company FROM BikeInsuranceData;

