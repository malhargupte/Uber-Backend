INSERT INTO app_user (name, email, password)
VALUES
('Aarav Mehta', 'aarav.mehta@gmail.com', '5f4dcc3b5aa765d61d8327deb882cf99'),
('Isha Sharma', 'isha.sharma@gmail.com', '7c6a180b36896a0a8c02787eeafb0e4c'),
('Advik Patel', 'advik.patel@gmail.com', 'e99a18c428cb38d5f260853678922e03'),
('Siya Kapoor', 'siya.kapoor@gmail.com', '098f6bcd4621d373cade4e832627b4f6'),
('Vivaan Gupta', 'vivaan.gupta@gmail.com', 'd41d8cd98f00b204e9800998ecf8427e'),
('Anaya Jain', 'anaya.jain@gmail.com', 'a87ff679a2f3e71d9181a67b7542122c'),
('Krish Singh', 'krish.singh@gmail.com', '8f14e45fceea167a5a36dedd4bea2543'),
('Myra Das', 'myra.das@gmail.com', 'c4ca4238a0b923820dcc509a6f75849b'),
('Arjun Verma', 'arjun.verma@gmail.com', 'eccbc87e4b5ce2fe28308fd9f2a7baf3'),
('Maira Nair', 'maira.nair@gmail.com', 'a5f3c6a11b03839d46af9fb43c97c188'),
('Ayush Bhatnagar', 'ayush.bhatnagar@gmail.com', '4a8a08f09d37b73795649038408b5f33'),
('Riya Chaudhary', 'riya.chaudhary@gmail.com', '8277e0910d750195b448797616e091ad'),
('Aradhya Reddy', 'aradhya.reddy@gmail.com', 'e4da3b7fbbce2345d7772b0674a318d5'),
('Kabir Rao', 'kabir.rao@gmail.com', '1679091c5a880faf6fb5e6087eb1b2dc'),
('Saanvi Mishra', 'saanvi.mishra@gmail.com', '8d9c307cb7f3c4a32822a51922d1ceaa'),
('Atharv Iyer', 'atharv.iyer@gmail.com', '45c48cce2e2d7fbdea1afc51c7c6ad26'),
('Zoya Shetty', 'zoya.shetty@gmail.com', 'd3d9446802a44259755d38e6d163e820'),
('Aaditya Chatterjee', 'aaditya.chatterjee@gmail.com', '6512bd43d9caa6e02c990b0a82652dca'),
('Anvi Bose', 'anvi.bose@gmail.com', 'c20ad4d76fe97759aa27a0c99bff6710'),
('Veer Roy', 'veer.roy@gmail.com', 'c51ce410c124a10e0db5e4b97fc2af39');

INSERT INTO user_roles (user_id, roles)
VALUES
(1, 'RIDER'),
(2, 'RIDER'),
(3, 'DRIVER'),
(4, 'RIDER'),
(5, 'DRIVER'),
(6, 'RIDER'),
(7, 'DRIVER'),
(8, 'RIDER'),
(9, 'DRIVER'),
(10, 'RIDER'),
(11, 'DRIVER'),
(12, 'RIDER'),
(13, 'DRIVER'),
(14, 'RIDER'),
(15, 'DRIVER'),
(16, 'RIDER'),
(17, 'DRIVER'),
(18, 'RIDER'),
(19, 'DRIVER'),
(20, 'RIDER');

INSERT INTO rider (user_id, rating)
VALUES (1, 4.9);

INSERT INTO driver (user_id, rating, available, current_location)
VALUES
(2, 4.9, true, ST_GeomFromText('POINT(73.1812 22.3072)', 4326)),
(3, 4.5, true, ST_GeomFromText('POINT(72.6030 23.5880)', 4326)),
(4, 4.2, true, ST_GeomFromText('POINT(72.5714 23.0225)', 4326)),
(5, 4.8, true, ST_GeomFromText('POINT(73.2016 22.3157)', 4326)),
(6, 4.3, true, ST_GeomFromText('POINT(72.6195 23.6007)', 4326)),
(7, 4.6, true, ST_GeomFromText('POINT(72.5903 23.0120)', 4326)),
(8, 4.7, true, ST_GeomFromText('POINT(73.2091 22.3120)', 4326)),
(9, 4.4, true, ST_GeomFromText('POINT(72.6080 23.5930)', 4326)),
(10, 4.9, true, ST_GeomFromText('POINT(72.5700 23.0200)', 4326)),
(11, 4.1, true, ST_GeomFromText('POINT(73.1850 22.3100)', 4326)),
(12, 4.7, true, ST_GeomFromText('POINT(72.6060 23.5800)', 4326)),
(13, 4.3, true, ST_GeomFromText('POINT(72.5680 23.0190)', 4326)),
(14, 4.6, true, ST_GeomFromText('POINT(73.1900 22.3125)', 4326)),
(15, 4.5, true, ST_GeomFromText('POINT(72.6020 23.6000)', 4326)),
(16, 4.8, true, ST_GeomFromText('POINT(72.5730 23.0215)', 4326)),
(17, 4.4, true, ST_GeomFromText('POINT(73.1950 22.3145)', 4326)),
(18, 4.9, true, ST_GeomFromText('POINT(72.6100 23.5850)', 4326)),
(19, 4.2, true, ST_GeomFromText('POINT(72.5750 23.0220)', 4326)),
(20, 4.8, true, ST_GeomFromText('POINT(73.2000 22.3130)', 4326));

INSERT INTO wallet (user_id, balance)
VALUES
(1, 100),
(2, 500),
(3, 900)



