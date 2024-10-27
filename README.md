# Proje Hakkında

Bu Proje Spring Boot 3.3.5 ve Java 21 Kullanılarak geliştirildi.
Proje içerisinde yer alan eureka, gateway, order ve product servisleri docker üzerinde ayağa kaldırılarak çalıştırılmıştır.
Proje içerisinde yer alan eureka servisi servislerin kayıt olduğu servis kayıt sunucusudur.
Proje içerisinde yer alan gateway servisi servislerin dışarıdan erişimini sağlayan servistir.
Proje içerisinde yer alan order servisi sipariş işlemlerinin yapıldığı servistir.
Proje içerisinde yer alan product servisi ürün işlemlerinin yapıldığı servistir.

# Proje Kurulumu
Kodlar lokal bilgisayara indirildikten sonra aşağıdaki adımlar izlenerek proje ayağa kaldırılabilir.

1. Proje içerisinde yer alan docker-compose.yml dosyası terminal üzerinden çalıştırılır.
``` docker compose up ``` 
2. Proje içerisinde yer alan eureka, gateway, order ve product servisleri sırasıyla ayağa kaldırılır.
3. Proje içerisinde yer alan eureka servisi http://localhost:8761/ adresinden erişilebilir.
4. Proje içerisinde yer alan gateway servisi http://localhost:8080/ adresinden erişilebilir.
5. Proje içerisinde yer alan order servisi http://localhost:8081/ adresinden erişilebilir.
6. Proje içerisinde yer alan product servisi http://localhost:8082/ adresinden erişilebilir.

docker compose up ile ayağa kalkarken servislerin ayağa kalkması beklenmiyor. 30sn sonrasında tüm servislerin ayağa kalktığını eureka üzerinden kontrol edilebilir.

# Proje Testi
Proje içerisinde yer alan order ve product servisleri postman üzerinden test edilebilir. 
Yada product ve order servisleri için localhost:8081/swagger-ui.html ve localhost:8082/swagger-ui.html adreslerinden test edilebilir.
Test ederken order servisinde userId olarak istediğinizi kullanabilirsiniz. Bir kullanıcı login/session/security işlemi yoktur. Sipariş Filtrelemede/Oluşturmada sadece userId kullanılmıştır.
http://localhost:8081/v3/api-docs adresinden order servisi için openapi.json içeriğine erişilebilir.
http://localhost:8082/v3/api-docs adresinden product servisi için openapi.json içeriğine erişilebilir.
İlgili open api json dosyaları postman içerisine import edilip host bilgisi localhost:8080 değiştirildiği taktirde ilgili testler gateway sunucusu üzerinden ilgili microservislere yönlendirilebilir.
# Proje Yapısı
Proje içerisinde yer alan eureka, gateway, order ve product servisleri ayrı ayrı modüller halinde geliştirilmiştir.
Api dokümantasyonu Swagger ile yapılmıştır.
ExceptionHandling sadece order servisi için yapılmıştır.
Loglama ile ilgili işlemler sadece order servisi için yapılmıştır.
UnitTestler sadece product servisinde mevcuttur. Bir kısım methodların unit testleri yazılmıştır.
Entegrasyon testlerine zaman kalmadığı için yazılmamıştır.
Kafka yada RabbitMq ile ilgili bir entegrasyon yapılmamıştır.
```